package com.ayannah.asira.data.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.data.model.Bank;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PreferenceDataSource implements PreferenceRepository {

    private SharedPreferences mPreferences;

    //token purposes
    private static final String PREF_TOKEN_LENDER = "TOKEN_PUBLIC_LENDER";
    private static final String PREF_TOKEN_ADMIN_LENDER = "TOKEN_ADMIN_LENDER";
    private static final String PREF_TOKEN_PUBLIC = "TOKEN_PUBLIC";
    private static final String PREF_USER_TOKEN = "USER_TOKEN";

    //for login privilledge purposes
    private static final String PREF_IS_LOGGED = "IS_LOGGED";
    private static final String PREF_USER_SETUP = "USER_SETUP";
    private static final String PREF_IS_AGENT_LOGGED = "AGENT_IS_LOGIN";

    //user attributes
    private static final String PREF_ID_USER = "ID_USER";
    private static final String PREF_USER_NAME = "USER_NAME";
    private static final String PREF_USER_GEMDER = "GENDER";
    private static final String PREF_USER_IDCARD = "IDCARD";
    private static final String PREF_USER_TAXCARD = "TAXCARD";
    private static final String PREF_USER_EMAIL = "EMAIL";
    private static final String PREF_USER_PHONE = "PHONE";
    private static final String PREF_USER_BIRTHDAY = "BIRTHDAY";
    private static final String PREF_USER_BIRTHPLACE = "BIRTHPLACE";
    private static final String PREF_USER_NIP = "USER_NIP";
    private static final String PREF_USER_LASTEDUCATION = "LASTEDUCATION";
    private static final String PREF_USER_MOTHERNAME = "MOTHERNAME";
    private static final String PREF_USER_MARRIAGESTATUS = "MARRIAGESTATUS";
    private static final String PREF_USER_SPOUSENAME = "SPOUSENAME";
    private static final String PREF_USER_SPOUSEBIRTHDATE = "SPOUSEBIRTHDATE";
    private static final String PREF_USER_SPOUSEEDUCATION = "SPOUSEEDUCATION";
    private static final String PREF_USER_ADDRESS = "ADDRESS";
    private static final String PREF_USER_PROVINCE = "PROVINCE";
    private static final String PREF_USER_CITY = "CITY";
    private static final String PREF_USER_NEIGHBOURASSOCIATION = "NEIGHBOURASSOCIATION";
    private static final String PREF_USER_HAMLETS = "HAMLETS";
    private static final String PREF_USER_HOMEPHONENUMBER = "HOMEPHONENUMBER";
    private static final String PREF_USER_SUBDISTRICT = "SUBDISTRICT";
    private static final String PREF_USER_URBANVILLAGE = "URBANVILLAGE";
    private static final String PREF_USER_HOMEOWNERSHIP = "HOMEOWNERSHIP";
    private static final String PREF_USER_LIVEDFOR = "LIVEDFOR";
    private static final String PREF_USER_OCCUPATION = "OCCUPATION";
    private static final String PREF_USER_EMPLOYEEID = "EMPLOYEEID";
    private static final String PREF_USER_EMPLOYERNAME = "EMPLOYERNAME";
    private static final String PREF_USER_EMPLOYERADDRESS = "EMPLOYERADDRESS";
    private static final String PREF_USER_DEPARTMENT = "DEPARTMENT";
    private static final String PREF_USER_BEENWORKINGFOR = "BEENWORKINGFOR";
    private static final String PREF_USER_DIRECT_SUPERIORNAME = "DIRECT_SUPERIORNAME";
    private static final String PREF_USER_EMPLYOYERNUMBER = "EMPLYOYERNUMBER";
    private static final String PREF_USER_FIELD_TO_WORK = "FIELD_TO_WORK";
    private static final String PREF_USER_RELATED_PERSONNAME = "RELATED_PERSONNAME";
    private static final String PREF_USER_RELATED_RELATION = "RELATED_RELATION";
    private static final String PREF_USER_RELATED_PHONENUMBER = "RELATED_PHONENUMBER";
    private static final String PREF_USER_RELATED_HOMENUMBER= "RELATED_HOMENUMBER";
    private static final String PREF_USER_RELATED_BANKACCOUNTNUMBER = "RELATED_BANKACCOUNTNUMBER";
    private static final String PREF_DEPEDANTS = "DEPENDANTS";

    private static final String PREF_USER_IMG_IDCARD = "IDCARD_IMG";
    private static final String PREF_USER_IMG_TAXCARD = "TAXCARD_IMG";

    private static final String PREF_USER_RELATED_ADDRESS = "RELATED_ADDRESS";
    private static final String PREF_USER_ID_CARD_ID = "ID_CARD_ID";
    private static final String PREF_USER_TAX_CARD_ID = "TAX_CARD_ID";

    private static final String PREF_USER_ID_CARD_IMAGE = "ID_CARD_IAMGE";
    private static final String PREF_USER_TAX_ID_IMAGE = "TAX_ID_IMAGE";
    private static final String PREF_USER_BANK_ID = "BANK_ID";

    private static final String PREF_BANK_ACCOUNT_BORROWER = "PREF_BANK_ACCOUNT_BORROWER";

    //user income
    private static final String PREF_USER_PRIMARY_INCOME = "USER_PRIMARY_INCOME";
    private static final String PREF_USER_OTHER_INCOME = "USER_OTHER_INCOME";
    private static final String PREF_USER_OTHER_SOURCE_INCOME = "USER_OTHER_SOURCE_INCOME";

    private static final String PREF_USER_NICKNAME = "PREF_USER_NICKNAME";
    private static final String PREF_USER_NATIONALILTY = "PREF_USER_NATIONALILTY";
    private static final String PREF_USER_LOAN_STATUS = "PREF_USER_LOAN_STATUS";
    private static final String PREF_IMAGE_PROFILE_BORROWER = "IMAGE_PROFILE_BORROWER";

    //agent atributes
    private static final String PREF_AGENT_ID = "AGENT_ID";
    private static final String PREF_AGENT_NAME = "AGENT_NAME";
    private static final String PREF_AGENT_PROFILE_IMG = "AGENT_PROFILE_IMG";
    private static final String PREF_AGENT_USERNAME = "AGENT_USERNAME";
    private static final String PREF_AGENT_EMAIL = "AGENT_EMAIL";
    private static final String PREF_AGENT_PHONE = "AGENT_PHONE";
    private static final String PREF_AGENT_PROVIDER = "AGENT_PROVIDER";
    private static final String PREF_AGENT_CATEGORY = "AGENT_CATEGORY";
    private static final String PREF_AGENT_STATUS = "AGENT_STATUS";
    private static final String PREF_AGENT_BANKS = "AGENT_BANKS";
    private static final String PREF_AGENT_BANKS_NAME = "AGENT_BANKS_NAME";

    //for agent purposes
    private static final String PREF_AGENT_PRIMARY_INCOME_BORROWER = "PREF_AGENT_PRIMARY_INCOME_BORROWER";
    private static final String PREF_AGENT_SECONDARY_INCOME_BORROWER = "PREF_AGENT_SECONDARY_INCOME_BORROWER";
    private static final String PREF_AGENT_OTHER_INCOME_BORROWER = "PREF_AGENT_OTHER_INCOME_BORROWER";


    @Inject
    public PreferenceDataSource(Application application){
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
    public void setSpouseEducation(String spouseEducation) {
        mPreferences.edit().putString(PREF_USER_SPOUSEEDUCATION, spouseEducation).apply();
    }

    @Override
    public String getSpouseEducation() {
        return mPreferences.getString(PREF_USER_SPOUSEEDUCATION, "");
    }

    @Override
    public String getUserOtherSourceIncome() {

        return mPreferences.getString(PREF_USER_OTHER_SOURCE_INCOME, "");

    }

    @Override
    public void setUserGender(String gender) {

        mPreferences.edit().putString(PREF_USER_GEMDER, gender).apply();

    }

    @Override
    public String getUserGender() {
        return mPreferences.getString(PREF_USER_GEMDER, "");
    }

    @Override
    public void setIdCardUser(String idCarf) {

        mPreferences.edit().putString(PREF_USER_IDCARD, idCarf).apply();

    }

    @Override
    public String getIdCard() {
        return mPreferences.getString(PREF_USER_IDCARD, "");
    }

    @Override
    public void setTaxCard(String taxCard) {

        mPreferences.edit().putString(PREF_USER_TAXCARD, taxCard).apply();
    }

    @Override
    public String getTaxCard() {
        return mPreferences.getString(PREF_USER_TAXCARD, "");
    }

    @Override
    public void setUserBirtday(String birthdate) {

        mPreferences.edit().putString(PREF_USER_BIRTHDAY, birthdate).apply();
    }

    @Override
    public String getUserBirthdate() {
        return mPreferences.getString(PREF_USER_BIRTHDAY, "");
    }

    @Override
    public void setUserBirthPlace(String place) {

        mPreferences.edit().putString(PREF_USER_BIRTHPLACE, place).apply();

    }

    @Override
    public String getUserBirthplace() {
        return mPreferences.getString(PREF_USER_BIRTHPLACE, "");
    }

    @Override
    public void setUserLastEducation(String education) {

        mPreferences.edit().putString(PREF_USER_LASTEDUCATION, education).apply();
    }

    @Override
    public String getUserLastEducation() {
        return mPreferences.getString(PREF_USER_LASTEDUCATION, "");
    }

    @Override
    public void setUserMotherName(String motherName) {

        mPreferences.edit().putString(PREF_USER_MOTHERNAME, motherName).apply();
    }

    @Override
    public String getUserMotherName() {
        return mPreferences.getString(PREF_USER_MOTHERNAME, "");
    }

    @Override
    public void setUserMarriageStatus(String marriageStatus) {

        mPreferences.edit().putString(PREF_USER_MARRIAGESTATUS, marriageStatus).apply();

    }

    @Override
    public String getUserMarriageStatus() {
        return mPreferences.getString(PREF_USER_MARRIAGESTATUS, "");
    }

    @Override
    public void setUserSpouseName(String spouseName) {

        mPreferences.edit().putString(PREF_USER_SPOUSENAME, spouseName).apply();
    }

    @Override
    public String getUserSpouseName() {
        return mPreferences.getString(PREF_USER_SPOUSENAME, "");
    }

    @Override
    public void setSpouseBirthDate(String spouseBirthDate) {
        mPreferences.edit().putString(PREF_USER_SPOUSEBIRTHDATE, spouseBirthDate).apply();
    }

    @Override
    public String getSpouserBirthdate() {
        return mPreferences.getString(PREF_USER_SPOUSEBIRTHDATE, "");
    }

    @Override
    public void setUserAddress(String address) {

        mPreferences.edit().putString(PREF_USER_ADDRESS, address).apply();
    }

    @Override
    public String getUserAddress() {
        return mPreferences.getString(PREF_USER_ADDRESS, "");
    }

    @Override
    public void setUserProvince(String province) {

        mPreferences.edit().putString(PREF_USER_PROVINCE, province).apply();
    }

    @Override
    public String getuserProvince() {
        return mPreferences.getString(PREF_USER_PROVINCE, "");
    }

    @Override
    public void setUserCity(String city) {

        mPreferences.edit().putString(PREF_USER_CITY, city).apply();
    }

    @Override
    public String getUserCity() {
        return mPreferences.getString(PREF_USER_CITY, "");
    }

    @Override
    public void setUserNeighbourAssociation(String neighbourAssociation) {

        mPreferences.edit().putString(PREF_USER_NEIGHBOURASSOCIATION, neighbourAssociation).apply();
    }

    @Override
    public String getUserNeighbourAssociation() {
        return mPreferences.getString(PREF_USER_NEIGHBOURASSOCIATION, "");
    }

    @Override
    public void setUserHamlets(String hamlets) {

        mPreferences.edit().putString(PREF_USER_HAMLETS, hamlets).apply();
    }

    @Override
    public String getUserHamlets() {
        return mPreferences.getString(PREF_USER_HAMLETS, "");
    }

    @Override
    public void setUserHomePhoneNumber(String homePhoneNumber) {

        mPreferences.edit().putString(PREF_USER_HOMEPHONENUMBER, homePhoneNumber).apply();
    }

    @Override
    public String getUserHomePhoneNumber() {
        return mPreferences.getString(PREF_USER_HOMEPHONENUMBER, "");
    }

    @Override
    public void setSubDistrict(String subDistrict) {

        mPreferences.edit().putString(PREF_USER_SUBDISTRICT, subDistrict).apply();
    }

    @Override
    public String getSubDistrict() {
        return mPreferences.getString(PREF_USER_SUBDISTRICT, "");
    }

    @Override
    public void setUrbanVillage(String urbanVillage) {

        mPreferences.edit().putString(PREF_USER_URBANVILLAGE, urbanVillage).apply();
    }

    @Override
    public String getUrbanVillage() {
        return mPreferences.getString(PREF_USER_URBANVILLAGE, "");
    }

    @Override
    public void setHomeOwnerShip(String homeOwnerShip) {

        mPreferences.edit().putString(PREF_USER_HOMEOWNERSHIP, homeOwnerShip).apply();
    }

    @Override
    public String getHomeOwnerShip() {
        return mPreferences.getString(PREF_USER_HOMEOWNERSHIP, "");
    }

    @Override
    public void setLivedFor(String livedFor) {

        mPreferences.edit().putString(PREF_USER_LIVEDFOR, livedFor).apply();
    }

    @Override
    public String getLivedFor() {
        return mPreferences.getString(PREF_USER_LIVEDFOR, "");
    }

    @Override
    public void setOccupation(String occupation) {

        mPreferences.edit().putString(PREF_USER_OCCUPATION, occupation).apply();
    }

    @Override
    public String getOccupation() {
        return mPreferences.getString(PREF_USER_OCCUPATION, "");
    }

    @Override
    public void setEmployeeId(String employeeId) {

        mPreferences.edit().putString(PREF_USER_EMPLOYEEID, employeeId).apply();
    }

    @Override
    public String getEmployeeId() {
        return mPreferences.getString(PREF_USER_EMPLOYEEID, "");
    }

    @Override
    public void setEmployerName(String employerName) {

        mPreferences.edit().putString(PREF_USER_EMPLOYERNAME, employerName).apply();
    }

    @Override
    public String getEmployerName() {
        return mPreferences.getString(PREF_USER_EMPLOYERNAME, "");
    }

    @Override
    public void setEmployerAddress(String employerAddress) {
        mPreferences.edit().putString(PREF_USER_EMPLOYERADDRESS, employerAddress).apply();
    }

    @Override
    public String getEmployerAddress() {
        return mPreferences.getString(PREF_USER_EMPLOYERADDRESS, "");
    }

    @Override
    public void setDepartment(String department) {

        mPreferences.edit().putString(PREF_USER_DEPARTMENT, department).apply();
    }

    @Override
    public String getDepartment() {
        return mPreferences.getString(PREF_USER_DEPARTMENT, "");
    }

    @Override
    public void setBeenWorkingFor(String beenWorkingFor) {

        mPreferences.edit().putString(PREF_USER_BEENWORKINGFOR, beenWorkingFor).apply();

    }

    @Override
    public String getBeenWorkingFor() {
        return mPreferences.getString(PREF_USER_BEENWORKINGFOR, "");
    }

    @Override
    public void setDirectSuperiorName(String superiorName) {

        mPreferences.edit().putString(PREF_USER_DIRECT_SUPERIORNAME, superiorName).apply();

    }

    @Override
    public String getDirectSuperiorName() {
        return mPreferences.getString(PREF_USER_DIRECT_SUPERIORNAME, "");
    }

    @Override
    public void setEmployerNumber(String employerNumber) {

        mPreferences.edit().putString(PREF_USER_EMPLYOYERNUMBER, employerNumber).apply();
    }

    @Override
    public String getEmployerNumber() {
        return mPreferences.getString(PREF_USER_EMPLYOYERNUMBER, "");
    }

    @Override
    public void setFieldToWork(String fieldToWork) {

        mPreferences.edit().putString(PREF_USER_FIELD_TO_WORK, fieldToWork).apply();
    }

    @Override
    public String getFieldToWork() {
        return mPreferences.getString(PREF_USER_FIELD_TO_WORK, "");
    }

    @Override
    public void setUserRelatedPhoneNumber(String relatedPhoneNumber) {

        mPreferences.edit().putString(PREF_USER_RELATED_PHONENUMBER, relatedPhoneNumber).apply();
    }

    @Override
    public String getUserRelatePhoneNumber() {
        return mPreferences.getString(PREF_USER_RELATED_PHONENUMBER, "");
    }

    @Override
    public void setUserRelatedPersonName(String relatedPersonName) {

        mPreferences.edit().putString(PREF_USER_RELATED_PERSONNAME, relatedPersonName).apply();
    }

    @Override
    public String getUserRelatedPersonName() {
        return mPreferences.getString(PREF_USER_RELATED_PERSONNAME, "");
    }

    @Override
    public void setUserRelatedRelation(String userRelatedRelation) {

        mPreferences.edit().putString(PREF_USER_RELATED_RELATION, userRelatedRelation).apply();

    }

    @Override
    public String getUserRelatedRelation() {
        return mPreferences.getString(PREF_USER_RELATED_RELATION, "");
    }

    @Override
    public void setUserRelatedHomeNumber(String relatedHomeNumber) {

        mPreferences.edit().putString(PREF_USER_RELATED_HOMENUMBER, relatedHomeNumber).apply();
    }

    @Override
    public String getUserRelatedHomeNumber() {
        return mPreferences.getString(PREF_USER_RELATED_HOMENUMBER, "");
    }

    @Override
    public void setUserRelatedBankAccountNumber(String relatedBankAccountNumber) {

        mPreferences.edit().putString(PREF_USER_RELATED_BANKACCOUNTNUMBER, relatedBankAccountNumber).apply();
    }

    @Override
    public String getUserRelatedBankAccountNumber() {

        return mPreferences.getString(PREF_USER_RELATED_BANKACCOUNTNUMBER, "");
    }

    @Override
    public void setDependants(int count) {
        mPreferences.edit().putInt(PREF_DEPEDANTS, count).apply();
    }

    @Override
    public int getDependants() {
        return mPreferences.getInt(PREF_DEPEDANTS, 0);
    }

    @Override
    public void setUserRelatedAddress(String relatedAddress) {
        mPreferences.edit().putString(PREF_USER_RELATED_ADDRESS, relatedAddress).apply();
    }

    @Override
    public String getUserRelatedAddress() {
        return mPreferences.getString(PREF_USER_RELATED_ADDRESS, "");
    }

    @Override
    public void setIDCardImage(String idCardImage) {
        mPreferences.edit().putString(PREF_USER_ID_CARD_IMAGE, idCardImage).apply();
    }

    @Override
    public String getIDCardImage() {
        return mPreferences.getString(PREF_USER_ID_CARD_IMAGE, "");
    }

    @Override
    public void setTaxIDImage(String taxIDImage) {
        mPreferences.edit().putString(PREF_USER_TAX_ID_IMAGE, taxIDImage).apply();
    }

    @Override
    public String getTaxIDImage() {
        return mPreferences.getString(PREF_USER_TAX_ID_IMAGE, "");
    }

    @Override
    public void setIDCardImageID(String idCardImageID) {
        mPreferences.edit().putString(PREF_USER_ID_CARD_ID, idCardImageID).apply();
    }

    @Override
    public String getIDCardImageID() {
        return mPreferences.getString(PREF_USER_ID_CARD_ID, "");
    }

    @Override
    public void setTaxIDImageID(String taxIDImageID) {
        mPreferences.edit().putString(PREF_USER_TAX_CARD_ID, taxIDImageID).apply();
    }

    @Override
    public String getTaxIDImageID() {
        return mPreferences.getString(PREF_USER_TAX_CARD_ID, "");
    }

    @Override
    public void setBankID(int bankID) {
        mPreferences.edit().putInt(PREF_USER_BANK_ID, bankID).apply();
    }

    @Override
    public int getBankID() {
        return mPreferences.getInt(PREF_USER_BANK_ID, 0);
    }

    @Override
    public void setUserNationality(String nationality) {
        mPreferences.edit().putString(PREF_USER_NATIONALILTY, nationality).apply();
    }

    @Override
    public String getUserNationality() {
        return mPreferences.getString(PREF_USER_NATIONALILTY, "");
    }

    @Override
    public void setUserNickname(String nickname) {
        mPreferences.edit().putString(PREF_USER_NICKNAME, nickname).apply();
    }

    @Override
    public String getUserNickname() {
        return mPreferences.getString(PREF_USER_NICKNAME, "");
    }


    @Override
    public void setPublicTokenLender(String token) {
        mPreferences.edit().putString(PREF_TOKEN_LENDER, token).apply();
    }

    @Override
    public String getPublicTokenLender() {
        return mPreferences.getString(PREF_TOKEN_LENDER, "");
    }

    @Override
    public void setAdminTokenLender(String tokenLender) {
        mPreferences.edit().putString(PREF_TOKEN_ADMIN_LENDER, tokenLender).apply();
    }

    @Override
    public String getAdminTokenLender() { return mPreferences.getString(PREF_TOKEN_ADMIN_LENDER, ""); }

    @Override
    public void setAgentLogged(boolean isAgentLogged) {
        mPreferences.edit().putBoolean(PREF_IS_AGENT_LOGGED, isAgentLogged).apply();
    }

    @Override
    public boolean isAgentLogged() {
        return mPreferences.getBoolean(PREF_IS_AGENT_LOGGED, false);
    }

    @Override
    public void setAgentId(String idAgemt) {
        mPreferences.edit().putString(PREF_AGENT_ID, idAgemt).apply();
    }

    @Override
    public String getAgentId() {
        return mPreferences.getString(PREF_AGENT_ID, "");
    }

    @Override
    public void setAgentName(String nameAgent) {
        mPreferences.edit().putString(PREF_AGENT_NAME, nameAgent).apply();
    }

    @Override
    public String getAgentName() {
        return mPreferences.getString(PREF_AGENT_NAME, "");
    }

    @Override
    public void setAgentUserName(String userNameAgent) {
        mPreferences.edit().putString(PREF_AGENT_USERNAME, userNameAgent).apply();
    }

    @Override
    public String getAgentUserName() {
        return mPreferences.getString(PREF_AGENT_USERNAME, "");
    }

    @Override
    public void setAgentProfileImage(String imgUrl) {
        mPreferences.edit().putString(PREF_AGENT_PROFILE_IMG, imgUrl).apply();
    }

    @Override
    public String getAgentProfileImage() {
        return mPreferences.getString(PREF_AGENT_PROFILE_IMG, "");
    }

    @Override
    public void setAgentEmail(String emailAgent) {
        mPreferences.edit().putString(PREF_AGENT_EMAIL, emailAgent).apply();
    }

    @Override
    public String getAgentEmail() {
        return mPreferences.getString(PREF_AGENT_EMAIL, "");
    }

    @Override
    public void setAgentPhone(String phoneAgent) {
        mPreferences.edit().putString(PREF_AGENT_PHONE, phoneAgent).apply();
    }

    @Override
    public String getAgentPhone() {
        return mPreferences.getString(PREF_AGENT_PHONE, "");
    }

    @Override
    public void setAgentProvider(String providerAgent) {
        mPreferences.edit().putString(PREF_AGENT_PROVIDER, providerAgent).apply();
    }

    @Override
    public String getAgentProvider() {
        return mPreferences.getString(PREF_AGENT_PROVIDER, "");
    }

    @Override
    public void setBankAccountBorrower(String accountNumber) {
        mPreferences.edit().putString(PREF_BANK_ACCOUNT_BORROWER, accountNumber).apply();
    }

    @Override
    public String getBankAccountBorrower() {
        return mPreferences.getString(PREF_BANK_ACCOUNT_BORROWER, "");
    }

    @Override
    public void setAgentCategory(String agentCategory) {
        mPreferences.edit().putString(PREF_AGENT_CATEGORY, agentCategory).apply();
    }

    @Override
    public String getAgentCategory() {
        return mPreferences.getString(PREF_AGENT_CATEGORY, "");
    }

    @Override
    public void setAgentStatus(String agentStatus) {
        mPreferences.edit().putString(PREF_AGENT_STATUS, agentStatus).apply();
    }

    @Override
    public String getAgentStatus() {
        return mPreferences.getString(PREF_AGENT_STATUS, "");
    }

    @Override
    public void setAgentBanks(String agentBanks) {
        mPreferences.edit().putString(PREF_AGENT_BANKS, agentBanks).apply();
    }

    @Override
    public String getAgentBanks() {
        return mPreferences.getString(PREF_AGENT_BANKS, "");
    }

    @Override
    public void setAgentBanksName(String agentBanksName) {
        mPreferences.edit().putString(PREF_AGENT_BANKS_NAME, agentBanksName).apply();
    }

    @Override
    public String getAgentBanksName() {
        return mPreferences.getString(PREF_AGENT_BANKS_NAME, "");
    }

    @Override
    public void setPrimaryIncomeBorrower(int income) {
        mPreferences.edit().putInt(PREF_AGENT_PRIMARY_INCOME_BORROWER, income).apply();
    }

    @Override
    public int getPrimaryIncomeBorrower() {
        return mPreferences.getInt(PREF_AGENT_PRIMARY_INCOME_BORROWER, 0);
    }

    @Override
    public void setSecondaryIncomeBorrower(int income) {

        mPreferences.edit().putInt(PREF_AGENT_SECONDARY_INCOME_BORROWER, income).apply();

    }

    @Override
    public int getSecondaryIncomeBorrower() {
        return mPreferences.getInt(PREF_AGENT_SECONDARY_INCOME_BORROWER, 0);
    }

    @Override
    public void setOtherIncomeBorrower(String otherIncome) {

        mPreferences.edit().putString(PREF_AGENT_OTHER_INCOME_BORROWER, otherIncome).apply();
    }

    @Override
    public String getOtherIncomeBorrower() {
        return mPreferences.getString(PREF_AGENT_OTHER_INCOME_BORROWER, "");
    }
    @Override
    public void setIdCardImg(String image) {
        mPreferences.edit().putString(PREF_USER_IMG_IDCARD, image).apply();
    }

    @Override
    public String getIdCardImg() {
        return mPreferences.getString(PREF_USER_IMG_IDCARD, "");
    }

    @Override
    public void setTaxCardImg(String image) {

        mPreferences.edit().putString(PREF_USER_IMG_TAXCARD, image).apply();
    }

    @Override
    public String getTaxCardImg() {
        return mPreferences.getString(PREF_USER_IMG_TAXCARD, "");
    }

    @Override
    public void setLoanStatus(String loanStatus) {

        mPreferences.edit().putString(PREF_USER_LOAN_STATUS, loanStatus).apply();
    }

    @Override
    public String getLoanStatus() {
        return mPreferences.getString(PREF_USER_LOAN_STATUS, "");
    }

    @Override
    public void setPrefImageProfileBorrower(String imgProfile) {

        mPreferences.edit().putString(PREF_IMAGE_PROFILE_BORROWER, imgProfile).apply();
    }

    @Override
    public String getPrefImageProfileBorrower() {
        return mPreferences.getString(PREF_IMAGE_PROFILE_BORROWER, "");
    }

}