package com.swifta.onerecharge.customer.customerdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.customer.customerwalletbalance.balancerequestmodel.CustomerWalletBalanceRequest;
import com.swifta.onerecharge.customer.customerwalletbalance.balanceresponsemodel.CustomerWalletBalanceResponse;
import com.swifta.onerecharge.util.CustomerService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.Url;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class CustomerDashboardFragment extends Fragment {

    @BindView(R.id.promo)
    TextView promoTextView;
    @BindView(R.id.discounts)
    TextView discountsTextView;
    @BindView(R.id.recharged)
    TextView rechargedTextView;

    private View view;
    private SharedPreferences sharedPreferences;

    @BindView(R.id.wallet_balance_text)
    TextView walletBalanceText;

    public CustomerDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_customer_dashboard, container,
                false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.app_name));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.customer_shared_preference_name), Context.MODE_PRIVATE);

        setUpUiWithDefaultValues();
        setUpUi();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpUi();
    }

    private String getWalletBalance() {

        String balance = String.valueOf(sharedPreferences.getInt(getResources().getString(R.string
                .saved_customer_balance), 0));

        return (balance.equals("0")) ? "0.00" : balance;
    }

    private void setUpUiWithDefaultValues() {
        walletBalanceText.setText(getResources().getString(R.string
                .wallet_balance, getWalletBalance()));
    }

    private void setUpUi() {
        promoTextView.setText(getPromo());
        discountsTextView.setText(getDiscounts());
        rechargedTextView.setText(getRecharged());

        if (InternetConnectivity.isDeviceConnected(getActivity())) {
            getNewWalletBalance();
        }
    }

    private String getPromo() {
        String promo = String.valueOf(sharedPreferences.getInt(getResources().getString(R.string
                .saved_customer_promo), 0));
        return promo;
    }

    private String getDiscounts() {
        String discounts = String.valueOf(sharedPreferences.getInt(getResources().getString(R.string
                .saved_customer_discount), 0));
        return discounts;
    }

    private String getRecharged() {
        String recharged = String.valueOf(sharedPreferences.getInt(getResources().getString(R.string
                .saved_customer_recharged), 0));
        return recharged;
    }

    private void getNewWalletBalance() {

        CustomerWalletBalanceRequest walletBalanceRequest = new CustomerWalletBalanceRequest
                ("sMHuflOVZYEuCvXW1SGWmIMoj0aV5q1D", getCustomerEmail());

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<CustomerWalletBalanceResponse> walletBalanceResponse =
                customerService.getWalletBallance(walletBalanceRequest);

        walletBalanceResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerWalletBalanceResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CustomerWalletBalanceResponse walletBalanceResponse) {

                        if (walletBalanceResponse.getStatus() == 1) {
                            updateSavedCustomerBalance(walletBalanceResponse.getData().getBalance
                                    ());
                            setUpUiWithDefaultValues();
                        }
                    }
                });
    }

    private String getCustomerEmail() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_email_address), "");
    }

    private void updateSavedCustomerBalance(Double balance) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Integer walletBalanceAsInteger = balance.intValue();

        editor.putInt(getResources().getString(R.string.saved_customer_balance),
                walletBalanceAsInteger);
        editor.apply();
    }
}
