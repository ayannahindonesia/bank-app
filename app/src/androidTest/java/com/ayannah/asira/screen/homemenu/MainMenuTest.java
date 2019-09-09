package com.ayannah.asira.screen.homemenu;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainMenuTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> mActivity = new ActivityTestRule<>(MainMenuActivity.class);

    private MainMenuActivity mMainMenuActivity = null;

    @Before
    public void setUp() throws Exception {

        mMainMenuActivity = mActivity.getActivity();
    }

    @Test
    public void testClickMenu(){

        //perform click menu loan
//        Espresso.onView(withId(R.id.recycler_menuproducts)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @After
    public void tearDown() throws Exception {

        mMainMenuActivity = null;
    }
}