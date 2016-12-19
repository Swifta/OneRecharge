package com.swifta.onerecharge;

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

import com.swifta.onerecharge.agentdashboard.DashboardFragment;
import com.swifta.onerecharge.agentquickrecharge.QuickRechargeFragment;
import com.swifta.onerecharge.agentquicktransactionhistory.AgentQuickTransactionHistoryFragment;
import com.swifta.onerecharge.agentregistration.AgentRegistrationActivity;
import com.swifta.onerecharge.agentscheduledrecharge.ScheduledRechargeFragment;
import com.swifta.onerecharge.agentscheduledtransactionhistory.AgentScheduledTransactionHistoryFragment;
import com.swifta.onerecharge.resetagentpassword.ProfileActivity;

public class AgentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager;
    TextView agentHeaderEmailView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(getString (R.string
                .agent_shared_preference_name), Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();
        displayFragment(new DashboardFragment());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        agentHeaderEmailView = (TextView) headerView.findViewById(R.id
                .agent_header_email);
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
            case R.id.action_settings:
                Intent settingsIntent = new Intent(AgentActivity.this, ProfileActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_logout:
                clearAgentData();
                Intent logoutIntent = new Intent(AgentActivity.this, AgentRegistrationActivity
                        .class);
                startActivity(logoutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearAgentData() {
        sharedPreferences.edit().clear().apply();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            displayFragment(new DashboardFragment());
        } else if (id == R.id.nav_quick_recharge) {
            displayFragment(new QuickRechargeFragment());
        } else if (id == R.id.nav_auto_recharge) {
            displayFragment(new ScheduledRechargeFragment());
        } else if (id == R.id.nav_quick_transaction_history) {
            displayFragment(new AgentQuickTransactionHistoryFragment());
        } else if (id == R.id.nav_scheduled_transaction_history) {
            displayFragment(new AgentScheduledTransactionHistoryFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(Fragment fragment) {
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
}
