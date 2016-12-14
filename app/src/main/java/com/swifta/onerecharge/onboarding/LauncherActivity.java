package com.swifta.onerecharge.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swifta.onerecharge.MainActivity;
import com.swifta.onerecharge.R;

public class LauncherActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Boolean isUserNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);
        isUserNew = sharedPreferences.getBoolean(getResources().getString(R.string
                .is_user_new), true);

        if (isUserNew) {
            goToOnboardingActivity();
        } else {
            goToMainActivity();
        }
    }

    private void goToOnboardingActivity() {
        Intent intent = new Intent(LauncherActivity.this,
                OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LauncherActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}
