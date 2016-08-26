package com.swifta.onerecharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.swifta.onerecharge.resetagentpassword.ProfileActivity;
import com.swifta.onerecharge.agentregistration.AgentRegistrationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }

    public void launchUserInterface(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.customer_option:
                if (checked) launchCustomerActivity();
                break;
            case R.id.agent_option:
                if (checked) launchAgentActivity();
                break;
        }
    }

    private void launchCustomerActivity() {
        Intent customerIntent = new Intent(MainActivity.this,
                CustomerRegistrationActivity
                        .class);
        startActivity(customerIntent);
    }

    private void launchAgentActivity() {
        Intent customerIntent = new Intent(MainActivity.this,
                AgentRegistrationActivity
                        .class);
        startActivity(customerIntent);
    }
}
