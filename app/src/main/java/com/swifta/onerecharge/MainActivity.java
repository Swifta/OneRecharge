package com.swifta.onerecharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.swifta.onerecharge.agent.agentregistration.AgentRegistrationActivity;
import com.swifta.onerecharge.customer.customerregistration.CustomerRegistrationActivity;

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
        Intent customerIntent = new Intent(MainActivity.this, CustomerRegistrationActivity.class);
        startActivity(customerIntent);
    }

    private void launchAgentActivity() {
        Intent agentIntent = new Intent(MainActivity.this, AgentRegistrationActivity.class);
        startActivity(agentIntent);
    }
}
