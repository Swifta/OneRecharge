package com.swifta.onerecharge.agentregistration;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agentplatform.AgentActivity;
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
    @BindView(R.id.agent_login_email_address)
    TextInputEditText emailAddressField;
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
    @BindView(R.id.agent_personal_details)
    LinearLayout agentSignUpView;

    String emailAddress;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_registration);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
    }

    @OnClick(R.id.agent_signup_button)
    void launchAgentSignUpView() {
        agentLogInView.setVisibility(View.GONE);
        agentSignUpView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.agent_login_button)
    void logAgentIn() {

        emailAddressFieldLayout.setError(null);
        passwordFieldLayout.setError(null);

        emailAddress = emailAddressField.getText().toString();
        password = passwordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (password.isEmpty()) {
            passwordFieldLayout.setError(getResources().getString(
                    R.string.password_empty_error));
            focusView = passwordFieldLayout;
            cancel = true;
        } else {
            passwordFieldLayout.setError(null);
        }

        if (emailAddress.isEmpty()) {
            emailAddressFieldLayout.setError(getResources().getString(R
                    .string.email_empty_error));
            focusView = emailAddressFieldLayout;
            cancel = true;
        } else if (!EmailRegexValidator.isValidEmail(emailAddress)) {
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
                performNetworkValidation();
            }
        }
    }

    private void performNetworkValidation() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        AgentRegistrationService agentRegistrationService = retrofit
                .create(AgentRegistrationService.class);
        final Observable<AgentRegistration> agent = agentRegistrationService
                .logAgentIn(emailAddress, password);

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentRegistration>() {
                    @Override
                    public void onCompleted() {
                        agentLoginProgress.setVisibility(View.GONE);
                        agentLogInView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(agentLogInView, R.string.agent_login_error,
                                Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentRegistration agentRegistration) {
                        if (agentRegistration.getStatus() == 1) {
                            emailAddressField.setText("");
                            passwordField.setText("");
                            Intent intent = new Intent
                                    (AgentRegistrationActivity.this,
                                            AgentActivity.class);
                            startActivity(intent);
                        } else {
                            Snackbar.make(agentLogInView,
                                    agentRegistration.getData(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
