package com.swifta.onerecharge.resetagentpassword;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.util.AgentService;
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

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.old_password)
    TextInputEditText oldPasswordField;
    @BindView(R.id.new_password)
    TextInputEditText newPasswordField;

    @BindView(R.id.request_password_change)
    Button changePasswordButton;

    @BindView(R.id.old_password_layout)
    TextInputLayout oldPasswordLayout;
    @BindView(R.id.new_password_layout)
    TextInputLayout newPasswordLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    String oldPassword;
    String newPassword;
    String userId;
    String authenticationToken;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(getString(R.string
                .shared_preference_name), Context.MODE_PRIVATE);
    }

    @OnClick(R.id.request_password_change)
    void changePassword() {

        oldPassword = oldPasswordField.getText().toString();
        newPassword = newPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (newPassword.isEmpty()) {
            newPasswordLayout.setError(getString(R.string.password_empty_error));
            focusView = newPasswordLayout;
            cancel = true;
        } else {
            newPasswordLayout.setError(null);
        }

        if (oldPassword.isEmpty()) {
            oldPasswordLayout.setError(getString(R.string.password_empty_error));
            focusView = oldPasswordLayout;
            cancel = true;
        } else {
            oldPasswordLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext
                    ())) {
                Snackbar.make(newPasswordLayout, R.string.internet_error,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                hidePasswordLayout();
                showProgressBar();
                performPasswordChangeRequest();
            }
        }
    }

    private void performPasswordChangeRequest() {
        userId = getStoredUserId();
        authenticationToken = getStoredAuthToken();

        RequestPasswordData passwordData = new RequestPasswordData
                (userId, authenticationToken, oldPassword, newPassword);

        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        AgentService agentService = retrofit.create(AgentService.class);
        final Observable<AgentPassword> passwordObservable = agentService
                .changePassword(passwordData);

        passwordObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentPassword>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressBar();
                        showPasswordLayout();
                        clearPasswordFields();
                        Snackbar.make(newPasswordLayout, R.string.password_reset_failed, Snackbar
                                .LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentPassword agentPassword) {
                        hideProgressBar();
                        showPasswordLayout();
                        clearPasswordFields();
                        Snackbar.make(newPasswordLayout, agentPassword.getData().getMessage(),
                                Snackbar
                                        .LENGTH_SHORT).show();
                    }
                });
    }

    private void hidePasswordLayout() {
        oldPasswordLayout.setVisibility(View.GONE);
        newPasswordLayout.setVisibility(View.GONE);
        changePasswordButton.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showPasswordLayout() {
        oldPasswordLayout.setVisibility(View.VISIBLE);
        newPasswordLayout.setVisibility(View.VISIBLE);
        changePasswordButton.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void clearPasswordFields() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
    }

    private String getStoredUserId() {
        return sharedPreferences.getString(getString(R.string
                .saved_email_address), "null");
    }

    private String getStoredAuthToken() {
        return sharedPreferences.getString(getString(R.string
                .saved_auth_token), "null");
    }
}
