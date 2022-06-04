package com.drusade.outdoorsie;

import android.content.Intent;
import android.widget.TextView;

import com.drusade.outdoorsie.ui.LoginActivity;
import com.drusade.outdoorsie.ui.MainActivity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)

public class MainActivityTest extends TestCase {

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void validateTextViewHeadingContent(){
        TextView appNameText = activity.findViewById(R.id.appNameText);
        assertEquals("Outdoorsie", appNameText.getText().toString());
    }

    @Test
    public void validateTextViewSloganContent(){
        TextView appSloganText = activity.findViewById(R.id.appSloganText);
        assertEquals("Home of Adventure", appSloganText.getText().toString());
    }

    @Test
    public void secondActivityStarted(){
        activity.findViewById(R.id.loginButton).performClick();
        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}