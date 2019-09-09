package com.ayannah.asira.screen;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.register.choosebank.ChooseBankActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterTest_1 {

    private String ktp = "16002009212290049"; //harus diganti sebelum test ini dijalankan
    private String email = "test@kevin.com";
    private String nomorPonsel = "081992288234"; //harus diganti sebelum test ini dijalankan



    @Rule
    public ActivityTestRule<ChooseBankActivity> mActivityTestRule = new ActivityTestRule<>(ChooseBankActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void registerTest_1() {
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rvBank),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1)),
                        1),
                        isDisplayed()));
        linearLayout.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnBelum), withText("Tidak"),
                        childAtPosition(
                                allOf(withId(R.id.lyButton),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.buttonDialog), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_bottom_sheet),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.imgKTP),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0)));
        appCompatImageView.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.btnCapture),
                        childAtPosition(
                                allOf(withId(R.id.captureLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etKTP),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etKTP),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatEditText2.perform(scrollTo(), typeText(ktp), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.regist_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.svMain),
                                        0),
                                8)));
        appCompatEditText3.perform(scrollTo(), typeText(email), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.regist_phone),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        10),
                                1)));
        appCompatEditText4.perform(scrollTo(), typeText(nomorPonsel), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.regist_pass),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.svMain),
                                        0),
                                12)));
        appCompatEditText5.perform(scrollTo(), typeText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.regist_pass_retype),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.svMain),
                                        0),
                                14)));
        appCompatEditText6.perform(scrollTo(), typeText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnNext), withText("Selanjutnya"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.regist_name),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1)));
        appCompatEditText7.perform(scrollTo(), typeText("ganteng"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rbPria), withText("Laki-laki"),
                        childAtPosition(
                                allOf(withId(R.id.rgJenisKelamin),
                                        childAtPosition(
                                                withId(R.id.container1),
                                                3)),
                                0)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.regist_dateBirth), withText("dd-mm-yyyy"),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                5)));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.regist_tempatLahir),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                7)));
        appCompatEditText8.perform(scrollTo(), typeText("jakarta"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.regist_namaIbu),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                11)));
        appCompatEditText9.perform(scrollTo(), typeText("itta"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.regist_alamatDomisili),
                        childAtPosition(
                                allOf(withId(R.id.container3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                2)));
        appCompatEditText10.perform(scrollTo(), typeText("jalan aja"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spProvinsi),
                        childAtPosition(
                                allOf(withId(R.id.container3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                4)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatTextView2.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spKota),
                        childAtPosition(
                                allOf(withId(R.id.container3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                6)));
        appCompatSpinner2.perform(scrollTo(), click());

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        appCompatTextView3.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spKecamatan),
                        childAtPosition(
                                allOf(withId(R.id.container3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                8)));
        appCompatSpinner3.perform(scrollTo(), click());

        DataInteraction appCompatTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        appCompatTextView4.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spKelurahan),
                        childAtPosition(
                                allOf(withId(R.id.container3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                10)));
        appCompatSpinner4.perform(scrollTo(), click());

        DataInteraction appCompatTextView5 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatTextView5.perform(click());

        onView(withId(R.id.regist_rt))
                .perform(scrollTo());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.regist_rt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lyRayon),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText11.perform(typeText("01"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.regist_rw),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.lyRayon),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(typeText("10"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.regist_phoneBorrower),
                        childAtPosition(
                                allOf(withId(R.id.container3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                13)));
        appCompatEditText13.perform(scrollTo(), typeText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.regist_lamaMenempatiRumah),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container3),
                                        15),
                                0)));
        appCompatEditText14.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());
//
//        DataInteraction appCompatTextView6 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(1);
//        appCompatTextView6.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.buttonNext), withText("Selanjutnya"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton6.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.spJenisPekerjaan),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2)));
        appCompatSpinner5.perform(scrollTo(), click());

        DataInteraction appCompatTextView7 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatTextView7.perform(click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.etEmployeeID),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4)));
        appCompatEditText15.perform(scrollTo(), typeText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.etCompanyName),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                6)));
        appCompatEditText16.perform(scrollTo(), typeText("PT ABC Maju Terus"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.etLamaBekerja),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container4),
                                        8),
                                0)));
        appCompatEditText17.perform(scrollTo(), typeText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.etAlamatKantor),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                10)));
        appCompatEditText18.perform(scrollTo(), typeText("Jalan we work togather"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.etCompanyPhone),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                12)));
        appCompatEditText19.perform(scrollTo(), typeText("123454321"), closeSoftKeyboard());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.etSpvName),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                14)));
        appCompatEditText20.perform(scrollTo(), typeText("Alex Jabat"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.etJobTitle),
                        childAtPosition(
                                allOf(withId(R.id.container4),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                16)));
        appCompatEditText21.perform(scrollTo(), typeText("Direktur kepala utama"), closeSoftKeyboard());

