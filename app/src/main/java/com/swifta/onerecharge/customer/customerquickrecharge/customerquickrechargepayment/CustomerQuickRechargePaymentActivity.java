package com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.card.ChargeObject;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.card.PaymentRequest;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.card.PaymentResponse;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.otp.OtpRequest;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargerequestmodel.CustomerQuickRechargeRequest;
import com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargeresponsemodel.CustomerQuickRechargeResponse;
import com.swifta.onerecharge.util.CustomerService;
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

    RechargeResponseFragment successfulFragment;

    String phoneNumber, country, networkProvider, email;
    int amount;
    String cardNumber, monthValue, yearValue, cvv, cardPin;

    static final boolean IS_CARD_TRANSACTION = true;
    private static final int TRANSACTION_FAILED = 0;
    private static final int TRANSACTION_SUCCESSFUL = 1;
    private static final String PAYMENT_METHOD_ID = "1";
    private static final String TRANSACTION_SUCCESSFUL_MESSAGE = "Transaction request sent " +
            "successfully!";
    private static final String TRANSACTION_ERROR_MESSAGE = "Transaction " +
            "unsuccessful. Please try again.";

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

        quickRechargeButton.setText("Pay " + getCountryCurrencyCode(country) + " " + amount);
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
        } else if (Integer.valueOf(monthValue) > 12) {
            expiryDateMonthLayout.setError("Enter a valid expiry month");
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

    private String getCountryCurrencyCode(String countryName) {
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

        ChargeObject chargeObject = new ChargeObject(cardNumber, monthValue, yearValue, cvv,
                cardPin);
        String referenceId = phoneNumber + "|" + email;
        String amountToString = String.valueOf(amount);

        PaymentRequest paymentRequest = new PaymentRequest(chargeObject, amountToString,
                PAYMENT_METHOD_ID, getCountryCurrencyCode(country), referenceId);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.MFISA_BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<PaymentResponse> processCardTransaction = customerService
                .performCardTransaction(paymentRequest);

        processCardTransaction.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PaymentResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
//                        cardPaymentContainer.setVisibility(View.VISIBLE);
//                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                        displayOtpDialog("");
                    }

                    @Override
                    public void onNext(PaymentResponse paymentResponse) {
                        progressBar.setVisibility(View.GONE);
                        displayOtpDialog(paymentResponse.getDetails().getOtpref());
                    }
                });
    }

    private void displayOtpDialog(String otpRef) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CustomerQuickRechargePaymentActivity
                .this);
        dialog.setCancelable(false)
                .setTitle("Please enter OTP")
                .setMessage("Transaction initiated successfully. Please enter the OTP sent to " +
                        "your phone to complete the transaction.")
                .setPositiveButton("Send OTP", (dialog1, id) -> {
                    if (!InternetConnectivity.isDeviceConnected(this)) {
                        Snackbar.make(cardNumberLayout, R.string.internet_error,
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        //  performOtpTransaction(otpRef, otp);
                    }
                })
                .setNegativeButton("Cancel", (dialog12, which) -> {
                    dialog12.dismiss();
                    cardPaymentContainer.setVisibility(View.VISIBLE);
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void performOtpTransaction(String otpRef, String otp) {
        progressBar.setVisibility(View.VISIBLE);

        com.swifta.onerecharge.customer.customerquickrecharge.customerquickrechargepayment.otp
                .ChargeObject chargeObject = new com.swifta.onerecharge.customer
                .customerquickrecharge.customerquickrechargepayment.otp.ChargeObject(otpRef, otp);

        OtpRequest otpRequest = new OtpRequest(chargeObject, PAYMENT_METHOD_ID);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.MFISA_BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<PaymentResponse> authorizeOtp = customerService.authorizeWithOtp
                (otpRequest);

        authorizeOtp.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PaymentResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(PaymentResponse paymentResponse) {
                        performQuickRecharge();
                    }
                });
    }

    private void performQuickRecharge() {

        CustomerQuickRechargeRequest walletQuickRechargeRequest = new
                CustomerQuickRechargeRequest(phoneNumber, amount, networkProvider,
                IS_CARD_TRANSACTION, email);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<CustomerQuickRechargeResponse> quickRecharge =
                customerService.performCustomerQuickRechargeFromWallet
                        ("sMHuflOVZYEuCvXW1SGWmIMoj0aV5q1D", "oZFLQVYpRO9Ur8i6H8Q1J5c3RMgt0fb0",
                                walletQuickRechargeRequest);

        quickRecharge.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerQuickRechargeResponse>() {

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(CustomerQuickRechargeResponse quickRechargeResponse) {

                        if (quickRechargeResponse.getStatus() == 1) {
                            showResultDialog(TRANSACTION_SUCCESSFUL_MESSAGE, TRANSACTION_SUCCESSFUL);
                            clearInputFields();
                            finish();
                        } else if (quickRechargeResponse.getStatus() == 0) {
                            showResultDialog(quickRechargeResponse.getData().getMessage(),
                                    TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void showResultDialog(String message, int status) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        successfulFragment = RechargeResponseFragment.newInstance(message, status);
        successfulFragment.show(fragmentManager, "");
    }

    private void clearInputFields() {
        cardNumberText.setText("");
        expiryDateMonthText.setText("");
        expiryDateYearText.setText("");
        cvvText.setText("");
        cardPinText.setText("");
    }
}
