package com.swifta.onerecharge.agentregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.AgentActivity;
import com.swifta.onerecharge.util.AgentService;
import com.swifta.onerecharge.util.EmailRegexValidator;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.Url;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AgentRegistrationActivity extends AppCompatActivity {
    // Login
    @BindView(R.id.agent_login_email_address)
    TextInputEditText loginEmailAddressField;
    @BindView(R.id.agent_login_password)
    TextInputEditText passwordField;

    @BindView(R.id.agent_login_email_address_layout)
    TextInputLayout emailAddressFieldLayout;
    @BindView(R.id.agent_login_password_layout)
    TextInputLayout passwordFieldLayout;
    @BindView(R.id.login_progressBar)
    ProgressBar agentLoginProgress;
    @BindView(R.id.agent_log_in)
    LinearLayout agentLogInView;

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
    @BindView(R.id.agent_signup_alternate_phone_layout)
    TextInputLayout alternatePhoneFieldLayout;
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
    @BindView(R.id.agent_upload_details)
    LinearLayout agentSignUpUploadView;

    // Sign Up - Agent class details
    @BindView(R.id.individual_agent)
    RadioButton individualAgent;
    @BindView(R.id.business_agent)
    RadioButton businessAgent;
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

    // Sign up fields for personal details
    String lastName;
    String otherNames;
    String signUpEmail;
    String personalPhoneNumber;
    String alternatePhoneNumber;

    // Sign up fields for company details
    String companyName;
    String companyRegistrationNumber;
    String companyPhoneNumber;
    String contactName;
    String contactPhoneNumber;

    // Sign up fields for upload details
    String businessCacUrl;
    String meansOfIdentificationUrl;
    String proofOfAddressUrl;

    // Sign up fields for agent class details
    String agentType;
    String agentClass;
    String referralId;
    boolean isTermsAndConditionsAccepted;

    private String authToken;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_registration);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(getString(R
                .string.shared_preference_name), Context.MODE_PRIVATE);
    }

    @OnClick(R.id.agent_signup_button)
    void launchAgentSignUpView() {
        agentLogInView.setVisibility(View.GONE);
        agentSignUpPersonalDetailsView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.agent_login_button)
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

                        Snackbar.make(agentLogInView, R.string.agent_login_error,
                                Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentRegistration agentRegistration) {
                        if (agentRegistration.getStatus() == 1) {
                            authToken = agentRegistration.getData();
                            saveAgentLoginResult();
                            Intent intent = new Intent
                                    (AgentRegistrationActivity.this,
                                            AgentActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            agentLoginProgress.setVisibility(View.GONE);
                            agentLogInView.setVisibility(View.VISIBLE);

                            Snackbar.make(agentLogInView,
                                    agentRegistration.getData(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick(R.id.agent_personal_login_button)
    void launchAgentLogInView() {
        agentSignUpPersonalDetailsView.setVisibility(View.GONE);
        agentLogInView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.agent_first_next_button)
    void switchToCompanyDetailsView() {

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
            agentSignUpCompanyView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.agent_back_to_personal_details_view_button)
    void backToPersonalDetailsView() {
        agentSignUpCompanyView.setVisibility(View.GONE);
        agentSignUpPersonalDetailsView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.switch_to_uploads_view_button)
    void switchToUploadCertificatesView() {
        companyName = companyNameField.getText().toString();
        companyRegistrationNumber = companyRegistrationNumberField.getText()
                .toString();
        companyPhoneNumber = companyPhoneNumberField.getText().toString();
        contactName = contactNameField.getText().toString();
        contactPhoneNumber = contactPhoneNumberField.getText().toString();

        agentSignUpCompanyView.setVisibility(View.GONE);
        agentSignUpUploadView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.business_certificate_button)
    void uploadBusinessCertificate() {
        // Get image from device
        // check internet
        // upload to amazon
        // get url to file on amazon
    }

    @OnClick(R.id.upload_identification_button)
    void uploadMeansOfIdentification() {
        // Get image from device
        // check internet
        // upload to amazon
        // get url to file on amazon
    }

    @OnClick(R.id.upload_proof_button)
    void uploadProof() {
        // Get image from device
        // check internet
        // upload to amazon
        // get url to file on amazon
    }

    @OnClick(R.id.agent_back_to_company_details_view_button)
    void backToCompanyDetailsView() {
        agentSignUpUploadView.setVisibility(View.GONE);
        agentSignUpCompanyView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.switch_to_agent_class_view_button)
    void switchToAgentClassView() {

//        if (!isBusinessUploadSuccessful) {
//            Snackbar.make(agentSignUpUploadView, R.string.upload_cac_certificate, Snackbar
//                    .LENGTH_SHORT)
//                    .show();
//        } else if (!identificationUploadSuccessful) {
//            Snackbar.make(agentSignUpUploadView, R.string.upload_means_of_identification, Snackbar
//                    .LENGTH_SHORT).show();
//        } else if (!proofUploadSuccessful) {
//            Snackbar.make(agentSignUpUploadView, R.string.upload_proof_of_address, Snackbar
//                    .LENGTH_SHORT).show();
//        } else {
//            agentSignUpUploadView.setVisibility(View.GONE);
//            agentSignUpClassView.setVisibility(View.VISIBLE);
//        }

        agentSignUpUploadView.setVisibility(View.GONE);
        agentSignUpClassView.setVisibility(View.VISIBLE);
        referralIdField.requestFocus();
    }

    @OnClick(R.id.agent_back_to_upload_details_view_button)
    void backToUploadDetailsView() {
        agentSignUpClassView.setVisibility(View.GONE);
        agentSignUpUploadView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.submit_and_signup_button)
    void signAgentIn() {

        if (individualAgent.isChecked()) {
            agentType = getString(R.string.individual_agent);
        } else if (businessAgent.isChecked()) {
            agentType = getString(R.string.business_agent);
        }

        if (discountBasedAgent.isChecked()) {
            agentClass = getString(R.string.discount_based_agent);
        } else if (commissionBasedAgent.isChecked()) {
            agentClass = getString(R.string.commission_based_agent);
        }

        referralId = referralIdField.getText().toString();

        if (!termsAndConditions.isChecked()) {
            Snackbar.make(agentSignUpClassView, R.string.please_accept_terms_and_conditions, Snackbar
                    .LENGTH_SHORT)
                    .show();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext
                    ())) {
                Snackbar.make(agentLogInView, R.string.internet_error, Snackbar
                        .LENGTH_SHORT).show();
            } else {
                agentSignUpClassView.setVisibility(View.GONE);
                agentLoginProgress.setVisibility(View.VISIBLE);
                // TODO: performSignUpNetworkRequest
            }
        }
    }

    private void saveAgentLoginResult() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_email_address),
                loginEmailAddress);
        editor.putString(getResources().getString(R.string.saved_auth_token), authToken);
        editor.apply();
    }
}