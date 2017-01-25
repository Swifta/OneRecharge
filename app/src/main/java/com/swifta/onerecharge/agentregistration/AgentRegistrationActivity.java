package com.swifta.onerecharge.agentregistration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.swifta.onerecharge.AgentActivity;
import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agentregistration.loginresponsemodel.AgentRegistration;
import com.swifta.onerecharge.agentregistration.registerrequestmodel.AgentSignUpBody;
import com.swifta.onerecharge.agentregistration.registerrequestmodel.BusinessProfile;
import com.swifta.onerecharge.agentregistration.registerrequestmodel.Data;
import com.swifta.onerecharge.agentregistration.registerrequestmodel.Uploads;
import com.swifta.onerecharge.agentregistration.registerresponsemodel.AgentSignUpResponse;
import com.swifta.onerecharge.util.AgentService;
import com.swifta.onerecharge.util.EmailRegexValidator;
import com.swifta.onerecharge.util.ImageNameBuilder;
import com.swifta.onerecharge.util.InputStreamToFileConverter;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.UploadType;
import com.swifta.onerecharge.util.Url;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AgentRegistrationActivity extends AppCompatActivity {
    // Login
    @BindView(R.id.user_login_email_address)
    TextInputEditText loginEmailAddressField;
    @BindView(R.id.user_login_password)
    TextInputEditText passwordField;

    @BindView(R.id.user_login_email_address_layout)
    TextInputLayout emailAddressFieldLayout;
    @BindView(R.id.user_login_password_layout)
    TextInputLayout passwordFieldLayout;
    @BindView(R.id.login_progressBar)
    ProgressBar agentLoginProgress;
    @BindView(R.id.user_log_in)
    LinearLayout agentLogInView;

    // Sign Up - Agent type details
    @BindView(R.id.individual_agent)
    RadioButton individualAgent;
    @BindView(R.id.business_agent)
    RadioButton businessAgent;
    @BindView(R.id.agent_type_details)
    LinearLayout agentTypeDetailsView;

    // Sign Up - Personal details
    @BindView(R.id.agent_signup_last_name)
    TextInputEditText lastNameField;
    @BindView(R.id.agent_signup_other_names)
    TextInputEditText otherNamesField;
    @BindView(R.id.agent_signup_email_address)
    TextInputEditText signupEmailAddressField;
    @BindView(R.id.agent_signup_personal_phone)
    TextInputEditText personalPhoneField;
    @BindView(R.id.agent_signup_alternate_phone)
    TextInputEditText alternatePhoneField;

    @BindView(R.id.agent_signup_last_name_layout)
    TextInputLayout lastNameFieldLayout;
    @BindView(R.id.agent_signup_other_names_layout)
    TextInputLayout otherNamesFieldLayout;
    @BindView(R.id.agent_signup_email_address_layout)
    TextInputLayout signupEmailAddressFieldLayout;
    @BindView(R.id.agent_signup_personal_phone_layout)
    TextInputLayout personalPhoneFieldLayout;
    @BindView(R.id.personal_details_progressBar)
    ProgressBar personalDetailsProgressBar;
    @BindView(R.id.personal_details_progress_text)
    TextView personalDetailsProgressText;
    @BindView(R.id.agent_personal_details)
    LinearLayout agentSignUpPersonalDetailsView;

    // Sign Up - Company details
    @BindView(R.id.agent_signup_company_name)
    TextInputEditText companyNameField;
    @BindView(R.id.agent_signup_company_registration_number)
    TextInputEditText companyRegistrationNumberField;
    @BindView(R.id.agent_signup_company_telephone_number)
    TextInputEditText companyPhoneNumberField;
    @BindView(R.id.agent_signup_contact_name)
    TextInputEditText contactNameField;
    @BindView(R.id.agent_signup_contact_telephone_number)
    TextInputEditText contactPhoneNumberField;
    @BindView(R.id.agent_company_details)
    LinearLayout agentSignUpCompanyView;

    // Sign Up - Upload details
    @BindView(R.id.business_certificate_success_image)
    ImageView businessCertificateUploadImage;
    @BindView(R.id.identification_success_image)
    ImageView identificationUploadImage;
    @BindView(R.id.proof_success_image)
    ImageView proofUploadImage;

    @BindView(R.id.upload_progressBar)
    ProgressBar uploadDetailsProgressBar;
    @BindView(R.id.upload_progress_text)
    TextView uploadDetailsProgressText;
    @BindView(R.id.agent_upload_details)
    LinearLayout agentSignUpUploadView;
    @BindView(R.id.business_certificate_layout)
    LinearLayout agentSignUpUploadBusinessView;

    // Sign Up - Agent class details
    @BindView(R.id.discount_based_agent)
    RadioButton discountBasedAgent;
    @BindView(R.id.commission_based_agent)
    RadioButton commissionBasedAgent;
    @BindView(R.id.referral_id)
    TextInputEditText referralIdField;
    @BindView(R.id.terms_and_conditions)
    CheckBox termsAndConditions;
    @BindView(R.id.agent_class_details)
    LinearLayout agentSignUpClassView;

    // Login Fields
    String loginEmailAddress;
    String password;

    // Sign up fields for agent type
    String agentType;

    // Sign up fields for personal details
    String lastName;
    String otherNames;
    String signUpEmail;
    String personalPhoneNumber;
    String alternatePhoneNumber;
    String country = "";
    String region = "";

    // Sign up fields for company details
    String companyName = "";
    String companyRegistrationNumber = "";
    String companyPhoneNumber = "";
    String companyContactName = "";
    String companyContactPhoneNumber = "";

    // Sign up fields for upload details
    String businessCacName = "";
    String meansOfIdentificationName = "";
    String proofOfAddressName = "";

    // Sign up fields for agent class details
    String agentClass;
    String referralId;
    Integer termsAndConditionsAccepted;

    Subscription subscription;

    private String authToken;
    private SharedPreferences sharedPreferences;
    private boolean isAgentLoggedIn;

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 200;
    private String uploadType;
    private Uri selectedImageUri;
    private InputStream businessCacStream;
    private InputStream identificationStream;
    private InputStream proofOfAddressStream;
    private InputStream imageStream;
    String imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);
        isAgentLoggedIn = sharedPreferences.getBoolean(getString(R.string
                .is_agent_logged_in), false);

        if (isAgentLoggedIn) {
            switchToAgentActivity();
        }

        setContentView(R.layout.activity_agent_registration);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
    }

    @OnClick(R.id.user_signup_button)
    void launchAgentSignUpView() {
        agentLogInView.setVisibility(View.GONE);
        agentTypeDetailsView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.user_login_button)
    void logAgentIn() {

        emailAddressFieldLayout.setError(null);
        passwordFieldLayout.setError(null);

        loginEmailAddress = loginEmailAddressField.getText().toString();
        password = passwordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (password.isEmpty()) {
            passwordFieldLayout.setError(getString(
                    R.string.password_empty_error));
            focusView = passwordFieldLayout;
            cancel = true;
        } else {
            passwordFieldLayout.setError(null);
        }

        if (loginEmailAddress.isEmpty()) {
            emailAddressFieldLayout.setError(getResources().getString(R
                    .string.email_empty_error));
            focusView = emailAddressFieldLayout;
            cancel = true;
        } else if (!EmailRegexValidator.isValidEmail(loginEmailAddress)) {
            emailAddressFieldLayout.setError(getString(R.string.email_invalid_error));
            focusView = emailAddressFieldLayout;
            cancel = true;
        } else {
            emailAddressFieldLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext
                    ())) {
                Snackbar.make(agentLogInView, R.string.internet_error, Snackbar
                        .LENGTH_SHORT).show();
            } else {
                agentLogInView.setVisibility(View.GONE);
                agentLoginProgress.setVisibility(View.VISIBLE);
                performLoginNetworkRequest();
            }
        }
    }

    @OnClick(R.id.agent_personal_login_button)
    void launchAgentLogInView() {
        agentTypeDetailsView.setVisibility(View.GONE);
        agentLogInView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.agent_first_next_button)
    void switchToSpecificPersonalViews() {
        if (individualAgent.isChecked()) {
            agentType = getString(R.string.individual_agent);
        } else if (businessAgent.isChecked()) {
            agentType = getString(R.string.business_agent);
        }

        agentTypeDetailsView.setVisibility(View.GONE);
        agentSignUpPersonalDetailsView.setVisibility(View.VISIBLE);
        setUpPersonalDetailsView();
    }

    @OnClick(R.id.agent_personal_back_button)
    void switchToTypeDetailsView() {
        agentSignUpPersonalDetailsView.setVisibility(View.GONE);
        agentTypeDetailsView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.agent_personal_next_button)
    void switchToOtherViews() {

        lastNameFieldLayout.setError(null);
        otherNamesFieldLayout.setError(null);
        signupEmailAddressFieldLayout.setError(null);
        personalPhoneFieldLayout.setError(null);

        lastName = lastNameField.getText().toString();
        otherNames = otherNamesField.getText().toString();
        signUpEmail = signupEmailAddressField.getText().toString();
        personalPhoneNumber = personalPhoneField.getText().toString();
        alternatePhoneNumber = alternatePhoneField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (personalPhoneNumber.isEmpty()) {
            personalPhoneFieldLayout.setError(getString(R.string.phone_number_empty_error));
            focusView = personalPhoneFieldLayout;
            cancel = true;
        } else {
            personalPhoneFieldLayout.setError(null);
        }

        if (signUpEmail.isEmpty()) {
            signupEmailAddressFieldLayout.setError(getString(R
                    .string.email_empty_error));
            focusView = signupEmailAddressFieldLayout;
            cancel = true;
        } else if (!EmailRegexValidator.isValidEmail(signUpEmail)) {
            signupEmailAddressFieldLayout.setError(getString(R.string.email_invalid_error));
            focusView = signupEmailAddressFieldLayout;
            cancel = true;
        } else {
            signupEmailAddressFieldLayout.setError(null);
        }

        if (otherNames.isEmpty()) {
            otherNamesFieldLayout.setError(getString(R.string.other_names_empty_error));
            focusView = otherNamesFieldLayout;
            cancel = true;
        } else {
            otherNamesFieldLayout.setError(null);
        }

        if (lastName.isEmpty()) {
            lastNameFieldLayout.setError(getString(R.string.last_name_empty_error));
            focusView = lastNameFieldLayout;
            cancel = true;
        } else {
            lastNameFieldLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            agentSignUpPersonalDetailsView.setVisibility(View.GONE);
            if (agentType.equals(getString(R.string.individual_agent))) {
                agentSignUpUploadView.setVisibility(View.VISIBLE);
                setUpUploadsDetailsView();
            } else if (agentType.equals(getString(R.string.business_agent))) {
                agentSignUpCompanyView.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.agent_company_back_button)
    void backToPersonalDetailsView() {
        agentSignUpCompanyView.setVisibility(View.GONE);
        agentSignUpPersonalDetailsView.setVisibility(View.VISIBLE);
        setUpPersonalDetailsView();
    }

    @OnClick(R.id.agent_company_next_button)
    void switchToUploadsView() {
        companyName = companyNameField.getText().toString();
        companyRegistrationNumber = companyRegistrationNumberField.getText()
                .toString();
        companyPhoneNumber = companyPhoneNumberField.getText().toString();
        companyContactName = contactNameField.getText().toString();
        companyContactPhoneNumber = contactPhoneNumberField.getText().toString();

        agentSignUpCompanyView.setVisibility(View.GONE);
        agentSignUpUploadView.setVisibility(View.VISIBLE);
        setUpUploadsDetailsView();
    }

    @OnClick(R.id.business_certificate_button)
    void uploadBusinessCertificate() {
        uploadType = UploadType.CAC;
        getDocument();
    }

    @OnClick(R.id.upload_identification_button)
    void uploadMeansOfIdentification() {
        uploadType = UploadType.IDENTIFICATION;
        getDocument();
    }

    @OnClick(R.id.upload_proof_button)
    void uploadProof() {
        uploadType = UploadType.PROOF_OF_ADDRESS;
        getDocument();
    }

    @OnClick(R.id.agent_upload_back_button)
    void backToPreUploadView() {
        agentSignUpUploadView.setVisibility(View.GONE);
        if (agentType.equals(getString(R.string.individual_agent))) {
            agentSignUpPersonalDetailsView.setVisibility(View.VISIBLE);
            setUpPersonalDetailsView();
        } else if (agentType.equals(getString(R.string.business_agent))) {
            agentSignUpCompanyView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.agent_upload_next_button)
    void switchToAgentClassView() {
        if (agentType.equals(getString(R.string.individual_agent))) {

            if (meansOfIdentificationName.isEmpty()) {
                Toast.makeText(this, "Please upload a means of identification",
                        Toast.LENGTH_SHORT).show();
            } else if (proofOfAddressName.isEmpty()) {
                Toast.makeText(this, "Please upload your proof of address",
                        Toast.LENGTH_SHORT).show();
            } else {
                agentSignUpUploadView.setVisibility(View.GONE);
                agentSignUpClassView.setVisibility(View.VISIBLE);
                referralIdField.requestFocus();
            }
        } else if (agentType.equals(getString(R.string.business_agent))) {
            if (businessCacName.isEmpty()) {
                Toast.makeText(this, "Please upload your Business CAC " +
                        "Certificate", Toast
                        .LENGTH_SHORT).show();
            } else if (meansOfIdentificationName.isEmpty()) {
                Toast.makeText(this, "Please upload a means of identification",
                        Toast.LENGTH_SHORT).show();
            } else if (proofOfAddressName.isEmpty()) {
                Toast.makeText(this, "Please upload your Proof of address",
                        Toast.LENGTH_SHORT).show();
            } else {
                agentSignUpUploadView.setVisibility(View.GONE);
                agentSignUpClassView.setVisibility(View.VISIBLE);
                referralIdField.requestFocus();
            }
        }
    }

    @OnClick(R.id.agent_class_back_button)
    void backToUploadView() {
        agentSignUpClassView.setVisibility(View.GONE);
        agentSignUpUploadView.setVisibility(View.VISIBLE);
        setUpUploadsDetailsView();
    }

    @OnClick(R.id.submit_and_signup_button)
    void registerAgent() {
        referralId = referralIdField.getText().toString();

        if (discountBasedAgent.isChecked()) {
            agentClass = getString(R.string.discount_based_agent);
        } else if (commissionBasedAgent.isChecked()) {
            agentClass = getString(R.string.commission_based_agent);
        }

        if (!termsAndConditions.isChecked()) {
            Snackbar.make(agentSignUpClassView, R.string.please_accept_terms_and_conditions, Snackbar
                    .LENGTH_SHORT)
                    .show();
        } else {
            termsAndConditionsAccepted = 1;
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext
                    ())) {
                Snackbar.make(agentLogInView, R.string.internet_error, Snackbar
                        .LENGTH_SHORT).show();
            } else {
                agentSignUpClassView.setVisibility(View.GONE);
                agentLoginProgress.setVisibility(View.VISIBLE);
                performAgentSignUp();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch ((requestCode)) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();

                    String imageExtension = getImageExtension(selectedImageUri);
                    imageName = ImageNameBuilder.buildAmazonDocumentName
                            (uploadType, signUpEmail,
                                    imageExtension);
                    try {

                        if (ContextCompat.checkSelfPermission(this, Manifest
                                .permission.READ_EXTERNAL_STORAGE) ==
                                PackageManager
                                        .PERMISSION_GRANTED) {
                            imageStream = getContentResolver()
                                    .openInputStream(selectedImageUri);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    switch (uploadType) {
                        case UploadType.CAC:
                            businessCacName = imageName;
                            businessCacStream = imageStream;
                            if ((!businessCacName.isEmpty()) && businessCacStream != null) {
                                businessCertificateUploadImage.setVisibility(View.VISIBLE);
                            }
                            break;
                        case UploadType.IDENTIFICATION:
                            meansOfIdentificationName = imageName;
                            identificationStream = imageStream;
                            if ((!meansOfIdentificationName.isEmpty()) && identificationStream !=
                                    null) {
                                identificationUploadImage.setVisibility(View.VISIBLE);
                            }
                            break;
                        case UploadType.PROOF_OF_ADDRESS:
                            proofOfAddressName = imageName;
                            proofOfAddressStream = imageStream;
                            if ((!proofOfAddressName.isEmpty()) && proofOfAddressStream != null) {
                                proofUploadImage.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                }
        }
    }

    @Override
    protected void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        try {
            if (imageStream != null) {
                imageStream.close();
            }
            if (businessCacStream != null) {
                businessCacStream.close();
            }
            if (identificationStream != null) {
                identificationStream.close();
            }

            if (proofOfAddressStream != null) {
                proofOfAddressStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void performLoginNetworkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<AgentRegistration> agent = agentService
                .logAgentIn(loginEmailAddress, password);

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentRegistration>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        agentLoginProgress.setVisibility(View.GONE);
                        agentLogInView.setVisibility(View.VISIBLE);

                        Snackbar.make(agentLogInView, R.string.user_login_error,
                                Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentRegistration agentRegistration) {
                        if (agentRegistration.getStatus() == 1) {
                            authToken = agentRegistration.getData();
                            saveAgentLoginResult();
                            saveAgentState();
                            switchToAgentActivity();
                        } else {
                            agentLoginProgress.setVisibility(View.GONE);
                            agentLogInView.setVisibility(View.VISIBLE);

                            Snackbar.make(agentLogInView,
                                    agentRegistration.getData(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveAgentLoginResult() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_email_address),
                loginEmailAddress);
        editor.putString(getResources().getString(R.string.saved_auth_token), authToken);
        editor.apply();
    }

    private void saveAgentState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.is_agent_logged_in), true);
        editor.apply();
    }

    private void switchToAgentActivity() {
        Intent intent = new Intent(AgentRegistrationActivity.this,
                AgentActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUpPersonalDetailsView() {
        if (agentType.equals(getString(R.string.individual_agent))) {
            personalDetailsProgressBar.setProgress(33);
            personalDetailsProgressText.setText(getString(R.string.thirty_three_percent));
        } else if (agentType.equals(getString(R.string.business_agent))) {
            personalDetailsProgressBar.setProgress(25);
            personalDetailsProgressText.setText(getString(R.string.twenty_five_percent));
        }
    }

    private void setUpUploadsDetailsView() {
        if (agentType.equals(getString(R.string.individual_agent))) {
            agentSignUpUploadBusinessView.setVisibility(View.GONE);
            uploadDetailsProgressBar.setProgress(67);
            uploadDetailsProgressText.setText(getString(R.string.sixty_seven_percent));
        } else if (agentType.equals(getString(R.string.business_agent))) {
            agentSignUpUploadBusinessView.setVisibility(View.VISIBLE);
            uploadDetailsProgressBar.setProgress(75);
            uploadDetailsProgressText.setText(getString(R.string.seventy_five_percent));
        }
    }

    private void getDocument() {
        if (ContextCompat.checkSelfPermission(this, Manifest
                .permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);

        } else {
            Intent selectPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
            selectPhotoIntent.setType("image/*");
            startActivityForResult(selectPhotoIntent, SELECT_PHOTO);
        }
    }

    private String getImageExtension(Uri selectedImage) {
        String filePath = null;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex
                    (filePathColumn[0]);
            filePath = cursor.getString(columnIndex);

            if (filePath != null) {
                filePath = filePath.substring(filePath.lastIndexOf("."));
            }
            cursor.close();
        }

        return filePath;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager
                    .PERMISSION_GRANTED) {
                getDocument();
            } else {
                // If permission is not granted, show an explanation
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(agentSignUpUploadView, "You can't upload " +
                            "pictures without accepting this permission", Snackbar
                            .LENGTH_INDEFINITE).setAction("Accept", view -> ActivityCompat.requestPermissions
                            (AgentRegistrationActivity.this, new
                                    String[]{Manifest
                                    .permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)).show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest
                            .permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void performAgentSignUp() {

        Uploads uploads = new Uploads(meansOfIdentificationName, proofOfAddressName,
                businessCacName);

        BusinessProfile businessProfile = new BusinessProfile(companyName, companyRegistrationNumber,
                companyPhoneNumber, companyContactName,
                companyContactPhoneNumber);

        Data data = new Data(lastName, otherNames, signUpEmail,
                personalPhoneNumber, alternatePhoneNumber, agentType,
                agentClass, referralId, country, region, termsAndConditionsAccepted, uploads,
                businessProfile);

        AgentSignUpBody agentSignUpBody = new AgentSignUpBody(data);

        if (identificationStream != null) {
            uploadImageWithRxJava(identificationStream, meansOfIdentificationName);
        }

        if (proofOfAddressStream != null) {
            uploadImageWithRxJava(proofOfAddressStream, proofOfAddressName);
        }

        if (businessCacStream != null) {
            uploadImageWithRxJava(businessCacStream, businessCacName);
        }

        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        AgentService agentService = retrofit.create(AgentService.class);
        final Observable<AgentSignUpResponse> registerObservable =
                agentService.registerNewAgent(agentSignUpBody);

        registerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentSignUpResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        agentLoginProgress.setVisibility(View.GONE);
                        agentSignUpClassView.setVisibility(View.VISIBLE);
                        Toast.makeText(AgentRegistrationActivity.this, "We are unable to register" +
                                " you. Please check your details and try again", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AgentSignUpResponse agentSignUpResponse) {
                        agentLoginProgress.setVisibility(View.GONE);

                        if (agentSignUpResponse.getStatus() == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder
                                    (getApplicationContext())
                                    .setCancelable(false)
                                    .setMessage("Your registration was successful! If " +
                                            "your registration is confirmed, " +
                                            "you will get an email with your login credentials.")
                                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                        Intent i = new Intent(AgentRegistrationActivity.this,
                                                AgentRegistrationActivity.class);
                                        startActivity(i);
                                        finish();
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            agentSignUpClassView.setVisibility(View.VISIBLE);

                            Toast.makeText(AgentRegistrationActivity.this, agentSignUpResponse
                                    .getStatus().toString(),
                                    Toast
                                    .LENGTH_SHORT).show();
                            Toast.makeText(AgentRegistrationActivity.this, "Registration failed. " +
                                    "Please update your email address and try again.", Toast
                                    .LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadImageWithRxJava(InputStream inputStream, String imageName) {
        Observable<String> uploadImageObservable = Observable.fromCallable(() -> uploadToAmazonBucket(inputStream, imageName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subscription = uploadImageObservable.subscribe();
    }

    private String uploadToAmazonBucket(InputStream inputStream, String imageName) {
        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials
                (getResources().getString(R.string.amazon_access_key_id), getResources()
                        .getString(R.string.amazon_secret_access_key)));

        File file = new File(getCacheDir(), "cacheFileAppeal.srl");
        InputStreamToFileConverter.copy(inputStream, file);

        PutObjectRequest putObjectRequest = new PutObjectRequest
                (UploadType.TEST_BUCKET, imageName, file);

        s3Client.putObject(putObjectRequest);

        return null;
    }
}