package com.swifta.onerecharge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.swifta.onerecharge.util.EmailRegexValidator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerRegistrationActivity extends AppCompatActivity {
    @BindView(R.id.first_name)
    EditText firstNameField;
    @BindView(R.id.last_name)
    EditText lastNameField;
    @BindView(R.id.email)
    EditText emailField;
    @BindView(R.id.password)
    EditText passwordField;
    @BindView(R.id.personal_details)
    LinearLayout personalDetailsView;
    @BindView(R.id.card_details)
    LinearLayout cardDetailsView;

    String firstName;
    String lastName;
    String email;
    String password;

    private EmailRegexValidator emailRegexValidator;
    private int shortAnimationDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
        personalDetailsView.setVisibility(View.VISIBLE);
        cardDetailsView.setVisibility(View.GONE);

        emailRegexValidator = new EmailRegexValidator();
        shortAnimationDuration = getResources().getInteger(android.R.integer
                .config_shortAnimTime);
    }

    @OnClick(R.id.password)
    void toggleVisibility() {
        if (passwordField.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            hidePassword();
        } else {
            showPassword();
        }
        passwordField.setSelection(passwordField.length());
    }

    private void showPassword() {
        passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R
                .drawable.ic_action_visibility, 0);
    }

    private void hidePassword() {
        passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                .TYPE_TEXT_VARIATION_PASSWORD);
        passwordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R
                .drawable.ic_action_visibility_off, 0);
    }

    @OnClick(R.id.save_and_continue)
    void saveInfo() {
        validateFields();
        saveAnswers();
        showCardDetailsView();
    }

    private void validateFields() {
        validateEmptyField(firstNameField, getResources().getString(R.string.first_name_empty_error));
        validateEmptyField(lastNameField, getResources().getString(R.string
                .last_name_empty_error));
        validateEmail();
        validateEmptyField(passwordField, getResources().getString(R.string
                .password_empty_error));
    }

    private void validateEmptyField(EditText inputField, String error) {
        if (inputField.getText().length() == 0) {
            inputField.setError(error);
        } else {
            inputField.setError(null);
        }
    }

    private void validateEmail() {
        validateEmptyField(emailField, getResources().getString(R.string
                .email_empty_error));

        if (!emailRegexValidator.isValidEmail(emailField.getText().toString
                ())) {
            emailField.setError(getResources().getString(R.string.email_invalid_error));
        } else {
            emailField.setError(null);
        }
    }

    private void saveAnswers() {
        firstName = firstNameField.getText().toString();
        lastName = lastNameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
    }

    private void showCardDetailsView() {

        cardDetailsView.setAlpha(0f);
        cardDetailsView.setVisibility(View.VISIBLE);

        cardDetailsView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        personalDetailsView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        personalDetailsView.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick(R.id.back)
    void showPersonalDetailsView() {

        personalDetailsView.setAlpha(0f);
        personalDetailsView.setVisibility(View.VISIBLE);

        personalDetailsView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        cardDetailsView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cardDetailsView.setVisibility(View.GONE);
                    }
                });
    }
}
