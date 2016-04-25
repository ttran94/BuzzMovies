package com.example.taitran.buzzmovie.controller;
import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.clearText;
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
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.allOf;


@SuppressWarnings("unused")
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
        onView(withId(R.id.regUserName)).perform(typeText("ttran1249"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("12342"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Registration Success")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void checkUsernameAleadyExists() {
        onView(withId(R.id.regEmail)).perform(typeText("taitran123@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.regUserName)).perform(typeText("12342"), closeSoftKeyboard());
        onView(withId(R.id.regPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn)).perform(click());
        onView(withText("Username already exists")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.regText)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("taitran321334722"), closeSoftKeyboard());
        onView(withId(R.id.Pass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Username does not exist.")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //password
        onView(withId(R.id.userName)).perform(clearText(), typeText("12342"), closeSoftKeyboard());
        onView(withId(R.id.Pass)).perform(clearText(), typeText("1234567"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Incorrect password")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        ///login
        onView(withId(R.id.userName)).perform(clearText(), typeText("12342"), closeSoftKeyboard());
        onView(withId(R.id.Pass)).perform(clearText(), typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Login Success")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //changed email
        onView(withId(R.id.editEmailBtn)).perform(click());
        onView(withId(R.id.editEmailText)).perform(clearText(), typeText("taitran1234@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.editEmailBtn)).perform(click());
        onView(withText("Email Changed")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        //editBio
        onView(withId(R.id.editBioBtn)).perform(click());
        onView(withId(R.id.editBioText)).perform(clearText(), typeText("Hello there! testing"), closeSoftKeyboard());
        onView(withId(R.id.editBioBtn)).perform(click());
        onView(withText("Bio Changed")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        //major
        onView(withId(R.id.editMajorBtn)).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Math"))).perform(click());
        onView(withId(R.id.editMajorBtn)).perform(click());
        onView(withId(R.id.editPassBtn)).perform(click());
        //passwordmatch
        onView(withId(R.id.oldPassText)).perform(clearText(), typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.newPassText)).perform(clearText(), typeText("111"), closeSoftKeyboard());
        onView(withId(R.id.newPassVerifyText)).perform(clearText(), typeText("1111"), closeSoftKeyboard());
        onView(withId(R.id.pwPressed)).perform(click());
        onView(withText("Passwords do not match.")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //password
        onView(withId(R.id.oldPassText)).perform(clearText(), typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.newPassText)).perform(clearText(), typeText("1111"), closeSoftKeyboard());
        onView(withId(R.id.newPassVerifyText)).perform(clearText(), typeText("1111"), closeSoftKeyboard());
        onView(withId(R.id.pwPressed)).perform(click());
        onView(withText("Wrong password")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //passwordchanged
        onView(withId(R.id.oldPassText)).perform(clearText(), typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.newPassText)).perform(clearText(), typeText("1234567"), closeSoftKeyboard());
        onView(withId(R.id.newPassVerifyText)).perform(clearText(), typeText("1234567"), closeSoftKeyboard());
        onView(withId(R.id.pwPressed)).perform(click());
        onView(withText("Success")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.search_button)).perform(click());
        //search1
        onView(withId(R.id.searchBar)).perform(clearText(), typeText("Testing Testing"), closeSoftKeyboard());
        onView(withId(R.id.goBtn)).perform(click());

        //search2
        onView(withId(R.id.searchBar)).perform(clearText(), typeText("asdasdasdasddfggdfgfdgfdgfdgfdgdffgdgfdfgdgfdgfdgfdgdfdfgfgdfgdfgdfgcbvvvcvbcvbvcbvbvbbbbbbbbbbbsdas"), closeSoftKeyboard());
        onView(withId(R.id.goBtn)).perform(click());

        //checkRating
        onView(withId(R.id.searchBar)).perform(clearText(), typeText("iron man"), closeSoftKeyboard());
        onView(withId(R.id.goBtn)).perform(click());
    }
}
