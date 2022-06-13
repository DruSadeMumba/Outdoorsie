package com.drusade.outdoorsie;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.drusade.outdoorsie.ui.ActivitiesDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class ActivitiesDetailActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<ActivitiesDetailActivity> activityRule =
            new ActivityScenarioRule<>(ActivitiesDetailActivity.class);

    @Test
    public void viewProfileButtonSendsToProfileActivity(){
        onView(withId(R.id.viewProfileButton)).perform(click());
    }

}