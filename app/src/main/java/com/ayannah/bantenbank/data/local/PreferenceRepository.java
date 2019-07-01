package com.ayannah.bantenbank.data.local;

public interface PreferenceRepository {

    void setUserLogged(boolean isLogged);

    boolean isUserLogged();

    void clearAll();

    void setPublicToken(String token);

    String getPublicToken();

    void setUserToken(String token);

    String getUserToken();

    void setUserSetup(boolean userSetup);

    boolean isUserSetup();

    void setIdUser(String idUser);

    String getIdUser();

    void setUserPhone(String phonenumber);

    String getUserPhone();

    void setUserEmail(String email);

    String getUserEmail();

    void setUserName(String name);

    String getUserName();

    void setUserBank(String bankName);

    String getUserBank();

    void setUserProfileImage(String profileImage);

    String getUserProfile();

    void setUserNIP(String userNIP);

    String getUserNIP();

    void setUserPrimaryIncome(String userIncome);

    String getUserPrimaryIncome();

    void setUserOtherIncome(String otherIncome);

    String getUserOtherIncome();

    void setuserOtherSourceIncome(String otherSourceIncome);

    String getUserOtherSourceIncome();

}
