package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.util.InternetConnectivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerQuickRechargePaymentActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.card_number)
    TextInputEditText cardNumberText;
    @BindView(R.id.card_number_layout)
    TextInputLayout cardNumberLayout;
    @BindView(R.id.expiry_date_month)
    TextInputEditText expiryDateMonthText;
    @BindView(R.id.expiry_date_month_layout)
    TextInputLayout expiryDateMonthLayout;
    @BindView(R.id.expiry_date_year)
    TextInputEditText expiryDateYearText;
    @BindView(R.id.expiry_date_year_layout)
    TextInputLayout expiryDateYearLayout;
    @BindView(R.id.cvv)
    TextInputEditText cvvText;
    @BindView(R.id.cvv_layout)
    TextInputLayout cvvLayout;
    @BindView(R.id.card_pin)
    TextInputEditText cardPinText;
    @BindView(R.id.card_pin_layout)
    TextInputLayout cardPinLayout;
    @BindView(R.id.quick_recharge_payment_button)
    Button quickRechargeButton;
    @BindView(R.id.card_payment_container)
    LinearLayout cardPaymentContainer;

    String phoneNumber, country, networkProvider, email;
    int amount;
    String cardNumber, monthValue, yearValue, cvv, cardPin;
    static final boolean IS_CARD_TRANSACTION = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_quick_recharge_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        phoneNumber = getIntent().getStringExtra("phone_number");
        amount = getIntent().getIntExtra("amount", 0);
        country = getIntent().getStringExtra("country");
        networkProvider = getIntent().getStringExtra("network_provider");
        email = getIntent().getStringExtra("email");

        quickRechargeButton.setText("Pay " + getCountryCode(country) + " " + amount);
    }

    @OnClick(R.id.quick_recharge_payment_button)
    void processPayment() {
        cardNumber = cardNumberText.getText().toString();
        monthValue = expiryDateMonthText.getText().toString();
        yearValue = expiryDateYearText.getText().toString();
        cvv = cvvText.getText().toString();
        cardPin = cardPinText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cardPin.isEmpty()) {
            cardPinLayout.setError("Enter a 4 digit PIN");
            focusView = cardPinLayout;
            cancel = true;
        } else if (cardPin.length() != 4) {
            cardPinLayout.setError("Enter a valid PIN");
            focusView = cardPinLayout;
            cancel = true;
        } else {
            cardPinLayout.setError(null);
        }

        if (cvv.isEmpty()) {
            cvvLayout.setError("Enter the card code");
            focusView = cvvLayout;
            cancel = true;
        } else if (cvv.length() != 3) {
            cvvLayout.setError("Enter a valid card code");
            focusView = cvvLayout;
            cancel = true;
        } else {
            cvvLayout.setError(null);
        }

        if (yearValue.isEmpty()) {
            expiryDateYearLayout.setError("Enter the expiry year");
            focusView = expiryDateMonthLayout;
            cancel = true;
        } else if (yearValue.length() != 2) {
            expiryDateYearLayout.setError("Enter a valid expiry year");
            focusView = expiryDateYearLayout;
            cancel = true;
        } else {
            expiryDateYearLayout.setError(null);
        }

        if (monthValue.isEmpty()) {
            expiryDateMonthLayout.setError("Enter the expiry month");
            focusView = expiryDateMonthLayout;
            cancel = true;
        } else if (monthValue.length() != 2) {
            expiryDateMonthLayout.setError("Enter a valid expiry month");
            focusView = expiryDateMonthLayout;
            cancel = true;
        } else {
            expiryDateMonthLayout.setError(null);
        }

        if (cardNumber.isEmpty()) {
            cardNumberLayout.setError("Enter your card number");
            focusView = cardNumberLayout;
            cancel = true;
        } else if (cardNumber.length() < 12) {
            cardNumberLayout.setError("Enter a valid card number");
            focusView = cardNumberLayout;
            cancel = true;
        } else {
            cardNumberLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(this)) {
                Snackbar.make(cardNumberLayout, R.string.internet_error,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                performCardTransaction();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getCountryCode(String countryName) {
        String shortCode = "";
        switch (countryName) {
            case "Nigeria":
                shortCode = "NGN";
                break;
            case "Ghana":
                shortCode = "GHS";
                break;
        }
        return shortCode;
    }

    private void performCardTransaction() {
        cardPaymentContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }
}
