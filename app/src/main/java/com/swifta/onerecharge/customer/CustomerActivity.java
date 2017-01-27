package com.swifta.onerecharge.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.swifta.onerecharge.R;
import com.swifta.onerecharge.agent.resetagentpassword.ProfileActivity;
import com.swifta.onerecharge.customer.customerdashboard.CustomerDashboardFragment;
import com.swifta.onerecharge.customer.customerlogout.CustomerLogout;
import com.swifta.onerecharge.customer.customerregistration.CustomerRegistrationActivity;
import com.swifta.onerecharge.privacypolicy.PrivacyPolicyActivity;
import com.swifta.onerecharge.util.CustomerService;
import com.swifta.onerecharge.util.InternetConnectivity;
import com.swifta.onerecharge.util.Url;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    TextView customerHeaderEmailView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(getString(R.string.customer_shared_preference_name),
                Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();
        displayFragment(new CustomerDashboardFragment());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        customerHeaderEmailView = (TextView) headerView.findViewById(R.id.customer_header_email);
        setHeaderViewText();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_privacy_policy:
                Intent privacyPolicyIntent = new Intent(CustomerActivity.this, PrivacyPolicyActivity
                        .class);
                startActivity(privacyPolicyIntent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(CustomerActivity.this, ProfileActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_logout:
                if (!InternetConnectivity.isDeviceConnected(this)) {
                    Toast.makeText(this, R.string.internet_error, Toast.LENGTH_SHORT).show();
                } else {
                    logCustomerOut();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearCustomerData() {
        sharedPreferences.edit().clear().apply();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.customer_container, fragment)
                .commit();
    }

    private void setHeaderViewText() {
        String email = sharedPreferences.getString(getResources().getString(R.string
                .saved_customer_email_address), "");

        customerHeaderEmailView.setText(email);
    }

    private void logCustomerOut() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        final CustomerService customerService = retrofit.create(CustomerService.class);
        final Observable<CustomerLogout> agent = customerService.logCustomerOut
                ("tPTpR4PIYtoFSiblO1P9Xn0ttGsWE9wS", "oZFLQVYpRO9Ur8i6H8Q1J5c3RMgt0fb0",
                        getCustomerEmail(), getCustomerToken());

        agent.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CustomerLogout>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CustomerLogout customerLogout) {
                        if (customerLogout.getStatus() == 1) {
                            clearCustomerData();
                            Intent logoutIntent = new Intent(CustomerActivity.this,
                                    CustomerRegistrationActivity.class);
                            startActivity(logoutIntent);
                            finish();
                        } else {
                            Toast.makeText(CustomerActivity.this, "Logout unsuccessful. Please " +
                                    "try again", Toast.LENGTH_SHORT).show();
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
}
