package com.andware.tetravex;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainMenuTests {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void setup(){
    }

    @Test
    public void checkAllButtons(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.classicButton)).check(matches(isDisplayed()));
        onView(withId(R.id.timeTrialButton)).check(matches(isDisplayed()));
        onView(withId(R.id.unknownTitleButton)).check(matches(isDisplayed()));
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.leaderboardButton)).check(matches(isDisplayed()));
        onView(withId(R.id.exitMainButton)).check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonClassic(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.classicButton)).perform(click());
        onView(withId(R.id.pause)).check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonTimeTrial(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.timeTrialButton)).perform(click());
        onView(withId(R.id.resetButton)).check(matches(isDisplayed()));
    }


    @Test
    public void checkButtonLeaderboard(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.leaderboardButton)).perform(click());
        onView(withId(R.id.classicButtonLeaderboard)).check(matches(isDisplayed()));
        onView(withId(R.id.timeTrialLeaderboard)).check(matches(isDisplayed()));
        onView(withId(R.id.arcadeButtonLeaderboard)).check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonSettings(){
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.settingsButton)).perform(click());
        onView(withText(R.string.description_color_tiles)).check(matches(isDisplayed()));
    }

    @Test
    public void checkBackButtonConfirm() {
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.classicButton)).check(matches(isDisplayed()));
        pressBack();
        onView(withText(R.string.dialog_exit_to_login)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_ok)).perform(click());
        onView(withId(R.id.email_sign_in_button)).check(matches(isDisplayed()));
    }

    @Test
    public void checkBackButtonCancel() {
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(typeText("Andrew"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.classicButton)).check(matches(isDisplayed()));
        pressBack();
        onView(withText(R.string.dialog_exit_to_login)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_cancel)).perform(click());
        onView(withId(R.id.classicButton)).check(matches(isDisplayed()));
    }


    @After
    public void finish(){

    }
}
