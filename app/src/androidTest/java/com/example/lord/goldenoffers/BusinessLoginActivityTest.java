package com.example.lord.goldenoffers;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.lord.goldenoffers.business.BusinessLoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
//tests
public class BusinessLoginActivityTest {





    @Rule
    public ActivityTestRule<BusinessLoginActivity> mActivityRule =
            new ActivityTestRule<>(BusinessLoginActivity.class);

    public BusinessLoginActivity mActivity = null;

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityRule.getActivity();

    }
    @Test
    public void testBusinessPortARegister(){
        //register
        String email = UUID.randomUUID().toString();


        Random random = new Random();
        int afm = 100000000 + random.nextInt(899999999);

        onView(withId(R.id.RegisterTextView)).perform(click());
        onView(withId(R.id.etEmail)).perform(typeText(email+"@gmail.com"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"));
        onView(withId(R.id.etRepeatPass)).perform(typeText("123456"));
        onView(withId(R.id.etUserName)).perform(typeText("master"));
        onView(withId(R.id.etOwner)).perform(typeText("giorgos"));
        onView(withId(R.id.etAfm)).perform(typeText(String.valueOf(afm)));
        onView(withId(R.id.btnCurrent)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.RegisterBtn)).perform(click());

    }
    @Test
    public void testBusinessPortBLogin() {
        //login
        onView(withId(R.id.email)).perform(typeText("giorgos@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("123456"));
        onView(withId(R.id.LoginBtn)).perform(click());

        onView(withId(R.id.LogoutBtn)).perform(click());


    }




}



