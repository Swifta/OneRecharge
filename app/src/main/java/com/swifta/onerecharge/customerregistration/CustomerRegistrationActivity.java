package com.swifta.onerecharge.customerregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.swifta.onerecharge.CustomerActivity;
import com.swifta.onerecharge.R;
import com.swifta.onerecharge.customerregistration.loginresponsemodel.CustomerRegistration;
import com.swifta.onerecharge.customerregistration.loginresponsemodel.Data;
import com.swifta.onerecharge.customerregistration.registerresponsemodel.CustomerSignUpResponse;
import com.swifta.onerecharge.util.CustomerService;
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

public class CustomerRegistrationActivity extends AppCompatActivity {
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
    ProgressBar loginProgress;
    @BindView(R.id.user_log_in)
    LinearLayout logInView;
    @BindView(R.id.customer_signup)
    LinearLayout signUpView;

    // SignUp
    @BindView(R.id.signup_fullname)
    TextInputEditText signupFullNameField;
    @BindView(R.id.signup_email_address)
    TextInputEditText signupEmailAddressField;
    @BindView(R.id.signup_telephone)
    TextInputEditText signupPhoneNumberField;
    @BindView(R.id.signup_password)
    TextInputEditText signupPasswordField;

    @BindView(R.id.signup_fullname_layout)
    TextInputLayout signupFullnameFieldLayout;
    @BindView(R.id.signup_email_address_layout)
    TextInputLayout signupEmailAddressLayout;
    @BindView(R.id.signup_telephone_layout)
    TextInputLayout signupPhoneNumberLayout;
    @BindView(R.id.signup_password_layout)
    TextInputLayout signupPasswordFieldLayout;

    // Login Fields
    String loginEmailAddress;
    String password;

    // Signup Fields
    String signupFullname;
    String signupEmailAddress;
    String signupPassword;
    String signupPhoneNumber;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(getString(R.string
                .customer_shared_preference_name), Context.MODE_PRIVATE);

