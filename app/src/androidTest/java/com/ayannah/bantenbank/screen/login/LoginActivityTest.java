package com.ayannah.bantenbank.screen.login;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.ayannah.bantenbank.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivity = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity mLoginActivity = null;

    String phoneNumber = "081234567890";
    String password = "password";

    @Before
    public void setUp() throws Exception {

        mLoginActivity = mActivity.getActivity();
    }

    @Test
    public void testFillColumn(){

//        LoginFragment loginFragment = mLoginActivity.mFragment;
//        View view = loginFragment.getView().findViewById(R.id.etPhone);

        //input number
        Espresso.onView(withId(R.id.etPhone)).perform(typeText(phoneNumber));

        //input password
        Espresso.onView(withId(R.id.etPassword)).perform(typeText(password));

        //close keyboard soft
        Espresso.closeSoftKeyboard();

        //perform click button
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        //perform click menu loan
        Espresso.onView(withId(R.id.recycler_menuproducts)).perform(click());

    }

    @After
    public void tearDown() throws Exception {

        mLoginActivity = null;
    }
}