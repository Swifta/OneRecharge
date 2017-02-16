package com.swifta.onerecharge.customer.customerwallettopup.customerwallettopuppayment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.cardpayment.card.requestmodel.ChargeObject;
import com.swifta.onerecharge.cardpayment.card.requestmodel.PaymentRequest;
import com.swifta.onerecharge.cardpayment.card.responsemodel.PaymentResponse;
import com.swifta.onerecharge.cardpayment.otp.requestmodel.OtpRequest;
import com.swifta.onerecharge.cardpayment.otp.responsemodel.OtpResponse;
import com.swifta.onerecharge.customer.CustomerActivity;
import com.swifta.onerecharge.customer.customerwallettopup.customerwallettopuprequestmodel.CustomerWalletTopUpRequest;
import com.swifta.onerecharge.customer.customerwallettopup.customerwallettopupresponsemodel.CustomerWalletTopUpResponse;
import com.swifta.onerecharge.util.CustomerService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.MfisaService;
import com.swifta.onerecharge.util.RandomNumberGenerator;
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

    @BindView(R.id.otp_message)
    TextView otpMessage;
    @BindView(R.id.otp_text)
    TextInputEditText otpText;
    @BindView(R.id.otp_text_layout)
    TextInputLayout otpTextLayout;
    @BindView(R.id.otp_container)
    LinearLayout otpContainer;
    @BindView(R.id.otp_progress_bar)
    ProgressBar otpProgressBar;

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
    Button customerWalletPaymentButton;
    @BindView(R.id.card_payment_container)
    HorizontalScrollView cardPaymentContainer;
    @BindView(R.id.credit_card_layout)
    LinearLayout creditCardLayout;

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.credit_card_image)
    ImageView creditCardTypeImage;
    @BindView(R.id.credit_card_number_text)
    TextView creditCardNumberText;
    @BindView(R.id.credit_card_month)
    TextView creditCardMonthText;
    @BindView(R.id.credit_card_year)
    TextView creditCardYearText;
    @BindView(R.id.credit_card_cvv)
    TextView creditCardCvvText;

    @BindView(R.id.activity_customer_quick_recharge)
    ScrollView scrollView;

    @BindView(R.id.otp_layout_content)
    LinearLayout otpLayoutContent;

    RechargeResponseFragment successfulFragment;

    FragmentManager fragmentManager;

    private SharedPreferences sharedPreferences;

    String email, referenceId, customerToken, country, otpValue;
    int amount;
    String cardNumber, monthValue, yearValue, cvv, cardPin;

    private static final String CREDIT_CARD_DEFAULT_TEXT = "XXXX  XXXX  XXXX  XXXX  XXXX";

    private static final int TRANSACTION_FAILED = 0;
    private static final String AUTHORIZATION = "Bearer 755187d4-11bb-3eea-96ca-440884367b9c";
    private static final String TRANSACTION_SUCCESSFUL_MESSAGE = "Transaction request sent " +
            "successfully!";
    private static final String PAYMENT_METHOD_ID = "2";
    private static final String TRANSACTION_ERROR_MESSAGE = "Transaction " +
            "unsuccessful. Please try again.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_quick_recharge_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        sharedPreferences = getSharedPreferences(getString(R.string
                .customer_shared_preference_name), Context.MODE_PRIVATE);

        getDataFromBundle();

        if (isOtpTransaction()) {
            switchToOtpView();
        } else {
            imageView.setImageDrawable(getCreditCardBackgroundBitmapDrawable());

            referenceId = RandomNumberGenerator.getRandomString(12);

            customerWalletPaymentButton.setText("Pay " + getCountryCurrencyCode(country) + " " +
                    amount);

            addCardNumberTextChangedListener();

            addExpiryDateMonthTextChangedListener();

            addExpiryDateYearTextChangedListener();

            addCvvTextChangedListener();
        }
    }

    private void getDataFromBundle() {
        amount = getIntent().getIntExtra("amount", 0);
        email = getIntent().getStringExtra("email");
        customerToken = getIntent().getStringExtra("customer_token");
        country = getIntent().getStringExtra("country");
    }

    private boolean isOtpTransaction() {
        return sharedPreferences.getBoolean(getResources().getString(R.string
                .saved_customer_wallet_otp_status), false);
    }

    private void switchToOtpView() {
        scrollView.setVisibility(View.GONE);
        otpContainer.setVisibility(View.VISIBLE);
        otpMessage.setText(getOtpDescription());
    }

    private String getOtpDescription() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_wallet_otp_description), "");
    }

    private RoundedBitmapDrawable getCreditCardBackgroundBitmapDrawable() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.credit_card_bg);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        dr.setCornerRadius(15);

        return dr;
    }

    private void addCardNumberTextChangedListener() {
        cardNumberText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                creditCardTypeImage.setVisibility(View.VISIBLE);

                if (s.length() == 0) {
                    creditCardNumberText.setText(CREDIT_CARD_DEFAULT_TEXT);
                } else {
                    String displayedCreditCardNumber = "";

                    for (int i = 0; i < s.length(); i++) {

                        if (i % 4 == 0 && i != 0) {
                            displayedCreditCardNumber += "  ";
                        }

                        displayedCreditCardNumber += s.charAt(i);
                    }

                    creditCardNumberText.setText(displayedCreditCardNumber);
                    setCreditCardTypeImage(String.valueOf(s));
                }
            }
        });
    }

    private void addExpiryDateMonthTextChangedListener() {
        expiryDateMonthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    creditCardMonthText.setText("MM");
                } else {
                    creditCardMonthText.setText("");
                    creditCardMonthText.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addExpiryDateYearTextChangedListener() {
        expiryDateYearText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    creditCardYearText.setText("YY");
                } else {
                    creditCardYearText.setText("");
                    creditCardYearText.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addCvvTextChangedListener() {
        cvvText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    creditCardCvvText.setText("CVV");
                } else {
                    creditCardCvvText.setText("");
                    creditCardCvvText.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setCreditCardTypeImage(String creditCardNumber) {

        String visa = "^4[0-9]*";
        String masterCard = "^5[1-5][0-9]*";
        String amex = "^3[47][0-9]*";

        if (creditCardNumber.matches(visa)) {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (CustomerWalletTopUpPaymentActivity.this, R.drawable.visa));
        } else if (creditCardNumber.matches(masterCard)) {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (CustomerWalletTopUpPaymentActivity.this, R.drawable.mastercard));
        } else if (creditCardNumber.matches(amex)) {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (CustomerWalletTopUpPaymentActivity.this, R.drawable.amex));
        } else {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (CustomerWalletTopUpPaymentActivity.this, R.drawable
                            .ic_credit_card_grey_300_48dp));
        }
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
                performOtpBackAction();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performOtpBackAction() {
        if (isOtpTransaction()) {
            createOtpBackButtonDialog();
        } else {
            finish();
        }
    }

    private void createOtpBackButtonDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CustomerWalletTopUpPaymentActivity.this);

        alertBuilder.setCancelable(false)
                .setTitle("Are you sure you want to go back?")
                .setMessage("This transaction will be cancelled and you'll be returned to the " +
                        "dashboard.")
                .setPositiveButton("Cancel Otp Transaction", (dialog, which) -> {
                    setSavedOtpTransactionValueToFalse();
                    returnToDashboard();
                })
                .setNegativeButton("Remain here", (dialog, which) -> {
                    dialog.cancel();
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void setSavedOtpTransactionValueToFalse() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getResources().getString(R.string
                .saved_customer_wallet_otp_status), false);
        editor.apply();
    }

    private void returnToDashboard() {
        Intent intent = new Intent(CustomerWalletTopUpPaymentActivity.this, CustomerActivity.class);
        startActivity(intent);
        finish();
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
        showLoading();

        ChargeObject chargeObject = new ChargeObject(cardNumber, monthValue, yearValue, cvv,
                cardPin);
        String amountToString = String.valueOf(amount);

        PaymentRequest paymentRequest = new PaymentRequest(chargeObject, amountToString,
                PAYMENT_METHOD_ID, getCountryCurrencyCode(country), referenceId);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.MFISA_BASE_URL)
                .build();

        MfisaService mfisaService = retrofit.create(MfisaService.class);
        final Observable<PaymentResponse> processCardTransaction = mfisaService
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
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        creditCardLayout.setVisibility(View.VISIBLE);
                        customerWalletPaymentButton.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(PaymentResponse paymentResponse) {
                        progressBar.setVisibility(View.GONE);

                        if (paymentResponse.getStatus().equals("02")) {
                            saveCurrentOtpStatus();
                            saveCurrentOtpValues(paymentResponse.getDescription(), paymentResponse
                                    .getDetails().getOtpref());
                            saveWalletTopUpValues();
                            switchToOtpView();
                        } else {
                            cardPaymentContainer.setVisibility(View.VISIBLE);
                            creditCardLayout.setVisibility(View.VISIBLE);
                            customerWalletPaymentButton.setVisibility(View.VISIBLE);
                            showResultDialog(paymentResponse.getDescription(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    @OnClick(R.id.otp_button)
    void processOtp() {
        otpValue = otpText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (otpValue.isEmpty()) {
            otpTextLayout.setError("Enter a 6 digit PIN");
            focusView = otpTextLayout;
            cancel = true;
        } else if (otpText.length() != 6) {
            otpTextLayout.setError("Enter a valid OTP number");
            focusView = otpTextLayout;
            cancel = true;
        } else {
            otpTextLayout.setError(null);
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(this)) {
                Snackbar.make(otpTextLayout, R.string.internet_error, Snackbar.LENGTH_SHORT).show();
            } else {
                performOtpTransaction(getOtpRef(), otpValue);
            }
        }
    }

    private void performOtpTransaction(String otpRef, String otp) {
        otpProgressBar.setVisibility(View.VISIBLE);
        otpLayoutContent.setVisibility(View.GONE);

        com.swifta.onerecharge.cardpayment.otp.requestmodel.ChargeObject chargeObject = new com
                .swifta.onerecharge.cardpayment.otp.requestmodel.ChargeObject(otpRef, otp);

        OtpRequest otpRequest = new OtpRequest(chargeObject, PAYMENT_METHOD_ID);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.MFISA_BASE_URL)
                .build();

        MfisaService mfisaService = retrofit.create(MfisaService.class);
        final Observable<OtpResponse> authorizeOtp = mfisaService.authorizeWithOtp
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
                        otpProgressBar.setVisibility(View.GONE);
                        otpLayoutContent.setVisibility(View.VISIBLE);
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(OtpResponse otpResponse) {
                        if (otpResponse.getStatus().equals("01")) {
                            performWalletTopUp(otpResponse.getDescription(), otpResponse
                                    .getDetails().getMfisaTxnId(), true);
                        } else {
                            //  performWalletTopUp("", "", false);
                            otpProgressBar.setVisibility(View.GONE);
                            otpLayoutContent.setVisibility(View.VISIBLE);
                            showResultDialog(otpResponse.getDescription(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void performWalletTopUp(String description, String transactionId, boolean
            isSuccessful) {

        CustomerWalletTopUpRequest walletTopUpRequest = new CustomerWalletTopUpRequest("mFISA",
                getWalletAmount(), getCustomerToken(), description, isSuccessful, transactionId,
                getReferenceId(), getEmail());

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
                        otpProgressBar.setVisibility(View.GONE);
                        otpLayoutContent.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        otpProgressBar.setVisibility(View.GONE);
                        otpLayoutContent.setVisibility(View.VISIBLE);
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(CustomerWalletTopUpResponse walletTopUpResponse) {

                        if (walletTopUpResponse.getStatus() == 1) {
                            updateSavedCustomerOtpStatus();
                            updateSavedCustomerBalance(walletTopUpResponse.getCustomerBalance());
                            showRechargeSuccessfulDialog();
                        } else {
                            showResultDialog(walletTopUpResponse.getData().getMessage(),
                                    TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private String getOtpRef() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_wallet_otp_ref), "");
    }

    private void saveCurrentOtpStatus() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getResources().getString(R.string.saved_customer_wallet_otp_status),
                true);
        editor.apply();
    }

    private void saveCurrentOtpValues(String description, String otpRef) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_customer_wallet_otp_description),
                description);
        editor.putString(getResources().getString(R.string.saved_customer_wallet_otp_ref), otpRef);
        editor.apply();
    }

    private void saveWalletTopUpValues() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getResources().getString(R.string.saved_customer_wallet_otp_amount), amount);
        editor.putString(getResources().getString(R.string
                .saved_customer_wallet_otp_customer_token), customerToken);
        editor.putString(getResources().getString(R.string
                .saved_customer_wallet_otp_reference_id), referenceId);
        editor.putString(getResources().getString(R.string.saved_customer_wallet_otp_email), email);
        editor.apply();
    }

    private void showLoading() {
        cardPaymentContainer.setVisibility(View.GONE);
        creditCardLayout.setVisibility(View.GONE);
        customerWalletPaymentButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private Integer getWalletAmount() {
        return sharedPreferences.getInt(getResources().getString(R.string
                .saved_customer_wallet_otp_amount), 0);
    }

    private String getReferenceId() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_wallet_otp_reference_id), "");
    }

    private String getCustomerToken() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_wallet_otp_customer_token), "");
    }

    private String getEmail() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_wallet_otp_email), "");
    }

    private void showRechargeSuccessfulDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(CustomerWalletTopUpPaymentActivity
                .this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.recharge_successful_layout, null);

        TextView textView = (TextView) dialogView.findViewById(R.id.success_message);
        textView.setText(TRANSACTION_SUCCESSFUL_MESSAGE);

        dialog.setCancelable(false)
                .setView(dialogView)
                .setPositiveButton("OK", (dialog1, id) -> {
                    Intent dashboardIntent = new Intent(CustomerWalletTopUpPaymentActivity.this,
                            CustomerActivity.class);
                    startActivity(dashboardIntent);
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void updateSavedCustomerOtpStatus() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getResources().getString(R.string.saved_customer_wallet_otp_status),
                false);
        editor.apply();
    }

    private void updateSavedCustomerBalance(Double balance) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Integer walletBalanceAsInteger = balance.intValue();

        editor.putInt(getResources().getString(R.string.saved_customer_balance),
                walletBalanceAsInteger);
        editor.apply();
    }

    private void showResultDialog(String message, int status) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        successfulFragment = RechargeResponseFragment.newInstance(message, status);
        successfulFragment.show(fragmentManager, "");
    }

//    private void hideLoading() {
//        progressBar.setVisibility(View.GONE);
//        linearLayoutContainer.setVisibility(View.VISIBLE);
//    }
}