package com.example.andware.tetravex;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.MenuInflater;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsTests {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityRule = new ActivityTestRule<>(
            SettingsActivity.class);

    @Before
    public void setup(){
    }

    @Test
    public void sizeChange(){
        onView(withText(R.string.title_size)).perform(click());
        onView(withText(R.string.five_x_five)).perform(click());
        onView(withText(R.string.five_x_five)).check(matches(isDisplayed()));
        onView(withText(R.string.title_size)).perform(click());
        onView(withText(R.string.three_x_three)).perform(click());
        onView(withText(R.string.three_x_three)).check(matches(isDisplayed()));
    }

    @Test
    public void difficultyChange(){
        onView(withText(R.string.title_difficulty)).perform(click());
        onView(withText(R.string.hard)).perform(click());
        onView(withText(R.string.hard)).check(matches(isDisplayed()));
        onView(withText(R.string.title_difficulty)).perform(click());
        onView(withText(R.string.easy)).perform(click());
        onView(withText(R.string.easy)).check(matches(isDisplayed()));
    }

    @Test
    public void colourChange(){
        onView(withText(R.string.title_colour)).perform(click());
        onView(withText(R.string.blue)).perform(click());
        onView(withText(R.string.blue)).check(matches(isDisplayed()));
        onView(withText(R.string.title_colour)).perform(click());
        onView(withText(R.string.normal)).perform(click());
        onView(withText(R.string.normal)).check(matches(isDisplayed()));
    }

    @After
    public void finish(){

    }
}
