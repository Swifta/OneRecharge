package com.swifta.onerecharge;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.swifta.onerecharge.resetagentpassword.ProfileActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.swifta.onerecharge.util.InternetConnectivity.isDeviceConnected;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;

/**
 * Created by moyinoluwa on 12/15/16.
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {

    private String dummyPassword = "password";
    private String emptyPassword = "";
    private String successfulMessage = "Successful";

    @Rule
    public ActivityTestRule<ProfileActivity> activityTestRule = new
            ActivityTestRule<ProfileActivity>(ProfileActivity.class);

    @Before
    public void setUp() {

    }

    @Test
    public void displayAllPasswordFieldsEmptyError() {
        onView(withId(R.id.old_password)).perform(clearText(), typeText(emptyPassword), ViewActions
                .closeSoftKeyboard());
        onView(withId(R.id.new_password)).perform(clearText(), typeText(emptyPassword),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.request_password_change)).perform(click());

        onView(withId(R.id.old_password_layout)).check(matches(hasTextInputLayoutErrorTest
                (activityTestRule.getActivity().getString(R.string.password_empty_error))));
        onView(withId(R.id.new_password_layout)).check(matches(hasTextInputLayoutErrorTest
                (activityTestRule.getActivity().getString(R.string.password_empty_error))));
    }

    @Test
    public void displayOldPasswordFieldEmptyError() {
        onView(withId(R.id.old_password)).perform(clearText(), typeText(emptyPassword), ViewActions
                .closeSoftKeyboard());
        onView(withId(R.id.new_password)).perform(clearText(), typeText(dummyPassword),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.request_password_change)).perform(click());
        onView(withId(R.id.old_password_layout)).check(matches(hasTextInputLayoutErrorTest
                (activityTestRule.getActivity().getString(R.string.password_empty_error))));
    }

    @Test
    public void displayNewPasswordFieldEmptyError() {
        onView(withId(R.id.old_password)).perform(clearText(), typeText(dummyPassword), ViewActions
                .closeSoftKeyboard());
        onView(withId(R.id.new_password)).perform(clearText(), typeText(emptyPassword),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.request_password_change)).perform(click());
        onView(withId(R.id.new_password_layout)).check(matches(hasTextInputLayoutErrorTest
                (activityTestRule.getActivity().getString(R.string.password_empty_error))));
    }

    @Test
    public void performPasswordChange() {
        onView(withId(R.id.old_password)).perform(clearText(), typeText(dummyPassword),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.new_password)).perform(clearText(), typeText(dummyPassword),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.request_password_change)).perform(click());

        ProfileActivity activity = activityTestRule.getActivity();

        if (!isDeviceConnected(activity)) {
            onView(withText(R.string.internet_error)).inRoot(withDecorView(not(is(activity.getWindow
                    ().getDecorView())))).check(matches(isDisplayed()));
        } else {
            onView(withText(successfulMessage)).inRoot(withDecorView(not(is(activity.getWindow
                    ().getDecorView())))).check(matches(isDisplayed()));
        }
    }

    public static Matcher<View> hasTextInputLayoutErrorTest(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (!(item instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) item).getError();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