        getSupportActionBar().hide();
    }

    @OnClick(R.id.user_signup_button)
    void launchCustomerSignUpView() {
        logInView.setVisibility(View.GONE);
        signUpView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.customer_login_button)
    void launchCustomerLoginView() {
        signUpView.setVisibility(View.GONE);
        logInView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.user_login_button)
    void logCustomerIn() {

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
                Snackbar.make(logInView, R.string.internet_error, Snackbar
                        .LENGTH_SHORT).show();
            } else {
                logInView.setVisibility(View.GONE);
                loginProgress.setVisibility(View.VISIBLE);
                performLoginNetworkRequest();
            }
        }
    }

    @OnClick(R.id.customer_signup_button)
    void signCustomerUp() {

        signupFullnameFieldLayout.setError(null);
        signupEmailAddressLayout.setError(null);
        signupPhoneNumberLayout.setError(null);
        signupPasswordFieldLayout.setError(null);

        signupFullname = signupFullNameField.getText().toString();
        signupEmailAddress = signupEmailAddressField.getText().toString();
        signupPhoneNumber = signupPhoneNumberField.getText().toString();
        signupPassword = signupPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (signupPassword.isEmpty()) {
            signupPasswordFieldLayout.setError(getString(R.string.password_empty_error));
            focusView = signupPasswordFieldLayout;
            cancel = true;
        } else {
            signupPasswordFieldLayout.setError(null);
        }

        if (signupPhoneNumber.isEmpty()) {
            signupPhoneNumberLayout.setError(getString(R.string.phone_number_empty_error));
            focusView = signupPhoneNumberLayout;
            cancel = true;
        } else {
            signupPhoneNumberLayout.setError(null);
        }

        if (signupEmailAddress.isEmpty()) {
            signupEmailAddressLayout.setError(getResources().getString(R
                    .string.email_empty_error));
            focusView = signupEmailAddressLayout;
            cancel = true;
        } else if (!EmailRegexValidator.isValidEmail(signupEmailAddress)) {
            signupEmailAddressLayout.setError(getString(R.string.email_invalid_error));
            focusView = signupEmailAddressLayout;
            cancel = true;
        } else {
            signupEmailAddressLayout.setError(null);
        }

        if (signupFullname.isEmpty()) {
            signupFullnameFieldLayout.setError(getString(R.string.fullname_empty_error));
            focusView = signupFullnameFieldLayout;
            cancel = true;
        } else {
            signupFullnameFieldLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext
                    ())) {
                Snackbar.make(logInView, R.string.internet_error, Snackbar
                        .LENGTH_SHORT).show();
            } else {
                signUpView.setVisibility(View.GONE);
                loginProgress.setVisibility(View.VISIBLE);
                performSignupNetworkRequest();
            }
        }
    }

    private void performLoginNetworkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit
                .create(CustomerService.class);
        final Observable<CustomerRegistration> agent = customerService.logCustomerIn
                ("tPTpR4PIYtoFSiblO1P9Xn0ttGsWE9wS", "oZFLQVYpRO9Ur8i6H8Q1J5c3RMgt0fb0",
                        loginEmailAddress, password);

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerRegistration>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginProgress.setVisibility(View.GONE);
                        logInView.setVisibility(View.VISIBLE);

                        Snackbar.make(logInView, R.string.user_login_error,
                                Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CustomerRegistration customerRegistration) {
                        if (customerRegistration.getStatus() == 1) {
                            saveCustomerLoginResult(customerRegistration.getData());
                            switchToCustomerActivity();
                        } else {
                            loginProgress.setVisibility(View.GONE);
                            logInView.setVisibility(View.VISIBLE);

                            Snackbar.make(logInView, customerRegistration.getMessage(), Snackbar
                                    .LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void performSignupNetworkRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit
                .create(CustomerService.class);
        final Observable<CustomerSignUpResponse> customer = customerService.signCustomerUp
                ("Ksb9Fjaho7p7y2bQcN3QiYSPAitYoWSO", "oZFLQVYpRO9Ur8i6H8Q1J5c3RMgt0fb0",
                        signupFullname, signupEmailAddress, signupPhoneNumber, signupPassword);

        customer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerSignUpResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginProgress.setVisibility(View.GONE);
                        signUpView.setVisibility(View.VISIBLE);

                        Snackbar.make(signUpView, R.string.user_signup_error, Snackbar
                                .LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CustomerSignUpResponse customerSignUpResponse) {
                        if (customerSignUpResponse.getStatus() == 1) {
                            saveCustomerSignUpResult(customerSignUpResponse.getData());
                            switchToCustomerActivity();
                        } else {
                            loginProgress.setVisibility(View.GONE);
                            signUpView.setVisibility(View.VISIBLE);

                            Snackbar.make(signUpView, R.string.user_signup_error, Snackbar
                                    .LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveCustomerLoginResult(Data data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_customer_auth_token), data
                .getToken());
        editor.putString(getResources().getString(R.string.saved_customer_id), data.getProfile()
                .getId());
        editor.putString(getResources().getString(R.string.saved_customer_fullname), data
                .getProfile().getCustomerFullname());
        editor.putString(getResources().getString(R.string.saved_customer_email_address), data
                .getProfile().getCustomerEmail());
        editor.putString(getResources().getString(R.string.saved_customer_telephone), data
                .getProfile().getCustomerTelephone());
        editor.putString(getResources().getString(R.string.saved_customer_password), password);
        editor.putString(getResources().getString(R.string.saved_customer_linked_agent_id), data
                .getProfile().getLinkedAgent().getId());
        editor.putString(getResources().getString(R.string.saved_customer_linked_agent_country),
                data.getProfile().getLinkedAgent().getCountry());
        editor.putInt(getResources().getString(R.string.saved_customer_promo), data
                .getProfile().getWallet().getPromo());
        editor.putInt(getResources().getString(R.string.saved_customer_discount), data
                .getProfile().getWallet().getDiscounts());
        editor.putInt(getResources().getString(R.string.saved_customer_recharged), data
                .getProfile().getWallet().getRecharged());
        editor.putInt(getResources().getString(R.string.saved_customer_balance), data
                .getProfile().getWallet().getBalance());
        editor.putString(getResources().getString(R.string.saved_customer_created), data
                .getProfile().getCustomerCreated());

        editor.apply();
    }

    private void saveCustomerSignUpResult(com.swifta.onerecharge.customerregistration.registerresponsemodel.Data data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(getResources().getString(R.string.saved_customer_auth_token), data
//                .getToken());
        editor.putString(getResources().getString(R.string.saved_customer_id), data.getId());
        editor.putString(getResources().getString(R.string.saved_customer_fullname), data
                .getCustomerFullname());
        editor.putString(getResources().getString(R.string.saved_customer_email_address), data
                .getCustomerEmail());
        editor.putString(getResources().getString(R.string.saved_customer_telephone), data
                .getCustomerTelephone());
        editor.putString(getResources().getString(R.string.saved_customer_password), data
                .getCustomerPassword());
        editor.putString(getResources().getString(R.string.saved_customer_linked_agent_id), data
                .getLinkedAgent());
        editor.putString(getResources().getString(R.string.saved_customer_linked_agent_country),
                data.getMyCountry());
        editor.putInt(getResources().getString(R.string.saved_customer_promo), data.getWallet()
                .getPromo());
        editor.putInt(getResources().getString(R.string.saved_customer_discount), data.getWallet
                ().getDiscounts());
        editor.putInt(getResources().getString(R.string.saved_customer_recharged), data.getWallet
                ().getRecharged());
        editor.putInt(getResources().getString(R.string.saved_customer_balance), data.getWallet()
                .getBalance());
        editor.putString(getResources().getString(R.string.saved_customer_created), data
                .getCustomerCreated());

        editor.apply();
    }

    private void switchToCustomerActivity() {
        Intent i = new Intent(CustomerRegistrationActivity.this, CustomerActivity.class);
        startActivity(i);
    }

}
