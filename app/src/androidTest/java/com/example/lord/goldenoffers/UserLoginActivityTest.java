package com.example.lord.goldenoffers;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.lord.goldenoffers.user.UserLoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class UserLoginActivityTest {


    @Rule
    public ActivityTestRule<UserLoginActivity> userActivityRule=
            new ActivityTestRule<>(UserLoginActivity.class);

    public UserLoginActivity userActivity = null;


    @Before
    public void setUp() throws Exception {

        userActivity = userActivityRule.getActivity();
    }
    @Test
    public void testUsersPortARegister(){
        //register
        String email = UUID.randomUUID().toString();


        Random random = new Random();
        int afm = 100000000 + random.nextInt(899999999);

        onView(withId(R.id.tv_register)).perform(click());
        onView(withId(R.id.username_input)).perform(typeText("tester"));
        onView(withId(R.id.email_input)).perform(typeText(email+"@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("123456"));
        onView(withId(R.id.password_repeat_input)).perform(typeText("123456"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register)).perform(click());

    }
    @Test
    public void testUsersPortBLogin() {
        //login
        onView(withId(R.id.email_input)).perform(typeText("hortokopi.gr@hotmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("123456"));
        onView(withId(R.id.btn_login)).perform(click());

        //add desire
        onView(withId(R.id.btn_add_desire)).perform(click());
        onView(withId(R.id.product_name_input)).perform(typeText("test"));
        onView(withId(R.id.price_low_input)).perform(typeText("1"));
        onView(withId(R.id.price_high_input)).perform(typeText("1000"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_submit)).perform(click());






        onView(withId(R.id.btn_logout)).perform(click());
    }







}