//        ViewInteraction appCompatEditText22 = onView(
//                allOf(withId(R.id.etGajiBulanan),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2)));
//        appCompatEditText22.perform(scrollTo(), typeText("4500"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText23 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("4,500"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2)));
//        appCompatEditText23.perform(scrollTo(), replaceText("4,5000"));
//
//        ViewInteraction appCompatEditText24 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("4,5000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2),
//                        isDisplayed()));
//        appCompatEditText24.perform(closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText25 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("45,000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2)));
//        appCompatEditText25.perform(scrollTo(), replaceText("45,0000"));
//
//        ViewInteraction appCompatEditText26 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("45,0000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2),
//                        isDisplayed()));
//        appCompatEditText26.perform(closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText27 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("450,000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2)));
//        appCompatEditText27.perform(scrollTo(), replaceText("450,0000"));
//
//        ViewInteraction appCompatEditText28 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("450,0000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2),
//                        isDisplayed()));
//        appCompatEditText28.perform(closeSoftKeyboard());

//        ViewInteraction appCompatEditText29 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("4,500,000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2)));
//        appCompatEditText29.perform(scrollTo(), typeText("4,500,0000"));

//        ViewInteraction appCompatEditText30 = onView(
//                allOf(withId(R.id.etGajiBulanan), withText("4,500,0000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                2),
//                        isDisplayed()));
//        appCompatEditText30.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.etGajiBulanan),
                        childAtPosition(
                                allOf(withId(R.id.container5),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                2)));
        appCompatEditText30.perform(scrollTo(), typeText("45000000"), closeSoftKeyboard());

//        ViewInteraction appCompatEditText31 = onView(
//                allOf(withId(R.id.etPendapatanLain),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4)));
//        appCompatEditText31.perform(scrollTo(), replaceText("1200"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText32 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("1,200"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4)));
//        appCompatEditText32.perform(scrollTo(), replaceText("1,2000"));
//
//        ViewInteraction appCompatEditText33 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("1,2000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4),
//                        isDisplayed()));
//        appCompatEditText33.perform(closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText34 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("12,000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4)));
//        appCompatEditText34.perform(scrollTo(), replaceText("12,0000"));
//
//        ViewInteraction appCompatEditText35 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("12,0000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4),
//                        isDisplayed()));
//        appCompatEditText35.perform(closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText36 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("120,000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4)));
//        appCompatEditText36.perform(scrollTo(), replaceText("120,0000"));
//
//        ViewInteraction appCompatEditText37 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("120,0000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4),
//                        isDisplayed()));
//        appCompatEditText37.perform(closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText38 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("1,200,000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4)));
//        appCompatEditText38.perform(scrollTo(), replaceText("1,200,0000"));

//        ViewInteraction appCompatEditText39 = onView(
//                allOf(withId(R.id.etPendapatanLain), withText("1,200,0000"),
//                        childAtPosition(
//                                allOf(withId(R.id.container5),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.RelativeLayout")),
//                                                3)),
//                                4),
//                        isDisplayed()));
//        appCompatEditText39.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText39 = onView(
                allOf(withId(R.id.etPendapatanLain),
                        childAtPosition(
                                allOf(withId(R.id.container5),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                4)));
        appCompatEditText39.perform(scrollTo(), typeText("12000000"), closeSoftKeyboard());

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.etSumberPendapatanLain),
                        childAtPosition(
                                allOf(withId(R.id.container5),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                6)));
        appCompatEditText40.perform(scrollTo(), typeText("dagang"), closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.buttonNext), withText("Selanjutnya"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton7.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.etRelatedName),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2)));
        appCompatEditText41.perform(scrollTo(), typeText("angel"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.spHubungan),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4)));
        appCompatSpinner6.perform(scrollTo(), click());

        DataInteraction appCompatTextView8 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatTextView8.perform(click());

        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.etRelatedAddress),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                6)));
        appCompatEditText42.perform(scrollTo(), typeText("jalan kemenangan alamat rmh"), closeSoftKeyboard());

        ViewInteraction appCompatEditText43 = onView(
                allOf(withId(R.id.etRelatedPhone),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                8)));
        appCompatEditText43.perform(scrollTo(), typeText("4254989"), closeSoftKeyboard());

        ViewInteraction appCompatEditText44 = onView(
                allOf(withId(R.id.etRelatedHP),
                        childAtPosition(
                                allOf(withId(R.id.container1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                10)));
        appCompatEditText44.perform(scrollTo(), typeText("08123456789"), closeSoftKeyboard());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.buttonNext), withText("Selanjutnya"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton8.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction pinEntryEditText = onView(
                allOf(withId(R.id.etPin),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        pinEntryEditText.perform(click());

        ViewInteraction pinEntryEditText2 = onView(
                allOf(withId(R.id.etPin),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        pinEntryEditText2.perform(typeText("888999"), closeSoftKeyboard());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
