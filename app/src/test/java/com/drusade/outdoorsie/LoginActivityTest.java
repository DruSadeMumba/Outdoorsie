package com.drusade.outdoorsie;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.ContentView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest extends TestCase {

    private LoginActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void validateTextViewContent(){
        TextView signInText = activity.findViewById(R.id.signInText);
        assertEquals("SIGN IN", signInText.getText().toString());
    }

    @Test
    public void nextActivityStarted(){
        activity.findViewById(R.id.signInButton).performClick();
        Intent expectedIntent = new Intent(activity, ActivitiesActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}