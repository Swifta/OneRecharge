package com.swifta.onerecharge.agent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swifta.onerecharge.MainActivity;
import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.agentapikey.AgentApiKeyResponse;
import com.swifta.onerecharge.agent.agentdashboard.AgentSummary;
import com.swifta.onerecharge.agent.agentdashboard.PagerAdapter;
import com.swifta.onerecharge.agent.agentdashboard.ProfileRulesFragment;
import com.swifta.onerecharge.agent.agentdashboard.ProfileSummaryFragment;
import com.swifta.onerecharge.agent.agentlogout.AgentLogout;
import com.swifta.onerecharge.agent.agentquickrecharge.QuickRechargeFragment;
import com.swifta.onerecharge.agent.agentquicktransactionhistory.AgentQuickTransactionHistoryFragment;
import com.swifta.onerecharge.agent.agentscheduledrecharge.ScheduledRechargeFragment;
import com.swifta.onerecharge.agent.agentscheduledtransactionhistory.AgentScheduledTransactionHistoryFragment;
import com.swifta.onerecharge.agent.agentwallettopup.AgentWalletTopUpFragment;
import com.swifta.onerecharge.agent.resetagentpassword.ProfileActivity;
import com.swifta.onerecharge.countryinfo.AvailableCountriesResponse;
import com.swifta.onerecharge.countryinfo.CountryListRepository;
import com.swifta.onerecharge.countryinfo.Data;
import com.swifta.onerecharge.networklist.NetworkListRepository;
import com.swifta.onerecharge.privacypolicy.PrivacyPolicyActivity;
import com.swifta.onerecharge.util.AgentService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.Url;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AgentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_dashboard)
    Toolbar toolbarDashboard;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.agent_container)
    FrameLayout activityContainerFrame;

    @BindView(R.id.dashboard_app_bar_layout)
    AppBarLayout dashboardAppBarLayout;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.collapsingLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager;
    TextView agentHeaderEmailView;
    SharedPreferences sharedPreferences;
    Realm realm;
    String currentDisplayedView = "none";

    @Override
    protected void onStart() {
        super.onStart();
        if (InternetConnectivity.isDeviceConnected(this)) {
            updateWalletBalance();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (InternetConnectivity.isDeviceConnected(this)) {
            updateWalletBalance();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(getString(R.string
                .agent_shared_preference_name), Context.MODE_PRIVATE);
        realm = Realm.getDefaultInstance();

        if (InternetConnectivity.isDeviceConnected(this)) {
            updateWalletBalance();
        }

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            currentDisplayedView = savedInstanceState.getString("current_displayed_view");

            switch (currentDisplayedView) {
                case "none":
                    displayFragmentDashboard();
                    break;
                case "dashboard":
                    displayFragmentDashboard();
                    break;
                case "agent_wallet_topup":
                    displayFragment(new AgentWalletTopUpFragment());
                    break;
                case "nav_quick_recharge":
                    displayFragment(new QuickRechargeFragment());
                    break;
                case "nav_auto_recharge":
                    displayFragment(new ScheduledRechargeFragment());
                    break;
                case "nav_quick_transaction_history":
                    displayFragment(new AgentQuickTransactionHistoryFragment());
                    break;
                case "nav_scheduled_transaction_history":
                    displayFragment(new AgentScheduledTransactionHistoryFragment());
                    break;
                default:
                    displayFragmentDashboard();
            }
        } else {
            displayFragmentDashboard();
        }

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        agentHeaderEmailView = (TextView) headerView.findViewById(R.id
                .agent_header_email);
        setHeaderViewText();

        setUpCollapsingToolbarWithCurrentValue();

        getAgentSummary();

        getAgentApiKey();

        getAvailableCountries();
    }

    private void updateWalletBalance() {
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
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentSummary agentSummary) {
                        if (agentSummary.getStatus() == 1) {

                            String walletBalance = agentSummary.getData
                                    ().getWalletBalance();
                            setUpWalletWithCurrentValue(walletBalance);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("current_displayed_view", currentDisplayedView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_privacy_policy:
                Intent privacyPolicyIntent = new Intent(AgentActivity.this, PrivacyPolicyActivity
                        .class);
                startActivity(privacyPolicyIntent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(AgentActivity.this, ProfileActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_logout:
                if (!InternetConnectivity.isDeviceConnected(this)) {
                    Toast.makeText(this, R.string.internet_error, Toast.LENGTH_SHORT).show();
                } else {
                    logAgentOut();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearAgentData() {
        sharedPreferences.edit().clear().apply();

        realm.executeTransaction(realm -> realm.deleteAll());
        realm.close();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            displayFragmentDashboard();
        } else if (id == R.id.nav_wallet_topup) {
            currentDisplayedView = "agent_wallet_topup";
            displayFragment(new AgentWalletTopUpFragment());
        } else if (id == R.id.nav_quick_recharge) {
            currentDisplayedView = "nav_quick_recharge";
            displayFragment(new QuickRechargeFragment());
        } else if (id == R.id.nav_auto_recharge) {
            currentDisplayedView = "nav_auto_recharge";
            displayFragment(new ScheduledRechargeFragment());
        } else if (id == R.id.nav_quick_transaction_history) {
            currentDisplayedView = "nav_quick_transaction_history";
            displayFragment(new AgentQuickTransactionHistoryFragment());
        } else if (id == R.id.nav_scheduled_transaction_history) {
            currentDisplayedView = "nav_scheduled_transaction_history";
            displayFragment(new AgentScheduledTransactionHistoryFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragmentDashboard() {
        appBarLayout.setVisibility(View.GONE);
        activityContainerFrame.setVisibility(View.GONE);

        dashboardAppBarLayout.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbarDashboard);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbarDashboard, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initTabInstances();
        tabLayout.setupWithViewPager(viewPager);

        currentDisplayedView = "dashboard";
        setupViewPager(viewPager, getSupportFragmentManager());
    }

    private void displayFragment(Fragment fragment) {
        dashboardAppBarLayout.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

        appBarLayout.setVisibility(View.VISIBLE);
        activityContainerFrame.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager.beginTransaction()
                .replace(R.id.agent_container, fragment)
                .commit();
    }

    private void setHeaderViewText() {
        String email = sharedPreferences.getString(getResources().getString(R.string
                .saved_email_address), "");
        if (!email.isEmpty()) {
            agentHeaderEmailView.setText(email);
        }
    }

    private void setupViewPager(ViewPager viewPager, FragmentManager fragmentManager) {
        PagerAdapter adapter = new PagerAdapter(this, fragmentManager);
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
    }

    private void setUpCollapsingToolbarWithCurrentValue() {
        collapsingToolbarLayout.setTitle(getResources().getString(R.string
                .wallet_balance, getWalletBalance()));
    }

    private String getWalletBalance() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_wallet_balance), "0.00");
    }

    private String getEmailAddress() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_email_address), "");
    }

    private String getToken() {
        return sharedPreferences.getString(getResources().getString(R.string
                .saved_auth_token), "");
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
                                                  totalFailedTransactionAmount, int lastFailedAmount, String lastFailedTime, String
                                                  lastFailedDescription) {
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
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
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

                            setUpWalletWithCurrentValue(walletBalance);
                            setUpTabFragmentsWithCurrentValue();
                        }
                    }
                });
    }

    private void setUpWalletWithCurrentValue(String walletBalance) {
        collapsingToolbarLayout.setTitle(getResources().getString(R.string
                .wallet_balance, walletBalance));
    }

    private void setUpTabFragmentsWithCurrentValue() {
        initTabInstances();
        tabLayout.setupWithViewPager(viewPager);

        currentDisplayedView = "dashboard";
        setupViewPager(viewPager, getSupportFragmentManager());
    }

    private void getAvailableCountries() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit.create(AgentService.class);
        final Observable<AvailableCountriesResponse> agent = agentService.getAvailableCountries();

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AvailableCountriesResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AvailableCountriesResponse availableCountriesResponse) {
                        if (availableCountriesResponse.getStatus() == 1) {

                            List<String> countryList = new ArrayList<String>();
                            List<List<String>> countryNetworkList = new ArrayList<List<String>>();

                            for (Data data : availableCountriesResponse.getData()) {
                                countryList.add(data.getName());
                                countryNetworkList.add(data.getNetworks());
                            }

                            CountryListRepository.setCountryList(countryList);
                            NetworkListRepository.setNetworkList(countryNetworkList);
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
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
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

    private void logAgentOut() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final AgentService agentService = retrofit.create(AgentService.class);
        final Observable<AgentLogout> agent = agentService.logAgentOut(getEmailAddress
                (), getToken());

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AgentLogout>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AgentLogout agentLogout) {
                        if (agentLogout.getStatus() == 1) {
                            clearAgentData();
                            Intent logoutIntent = new Intent(AgentActivity.this, MainActivity
                                    .class);
                            startActivity(logoutIntent);
                            finish();
                        } else {
                            Toast.makeText(AgentActivity.this, agentLogout.getData(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}