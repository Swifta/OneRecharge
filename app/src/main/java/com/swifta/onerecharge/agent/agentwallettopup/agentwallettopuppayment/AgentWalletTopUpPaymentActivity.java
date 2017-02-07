package com.swifta.onerecharge.agent.agentwallettopup.agentwallettopuppayment;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.AgentActivity;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
import com.swifta.onerecharge.agent.agentwallettopup.agentwallettopuprequestmodel.AgentWalletTopUpRequest;
import com.swifta.onerecharge.agent.agentwallettopup.agentwallettopupresponsemodel.AgentWalletTopUpResponse;
import com.swifta.onerecharge.cardpayment.card.requestmodel.ChargeObject;
import com.swifta.onerecharge.cardpayment.card.requestmodel.PaymentRequest;
import com.swifta.onerecharge.cardpayment.card.responsemodel.PaymentResponse;
import com.swifta.onerecharge.cardpayment.otp.requestmodel.OtpRequest;
import com.swifta.onerecharge.cardpayment.otp.responsemodel.OtpResponse;
import com.swifta.onerecharge.util.AgentService;
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

public class AgentWalletTopUpPaymentActivity extends AppCompatActivity {

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
    Button agentWalletPaymentButton;
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

    RechargeResponseFragment successfulFragment;

    String email, referenceId, agentToken, country;
    int amount;
    String cardNumber, monthValue, yearValue, cvv, cardPin;

    private static final int TRANSACTION_FAILED = 0;
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

        amount = getIntent().getIntExtra("amount", 0);
        email = getIntent().getStringExtra("email");
        agentToken = getIntent().getStringExtra("agent_token");
        country = getIntent().getStringExtra("country");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.credit_card_bg);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        dr.setCornerRadius(15);
        imageView.setImageDrawable(dr);

        referenceId = RandomNumberGenerator.getRandomString(12);

        agentWalletPaymentButton.setText("Pay " + getCountryCurrencyCode(country) + " " + amount);

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
                    creditCardNumberText.setText("XXXX  XXXX  XXXX  XXXX  XXXX");
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
                    (AgentWalletTopUpPaymentActivity.this, R.drawable.visa));
        } else if (creditCardNumber.matches(masterCard)) {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (AgentWalletTopUpPaymentActivity.this, R.drawable.mastercard));
        } else if (creditCardNumber.matches(amex)) {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (AgentWalletTopUpPaymentActivity.this, R.drawable.amex));
        } else {
            creditCardTypeImage.setImageDrawable(ContextCompat.getDrawable
                    (AgentWalletTopUpPaymentActivity.this, R.drawable.ic_credit_card_grey_300_48dp));
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
        creditCardLayout.setVisibility(View.GONE);
        agentWalletPaymentButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

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
                        progressBar.setVisibility(View.GONE);
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        creditCardLayout.setVisibility(View.VISIBLE);
                        agentWalletPaymentButton.setVisibility(View.VISIBLE);
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
                            creditCardLayout.setVisibility(View.VISIBLE);
                            agentWalletPaymentButton.setVisibility(View.VISIBLE);
                            showResultDialog(paymentResponse.getDescription(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void displayOtpDialog(String description, String otpRef) {

        final EditText input = new EditText(AgentWalletTopUpPaymentActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder dialog = new AlertDialog.Builder(AgentWalletTopUpPaymentActivity
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
                    creditCardLayout.setVisibility(View.VISIBLE);
                    agentWalletPaymentButton.setVisibility(View.VISIBLE);
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
                        progressBar.setVisibility(View.GONE);
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        creditCardLayout.setVisibility(View.VISIBLE);
                        agentWalletPaymentButton.setVisibility(View.VISIBLE);
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(OtpResponse otpResponse) {
                        if (otpResponse.getStatus().equals("01")) {
                            performWalletTopUp(otpResponse.getDescription(), otpResponse
                                    .getDetails().getMfisaTxnId());
                        } else {
                            progressBar.setVisibility(View.GONE);
                            cardPaymentContainer.setVisibility(View.VISIBLE);
                            creditCardLayout.setVisibility(View.VISIBLE);
                            agentWalletPaymentButton.setVisibility(View.VISIBLE);
                            showResultDialog(otpResponse.getDescription(), TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void performWalletTopUp(String description, String transactionId) {

        AgentWalletTopUpRequest walletTopUpRequest = new AgentWalletTopUpRequest(amount,
                agentToken, description, transactionId, referenceId, email);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        AgentService agentService = retrofit.create(AgentService.class);
        final Observable<AgentWalletTopUpResponse> walletTopUp = agentService.topWalletUp
                (walletTopUpRequest);

        walletTopUp.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentWalletTopUpResponse>() {

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        cardPaymentContainer.setVisibility(View.VISIBLE);
                        creditCardLayout.setVisibility(View.VISIBLE);
                        agentWalletPaymentButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showResultDialog(TRANSACTION_ERROR_MESSAGE, TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(AgentWalletTopUpResponse walletTopUpResponse) {
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

        final ImageView imageView = new ImageView(AgentWalletTopUpPaymentActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        imageView.setBackgroundResource(R.drawable.ic_check_circle_green_700_36dp);

        AlertDialog.Builder dialog = new AlertDialog.Builder(AgentWalletTopUpPaymentActivity.this);
        dialog.setCancelable(false)
                .setTitle("Recharge Successful!")
                .setMessage(TRANSACTION_SUCCESSFUL_MESSAGE)
                .setView(imageView)
                .setPositiveButton("OK", (dialog1, id) -> {
                    Intent dashboardIntent = new Intent(AgentWalletTopUpPaymentActivity.this,
                            AgentActivity.class);
                    startActivity(dashboardIntent);
                    finish();
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}