package com.swifta.onerecharge.customer.customerwallettopup.customerwallettopuppayment;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.cardpayment.card.requestmodel.ChargeObject;
import com.swifta.onerecharge.cardpayment.card.requestmodel.PaymentRequest;
import com.swifta.onerecharge.cardpayment.card.responsemodel.PaymentResponse;
import com.swifta.onerecharge.cardpayment.otp.requestmodel.OtpRequest;
import com.swifta.onerecharge.cardpayment.otp.responsemodel.OtpResponse;
import com.swifta.onerecharge.customer.customerwallettopup.customerwallettopuprequestmodel.CustomerWalletTopUpRequest;
import com.swifta.onerecharge.customer.customerwallettopup.customerwallettopupresponsemodel.CustomerWalletTopUpResponse;
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

public class CustomerWalletTopUpPaymentActivity extends AppCompatActivity {

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

    String phoneNumber, email, referenceId, customerToken, country;
    int amount;
    String cardNumber, monthValue, yearValue, cvv, cardPin;

    static final boolean IS_CARD_TRANSACTION = true;
    private static final int TRANSACTION_FAILED = 0;
    private static final int TRANSACTION_SUCCESSFUL = 1;
    private static final String PAYMENT_METHOD_ID = "2";
    private static final String AUTHORIZATION = "Bearer 755187d4-11bb-3eea-96ca-440884367b9c";
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

        phoneNumber = getIntent().getStringExtra("customer_telephone");
        amount = getIntent().getIntExtra("amount", 0);
        email = getIntent().getStringExtra("email");
        referenceId = getIntent().getStringExtra("customer_token");
        customerToken = getIntent().getStringExtra("reference_id");
        country = getIntent().getStringExtra("country");

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
                .performCardTransaction(AUTHORIZATION, paymentRequest);

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
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(PaymentResponse paymentResponse) {
                        progressBar.setVisibility(View.GONE);

                        if (paymentResponse.getStatus().equals("02")) {
                            displayOtpDialog(paymentResponse.getDescription(), paymentResponse
                                    .getDetails().getOtpref());
                        } else {
                            cardPaymentContainer.setVisibility(View.VISIBLE);
                            showResultDialog(paymentResponse.getDescription(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void displayOtpDialog(String description, String otpRef) {

        final EditText input = new EditText(CustomerWalletTopUpPaymentActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder dialog = new AlertDialog.Builder(CustomerWalletTopUpPaymentActivity
                .this);
        dialog.setCancelable(false)
                .setTitle("Please enter OTP")
                .setMessage(description)
                .setView(input)
                .setPositiveButton("Send OTP", (dialog1, id) -> {
                    final String otp = input.getText().toString();
                    if (otp.isEmpty()) {
                        displayOtpDialog(description, otpRef);
                        Toast.makeText(this, "Please enter an OTP", Toast.LENGTH_SHORT).show();
                    } else if (otp.length() != 6) {
                        displayOtpDialog(description, otpRef);
                        input.setText(otp);
                        Toast.makeText(this, "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!InternetConnectivity.isDeviceConnected(this)) {
                            displayOtpDialog(description, otpRef);
                            input.setText(otp);
                            Snackbar.make(cardNumberLayout, R.string.internet_error,
                                    Snackbar.LENGTH_SHORT).show();
                        } else {
                            performOtpTransaction(otpRef, otp);
                        }
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

        com.swifta.onerecharge.cardpayment.otp.requestmodel.ChargeObject chargeObject = new com
                .swifta.onerecharge.cardpayment.otp.requestmodel.ChargeObject(otpRef, otp);

        OtpRequest otpRequest = new OtpRequest(chargeObject, PAYMENT_METHOD_ID);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.MFISA_BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<OtpResponse> authorizeOtp = customerService.authorizeWithOtp
                (AUTHORIZATION, otpRequest);

        authorizeOtp.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<OtpResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //performWalletTopUp("", "", false);
                        progressBar.setVisibility(View.GONE);
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(OtpResponse otpResponse) {
                        if (otpResponse.getStatus().equals("01")) {
                            performWalletTopUp(otpResponse.getDescription(), otpResponse
                                    .getDetails().getMfisaTxnId(), true);
                        } else {
                            //  performWalletTopUp("", "", false);
                            progressBar.setVisibility(View.GONE);
                            cardPaymentContainer.setVisibility(View.VISIBLE);
                            showResultDialog(otpResponse.getDescription(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void performWalletTopUp(String description, String transactionId, boolean
            isSuccessful) {

        CustomerWalletTopUpRequest walletTopUpRequest = new CustomerWalletTopUpRequest("mFISA",
                amount, customerToken, description, isSuccessful, transactionId, referenceId,
                email);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<CustomerWalletTopUpResponse> quickRecharge = customerService.topWalletUp
                (walletTopUpRequest);

        quickRecharge.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerWalletTopUpResponse>() {

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
                    public void onNext(CustomerWalletTopUpResponse walletTopUpResponse) {

                        if (walletTopUpResponse.getStatus() == 1) {
                            showRechargeSuccessfulDialog();
                        } else {
                            showResultDialog(walletTopUpResponse.getData().getMessage(),
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

    private void showRechargeSuccessfulDialog() {

        final ImageView imageView = new ImageView(CustomerWalletTopUpPaymentActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        imageView.setBackgroundResource(R.drawable.ic_check_circle_green_700_36dp);

        AlertDialog.Builder dialog = new AlertDialog.Builder(CustomerWalletTopUpPaymentActivity
                .this);
        dialog.setCancelable(false)
                .setTitle("Recharge Successful!")
                .setMessage(TRANSACTION_SUCCESSFUL_MESSAGE)
                .setView(imageView)
                .setPositiveButton("OK", (dialog1, id) -> {
                    finish();
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}