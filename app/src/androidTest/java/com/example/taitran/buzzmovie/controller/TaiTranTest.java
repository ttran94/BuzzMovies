package com.example.taitran.buzzmovie.controller;
import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class TaiTranTest {

    @Rule
    public final ActivityTestRule<RegisterActivity> mActivityRule = new ActivityTestRule<>(RegisterActivity.class);



    @Test
    public void checkShortUsername() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("tai"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Username is too short.")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void checkLongUserName() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("taitran123456789123456"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Username is too long.")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void checkValidUserName() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran@mail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("tai tran"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Invalid Username")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void checkValidEmail() {
        onView(withId(R.id.regEmail)).perform(typeText("taitranmail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("taitran66"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Invalid email address")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void checkValidPassword() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran@yahoo.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("taitran66"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Invalid password")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void checkSuccessfullyRegistered() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran123@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("taitran321312"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Registration Success")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void checkUsernameAleadyExists() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran123@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("taitran321312"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Username already exists")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

}
