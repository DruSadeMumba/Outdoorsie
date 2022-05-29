package com.drusade.outdoorsie;

import static org.junit.Assert.*;

import android.content.Intent;
import android.widget.TextView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)

public class ActivitiesActivityTest  extends TestCase {

    private ActivitiesActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(ActivitiesActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void validateTextViewHeadingContent(){
        TextView activitiesTextView = activity.findViewById(R.id.activitiesTextView);
        assertEquals("Activities", activitiesTextView.getText().toString());
    }

    @Test
    public void nextActivityStarted(){
        activity.findViewById(R.id.viewProfileButton).performClick();
        Intent expectedIntent = new Intent(activity, ProfileActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}