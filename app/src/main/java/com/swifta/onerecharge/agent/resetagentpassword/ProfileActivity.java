package com.swifta.onerecharge.agent.resetagentpassword;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.swifta.onerecharge.ApplicationModule;
import com.swifta.onerecharge.R;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.Url;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements ResetAgentPasswordContract.View {
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

    @Inject
    ResetAgentPasswordPresenter mResetAgentPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        DaggerResetAgentPasswordComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .resetAgentPasswordModule(new ResetAgentPasswordModule(Url.BASE_URL, this))
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mResetAgentPasswordPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mResetAgentPasswordPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(ResetAgentPasswordContract.Presenter presenter) {
    }

    @Override
    public void showNoInternetMessage() {
        Toast.makeText(ProfileActivity.this, R.string.internet_error,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfullyChangedMessage(String message) {
        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyTextFields() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(ProfileActivity.this, R.string.password_reset_failed, Toast
                .LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.request_password_change)
    void changePassword() {

        String oldPassword = oldPasswordField.getText().toString();
        String newPassword = newPasswordField.getText().toString();

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
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext())) {
                showNoInternetMessage();
            } else {
                hidePasswordLayout();
                showProgressBar();

                mResetAgentPasswordPresenter.submitPasswordRequest(oldPassword, newPassword);
            }
        }
    }

    @Override
    public void hidePasswordLayout() {
        oldPasswordLayout.setVisibility(View.GONE);
        newPasswordLayout.setVisibility(View.GONE);
        changePasswordButton.setVisibility(View.GONE);
    }

    @Override
    public void showPasswordLayout() {
        oldPasswordLayout.setVisibility(View.VISIBLE);
        newPasswordLayout.setVisibility(View.VISIBLE);
        changePasswordButton.setVisibility(View.VISIBLE);
    }
}
