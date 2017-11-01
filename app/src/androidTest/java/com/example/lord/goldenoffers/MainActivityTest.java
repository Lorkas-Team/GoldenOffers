package com.example.lord.goldenoffers;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by lord on 01-Nov-17.
 */

@RunWith(AndroidJUnit4.class)

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBusinessPortRegister() {

        onView(withText("Business Port")).perform(click());
        onView(withText("REGISTER")).perform(click());

    }

    @Test
    public void testBusinessPortLogin() {

        onView(withText("Business Port")).perform(click());
        onView(withText("LOGIN")).perform(click());

    }

    @Test
    public void testUsersPortRegister() {

        onView(withText("Users Port")).perform(click());
        onView(withText("REGISTER")).perform(click());

    }

    @Test
    public void testUsersPortLogin() {

        onView(withText("Users Port")).perform(click());
        onView(withText("LOGIN")).perform(click());

    }



}
