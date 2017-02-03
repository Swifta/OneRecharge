package com.swifta.onerecharge.customer.customerwallettopup;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentquickrecharge.RechargeResponseFragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerWalletTopUpFragment extends Fragment {

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
    RechargeResponseFragment successfulFragment;

    private static final int TRANSACTION_FAILED = 0;
    private static final int TRANSACTION_SUCCESSFUL = 1;

    public CustomerWalletTopUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_wallet_top_up, container, false);

        ButterKnife.bind(this, view);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string
                .customer_shared_preference_name), Context.MODE_PRIVATE);

        return view;
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
            if (!InternetConnectivity.isDeviceConnected(getActivity())) {
                Snackbar.make(topUpView, R.string.internet_error, Snackbar.LENGTH_SHORT).show();
            } else {
                topUpView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                topWalletUp();
            }
        }
    }

    private void topWalletUp() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
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
                        showResultDialog(getString(R.string.customer_topup_error), TRANSACTION_FAILED);
                    }

                    @Override
                    public void onNext(CustomerTopUpResponse customerTopUpResponse) {
                        progressBar.setVisibility(View.GONE);
                        topUpView.setVisibility(View.VISIBLE);

                        if (customerTopUpResponse.getStatus() == 1) {
                            updateWallet(customerTopUpResponse.getData().getWallet());
                            clearInputFields();
                            showResultDialog("Wallet credited with ₦" + amount + "!", TRANSACTION_SUCCESSFUL);
                        } else {
                            showResultDialog(customerTopUpResponse.getData().getMessage(),
                                    TRANSACTION_FAILED);
                        }
                    }
                });
    }

    private void showResultDialog(String message, int status) {
        FragmentManager fragmentManager = getChildFragmentManager();
        successfulFragment = RechargeResponseFragment.newInstance(message, status);
        successfulFragment.show(fragmentManager, "");
    }

    private void clearInputFields() {
        amountField.setText("");
        referenceField.setText("");
        descriptionField.setText("");
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

}
