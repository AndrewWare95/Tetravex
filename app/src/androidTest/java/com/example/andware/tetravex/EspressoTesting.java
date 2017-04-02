package com.example.andware.tetravex;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTesting {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void setup(){
    }

    @Test
    public void signIn(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.classicButton)).check(matches(isDisplayed()));
    }

    @Test
    public void signInPassword(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.classicButton)).check(matches(isDisplayed()));
    }

    @Test
    public void signInPasswordTooLong(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("AndrewWare"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.email_sign_in_button)).check(matches(isDisplayed()));
    }

    @Test
    public void signInNoPassword(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.email_sign_in_button)).check(matches(isDisplayed()));
    }

    @After
    public void finish(){

    }
}
