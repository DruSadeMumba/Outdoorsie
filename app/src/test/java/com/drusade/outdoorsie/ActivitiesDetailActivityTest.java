package com.drusade.outdoorsie;

import android.content.Intent;
import android.widget.TextView;

import com.drusade.outdoorsie.ui.ActivitiesDetailActivity;
import com.drusade.outdoorsie.ui.ProfileActivity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)

public class ActivitiesDetailActivityTest extends TestCase {

    private ActivitiesDetailActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(ActivitiesDetailActivity.class)
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