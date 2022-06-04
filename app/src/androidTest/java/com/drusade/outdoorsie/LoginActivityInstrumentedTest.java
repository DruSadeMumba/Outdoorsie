package com.drusade.outdoorsie;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.drusade.outdoorsie.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
       new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void validateUsernameEditText() {
        onView(withId(R.id.username)).perform(typeText("Dru"))
                .check(matches(withText("Dru")));
    }

    @Test
    public void validatePasswordEditText() {
        onView(withId(R.id.password)).perform(typeText("123@d"))
                .check(matches(withText("123@d")));
    }

    @Test
    public void locationIsSentToRestaurantsActivity() {
        String username = "Dru";
        String password = "123@d";
        onView(withId(R.id.username)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.signInButton)).perform(click());
    }

}