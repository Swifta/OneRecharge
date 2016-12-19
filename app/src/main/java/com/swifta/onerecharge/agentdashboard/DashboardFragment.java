package com.swifta.onerecharge.agentdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agentapikey.AgentApiKeyResponse;
import com.swifta.onerecharge.networklist.NetworkListRepository;
import com.swifta.onerecharge.networklist.NetworkListResponse;
import com.swifta.onerecharge.util.AgentService;
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
public class DashboardFragment extends Fragment {
    private View view;
    private SharedPreferences sharedPreferences;

    @BindView(R.id.wallet_balance_text)
    TextView walletBalanceText;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container,
                false);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle
                (getResources().getString(R.string.app_name));

        sharedPreferences = getActivity().getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        setupViewPager(viewPager, getActivity().getSupportFragmentManager());

        initTabInstances();
        tabLayout.setupWithViewPager(viewPager);

        setUpUiWithDefaultValues();

        getAgentSummary();

        getAgentApiKey();

        getNetworkList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupViewPager(viewPager, getChildFragmentManager());
    }

    private void setupViewPager(ViewPager viewPager, FragmentManager fragmentManager) {
        PagerAdapter adapter = new PagerAdapter(getActivity(), fragmentManager);
        adapter.addFragment(new ProfileRulesFragment(), getResources().getString(R
                .string.profile_rules));
        adapter.addFragment(new ProfileSummaryFragment(), getResources().getString(R
                .string.profile_summary));
        viewPager.setAdapter(adapter);
    }

    private void initTabInstances() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString
                (R.string.profile_rules)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString
                (R.string.profile_summary)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private String getEmailAddress() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_email_address), "");
    }

    private String getToken() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_auth_token), "");
    }

    private String getWalletBalance() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_wallet_balance), "0.00");
    }

    private void setUpUiWithDefaultValues() {
        walletBalanceText.setText(getResources().getString(R.string
                .wallet_balance, getWalletBalance()));
    }

    private void saveProfileRulesResult(String walletBalance, String
            msisdnPurchaseLimit, String minimumWalletBalance, String
                                                airtimePurchaseDiscount, String airtimeSoldCommission) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string
                .saved_wallet_balance), walletBalance);
        editor.putString(getResources().getString(R.string
                .saved_msisdn_purchase_limit), msisdnPurchaseLimit);
        editor.putString(getResources().getString(R.string
                .saved_minimum_wallet_balance), minimumWalletBalance);
        editor.putString(getResources().getString(R.string
                .saved_airtime_purchase_discount), airtimePurchaseDiscount);
        editor.putString(getResources().getString(R.string
                .saved_airtime_sold_commission), airtimeSoldCommission);

        editor.apply();
    }

    private void saveProfileSummaryResult(String referralId, String
            agentType, String agentClass, String dateAndTime, int
                                                  totalTransactions, int successfulTransactions, int
                                                  totalAmountSold, int successfulTransactionAmount, String
                                                  successfulTransactionTime,
                                          int totalFailedTransactions, int
                                                  totalFailedTransactionAmount, int lastFailedAmount, String lastFailedTime, String lastFailedDescription) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_referral_id)
                , referralId);
        editor.putString(getResources().getString(R.string.saved_agent_type)
                , agentType);
        editor.putString(getResources().getString(R.string.saved_agent_class)
                , agentClass);
        editor.putString(getResources().getString(R.string.saved_date_and_time)
                , dateAndTime);
        editor.putInt(getResources().getString(R.string.saved_total_transactions)
                , totalTransactions);
        editor.putInt(getResources().getString(R.string.saved_successful_transactions)
                , successfulTransactions);
        editor.putInt(getResources().getString(R.string.saved_total_amount_sold)
                , totalAmountSold);
        editor.putInt(getResources().getString(R.string.saved_last_successful_amount)
                , successfulTransactionAmount);
        editor.putString(getResources().getString(R.string.saved_last_successful_time)
                , successfulTransactionTime);
        editor.putInt(getResources().getString(R.string
                .saved_total_failed_transaction), totalFailedTransactions);
        editor.putInt(getResources().getString(R.string
                .saved_total_failed_transaction_amount), totalFailedTransactionAmount);
        editor.putInt(getResources().getString(R.string
                .saved_last_failed_amount), lastFailedAmount);
        editor.putString(getResources().getString(R.string.saved_last_failed_time)
                , lastFailedTime);
        editor.putString(getResources().getString(R.string.saved_failed_transaction_description)
                , lastFailedDescription);

        editor.apply();
    }

    private void setUpWalletWithCurrentValue(String walletBalance) {
        walletBalanceText.setText(getResources().getString(R.string
                .wallet_balance, walletBalance));
    }

    private void getAgentSummary() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        String emailAddress = getEmailAddress();
        String authToken = getToken();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<AgentSummary> agent = agentService
                .getSummary(emailAddress, authToken);

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentSummary>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentSummary agentSummary) {
                        if (agentSummary.getStatus() == 1) {

                            String walletBalance = agentSummary.getData
                                    ().getWalletBalance();
                            String msisdnPurchaseLimit = String.valueOf
                                    (agentSummary.getData().getRulesLimits()
                                            .getMsisdnPurchaseLimit());
                            String minimumWalletBalance = String.valueOf
                                    (agentSummary.getData().getRulesLimits()
                                            .getMinimumWallet());
                            String airtimePurchaseDiscount = String.valueOf
                                    (agentSummary.getData().getRulesLimits()
                                            .getAirtimePurchaseDiscount());
                            String airtimeSoldCommission = String.valueOf
                                    (agentSummary.getData().getRulesLimits()
                                            .getAirtimeSoldCommission());

                            String referralId = String.valueOf(agentSummary
                                    .getData().getReferralId());

                            String agentType = String.valueOf(agentSummary
                                    .getData().getAgentType());
                            String agentClass = String.valueOf(agentSummary
                                    .getData().getAgentClass());
                            String dateAndTimeCreated = String.valueOf
                                    (agentSummary.getData().getJoined());
                            int totalTransactions = agentSummary.getData().getTotal();
                            int savedSuccessfulTransactions = agentSummary
                                    .getData().getTotalSuccess();
                            int totalAmountSold = agentSummary.getData()
                                    .getAmountSuccess();
                            int successfulTransactionAmount = agentSummary
                                    .getData().getLastSuccessAmount();
                            String successfulTransactionTime = agentSummary
                                    .getData().getLastSuccessTime();
                            int totalFailedTransactions = agentSummary
                                    .getData().getTotalFailed();
                            int totalFailedTransactionAmount = agentSummary
                                    .getData().getAmountFailed();
                            int lastFailedAmount = agentSummary
                                    .getData().getLastFailedAmount();
                            String lastFailedTime = agentSummary
                                    .getData().getLastFailedTime();
                            String lastFailedDescription = agentSummary
                                    .getData().getLastFailedDescription();

                            setUpWalletWithCurrentValue(walletBalance);

                            saveProfileRulesResult(walletBalance,
                                    msisdnPurchaseLimit,
                                    minimumWalletBalance,
                                    airtimePurchaseDiscount, airtimeSoldCommission);

                            saveProfileSummaryResult(referralId, agentType,
                                    agentClass, dateAndTimeCreated,
                                    totalTransactions,
                                    savedSuccessfulTransactions,
                                    totalAmountSold,
                                    successfulTransactionAmount,
                                    successfulTransactionTime,
                                    totalFailedTransactions,
                                    totalFailedTransactionAmount,
                                    lastFailedAmount, lastFailedTime, lastFailedDescription);

                            setupViewPager(viewPager, getChildFragmentManager());
                        }
                    }
                });
    }

    private void getNetworkList() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<NetworkListResponse> agent = agentService.getNetworkList();

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NetworkListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(NetworkListResponse networkListResponse) {
                        if (networkListResponse.getStatus() == 1) {
                            NetworkListRepository.setNetworkList(networkListResponse.getData());
                        }
                    }
                });
    }

    private void getAgentApiKey() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit
                .create(AgentService.class);
        final Observable<AgentApiKeyResponse> agent = agentService.getAgentApiKey(getEmailAddress
                (), getToken());

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentApiKeyResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentApiKeyResponse agentApiKeyResponse) {
                        if (agentApiKeyResponse.getStatus() == 1) {
                            saveApiAgentKey(agentApiKeyResponse.getData());
                        }
                    }
                });
    }

    private void saveApiAgentKey(String agentApiKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.saved_agent_api_key), agentApiKey);

        editor.apply();
    }
}
