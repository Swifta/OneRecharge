package com.swifta.onerecharge.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.swifta.onerecharge.MainActivity;
import com.swifta.onerecharge.R;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends OnboarderActivity {
    List<OnboarderPage> onboarderPageList;
    String[] onboardingTitles;
    String[] onboardingDescription;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnboarderPage onboarderPage;

        onboarderPageList = new ArrayList<OnboarderPage>();
        onboardingTitles = getResources().getStringArray(R.array.onboarding_title);
        onboardingDescription = getResources().getStringArray(R
                .array.onboarding_description);

        sharedPreferences = getSharedPreferences(getString(R
                .string.agent_shared_preference_name), Context.MODE_PRIVATE);

        onboarderPage = getFirstOnboardingPage();
        onboarderPageList.add(onboarderPage);

        onboarderPage = getSecondOnboardingPage();
        onboarderPageList.add(onboarderPage);

        onboarderPage = getThirdOnboardingPage();
        onboarderPageList.add(onboarderPage);

        setActiveIndicatorColor(R.color.onboardingActiveIndicator);
        shouldDarkenButtonsLayout(true);
        setDividerVisibility(View.GONE);
        setSkipButtonTitle(getResources().getString(R.string.skip));
        setFinishButtonTitle(getResources().getString(R.string.finish));
        setOnboardPagesReady(onboarderPageList);
    }

    private OnboarderPage getFirstOnboardingPage() {
        OnboarderPage firstOnboarderPage = new OnboarderPage
                (onboardingTitles[0],
                        onboardingDescription[0], R
                        .drawable.onboarding1);
        firstOnboarderPage.setTitleColor(R.color
                .onboardingTitle);
        firstOnboarderPage.setDescriptionColor(R.color
                .onboardingTitle);
        firstOnboarderPage.setBackgroundColor(R.color
                .white);

        return firstOnboarderPage;
    }

    private OnboarderPage getSecondOnboardingPage() {
        OnboarderPage secondOnboarderPage = new OnboarderPage
                (onboardingTitles[1],
                        onboardingDescription[1], R
                        .drawable.onboarding2);
        secondOnboarderPage.setTitleColor(R.color
                .onboardingTitle);
        secondOnboarderPage.setDescriptionColor(R.color
                .onboardingTitle);
        secondOnboarderPage.setBackgroundColor(R.color
                .white);

        return secondOnboarderPage;
    }

    private OnboarderPage getThirdOnboardingPage() {

        OnboarderPage thirdOnboarderPage = new OnboarderPage
                (onboardingTitles[2],
                        onboardingDescription[2], R
                        .drawable.onboarding3);
        thirdOnboarderPage.setTitleColor(R.color
                .onboardingTitle);
        thirdOnboarderPage.setDescriptionColor(R.color
                .onboardingTitle);
        thirdOnboarderPage.setBackgroundColor(R.color
                .white);

        return thirdOnboarderPage;
    }

    @Override
    public void onFinishButtonPressed() {
        saveUserState();
        goToMainActivity();
    }

    @Override
    public void onSkipButtonPressed() {
        saveUserState();
        goToMainActivity();
    }

    private void saveUserState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getResources().getString(R.string.is_user_new), false);
        editor.apply();
    }

    private void goToMainActivity() {
        Intent mainActivityIntent = new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }
}
