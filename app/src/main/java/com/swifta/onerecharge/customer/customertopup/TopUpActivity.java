package com.swifta.onerecharge.customer.customertopup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.swifta.onerecharge.customer.CustomerActivity;
import com.swifta.onerecharge.R;
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

public class TopUpActivity extends AppCompatActivity {

    @BindView(R.id.amount)
    TextInputEditText amountField;
    @BindView(R.id.reference)
    TextInputEditText referenceField;
    @BindView(R.id.description)
    TextInputEditText descriptionField;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.amount_layout)
    TextInputLayout amountFieldLayout;
    @BindView(R.id.reference_layout)
    TextInputLayout referenceLayout;

    @BindView(R.id.topUpView)
    LinearLayout topUpView;

    String amount;
    String reference;
    String description;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(getString(R.string
                .customer_shared_preference_name), Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.top_wallet_up_button)
    void fundWallet() {

        amountFieldLayout.setError(null);
        referenceLayout.setError(null);

        amount = amountField.getText().toString();
        reference = referenceField.getText().toString();
        description = descriptionField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (description.isEmpty()) {
            description = "None";
        }

        if (reference.isEmpty()) {
            referenceLayout.setError(getString(R.string.reference_empty_error));
            focusView = referenceLayout;
            cancel = true;
        } else {
            referenceLayout.setError(null);
        }

        if (amount.isEmpty()) {
            amountFieldLayout.setError(getString(R.string.amount_empty_error));
            focusView = amountFieldLayout;
            cancel = true;
        } else {
            amountFieldLayout.setError(null);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!InternetConnectivity.isDeviceConnected(getApplicationContext
                    ())) {
                Snackbar.make(topUpView, R.string.internet_error, Snackbar
                        .LENGTH_SHORT).show();
            } else {
                topUpView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                topWalletUp();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switchToCustomerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void topWalletUp() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit
                .create(CustomerService.class);
        final Observable<CustomerTopUpResponse> customer = customerService.topWalletUp
                ("tPTpR4PIYtoFSiblO1P9Xn0ttGsWE9wS", "oZFLQVYpRO9Ur8i6H8Q1J5c3RMgt0fb0",
                        getCustomerEmail(), getCustomerToken(), amount, reference, description);

        customer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerTopUpResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        topUpView.setVisibility(View.VISIBLE);

                        Snackbar.make(topUpView, R.string.customer_topup_error, Snackbar
                                .LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CustomerTopUpResponse customerTopUpResponse) {
                        if (customerTopUpResponse.getStatus() == 1) {
                            Toast.makeText(TopUpActivity.this, "Wallet credited with " + amount + "!",
                                    Toast.LENGTH_SHORT).show();
                            updateWallet(customerTopUpResponse.getData().getWallet());
                            switchToCustomerActivity();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            topUpView.setVisibility(View.VISIBLE);

                            Snackbar.make(topUpView, customerTopUpResponse.getData().getMessage()
                                    , Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getCustomerEmail() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_email_address), "");
    }

    private String getCustomerToken() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_auth_token), "");
    }

    private void updateWallet(Wallet wallet) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getResources().getString(R.string.saved_customer_promo), wallet.getPromo());
        editor.putInt(getResources().getString(R.string.saved_customer_discount), wallet.getDiscounts());
        editor.putInt(getResources().getString(R.string.saved_customer_recharged), wallet.getRecharged());
        editor.putInt(getResources().getString(R.string.saved_customer_balance), wallet.getBalance());

        editor.apply();
    }

    private void switchToCustomerActivity() {
        Intent i = new Intent(TopUpActivity.this, CustomerActivity.class);
        startActivity(i);
    }
}
