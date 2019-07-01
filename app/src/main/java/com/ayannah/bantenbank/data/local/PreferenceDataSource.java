package com.ayannah.bantenbank.data.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ayannah.bantenbank.BuildConfig;

import javax.inject.Inject;

public class PreferenceDataSource implements PreferenceRepository {

    private SharedPreferences mPreferences;

    private static final String PREF_TOKEN_PUBLIC = "TOKEN_PUBLIC";
    private static final String PREF_USER_TOKEN = "USER_TOKEN";
    private static final String PREF_IS_LOGGED = "IS_LOGGED";
    private static final String PREF_USER_SETUP = "USER_SETUP";
    private static final String PREF_ID_USER = "ID_USER";
    private static final String PREF_USER_PHONE = "USER_PHONE";
    private static final String PREF_USER_EMAIL = "USER_EMAIL";
    private static final String PREF_USER_NAME = "USER_NAME";
    private static final String PREF_USER_PROFILE_= "USER_PROFILE";
    private static final String PREF_USER_BANK = "USER_BANK";
    private static final String PREF_USER_NIP = "USER_NIP";
    private static final String PREF_USER_PRIMARY_INCOME = "USER_PRIMARY_INCOME";
    private static final String PREF_USER_OTHER_INCOME = "USER_OTHER_INCOME";
    private static final String PREF_USER_OTHER_SOURCE_INCOME = "USER_OTHER_SOURCE_INCOME";



    @Inject
    PreferenceDataSource(Application application){
        mPreferences = application.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

    }

    @Override
    public void setUserLogged(boolean isLogged) {
        mPreferences.edit().putBoolean(PREF_IS_LOGGED, isLogged).apply();
    }

    @Override
    public boolean isUserLogged() {
        return mPreferences.getBoolean(PREF_IS_LOGGED, false);
    }

    @Override
    public void clearAll() {

        mPreferences.edit().clear().apply();
    }

    @Override
    public void setPublicToken(String token) {
        mPreferences.edit().putString(PREF_TOKEN_PUBLIC, token).apply();
    }

    @Override
    public String getPublicToken() {
        return mPreferences.getString(PREF_TOKEN_PUBLIC, "");
    }

    @Override
    public void setUserToken(String token) {

        mPreferences.edit().putString(PREF_USER_TOKEN, token).apply();
    }

    @Override
    public String getUserToken() {
        return mPreferences.getString(PREF_USER_TOKEN, "");
    }

    @Override
    public void setUserSetup(boolean userSetup) {

        mPreferences.edit().putBoolean(PREF_USER_SETUP, userSetup).apply();
    }

    @Override
    public boolean isUserSetup() {
        return mPreferences.getBoolean(PREF_USER_SETUP, false);
    }

    @Override
    public void setIdUser(String idUser) {

        mPreferences.edit().putString(PREF_ID_USER, idUser).apply();

    }

    @Override
    public String getIdUser() {
        return mPreferences.getString(PREF_ID_USER, "");
    }

    @Override
    public void setUserPhone(String phonenumber) {

        mPreferences.edit().putString(PREF_USER_PHONE, phonenumber).apply();
    }

    @Override
    public String getUserPhone() {
        return mPreferences.getString(PREF_USER_PHONE, "");
    }

    @Override
    public void setUserEmail(String email) {

        mPreferences.edit().putString(PREF_USER_EMAIL, email).apply();

    }

    @Override
    public String getUserEmail() {
        return mPreferences.getString(PREF_USER_EMAIL, "");
    }

    @Override
    public void setUserName(String name) {

        mPreferences.edit().putString(PREF_USER_NAME, name).apply();
    }

    @Override
    public String getUserName() {
        return mPreferences.getString(PREF_USER_NAME, "");
    }

    @Override
    public void setUserBank(String bankName) {

        mPreferences.edit().putString(PREF_USER_BANK, bankName).apply();
    }

    @Override
    public String getUserBank() {
        return mPreferences.getString(PREF_USER_BANK, "");
    }

    @Override
    public void setUserProfileImage(String profileImage) {

        mPreferences.edit().putString(PREF_USER_PROFILE_, profileImage).apply();
    }

    @Override
    public String getUserProfile() {
        return mPreferences.getString(PREF_USER_PROFILE_, "");
    }

    @Override
    public void setUserNIP(String userNIP) {

        mPreferences.edit().putString(PREF_USER_NIP, userNIP).apply();

    }

    @Override
    public String getUserNIP() {
        return mPreferences.getString(PREF_USER_NIP, "");
    }

    @Override
    public void setUserPrimaryIncome(String userIncome) {

        mPreferences.edit().putString(PREF_USER_PRIMARY_INCOME, userIncome).apply();
    }

    @Override
    public String getUserPrimaryIncome() {

        return mPreferences.getString(PREF_USER_PRIMARY_INCOME, "");
    }

    @Override
    public void setUserOtherIncome(String otherIncome) {

        mPreferences.edit().putString(PREF_USER_OTHER_INCOME, otherIncome).apply();

    }

    @Override
    public String getUserOtherIncome() {
        return mPreferences.getString(PREF_USER_OTHER_INCOME, "");
    }

    @Override
    public void setuserOtherSourceIncome(String otherSourceIncome) {

        mPreferences.edit().putString(PREF_USER_OTHER_SOURCE_INCOME, otherSourceIncome).apply();

    }

    @Override
    public String getUserOtherSourceIncome() {

        return mPreferences.getString(PREF_USER_OTHER_SOURCE_INCOME, "");

    }
}