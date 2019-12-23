package com.ayannah.asira.data.local;

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

    void setUserNIP(String userNIP);

    String getUserNIP();

    void setUserPrimaryIncome(String userIncome);

    String getUserPrimaryIncome();

    void setUserOtherIncome(String otherIncome);

    String getUserOtherIncome();

    void setuserOtherSourceIncome(String otherSourceIncome);

    String getUserOtherSourceIncome();

    void setUserGender(String gender);

    String getUserGender();

    void setIdCardUser(String idCarf);

    String getIdCard();

    void setTaxCard(String taxCard);

    String getTaxCard();

    void setUserBirtday(String birthdate);

    String getUserBirthdate();

    void setUserBirthPlace(String place);

    String getUserBirthplace();

    void setUserLastEducation(String education);

    String getUserLastEducation();

    void setUserMotherName(String motherName);

    String getUserMotherName();

    void setUserMarriageStatus(String marriageStatus);

    String getUserMarriageStatus();

    void setUserSpouseName(String spouseName);

    String getUserSpouseName();

    void setSpouseBirthDate(String spouseBirthDate);

    String getSpouserBirthdate();

    void setSpouseEducation(String spouseEducation);

    String getSpouseEducation();

    void setUserAddress(String address);

    String getUserAddress();

    void setUserProvince(String province);

    String getuserProvince();

    void setUserCity(String city);

    String getUserCity();

    void setUserNeighbourAssociation(String neighbourAssociation);

    String getUserNeighbourAssociation();

    void setUserHamlets(String hamlets);

    String getUserHamlets();

    void setUserHomePhoneNumber(String homePhoneNumber);

    String getUserHomePhoneNumber();

    void setSubDistrict(String subDistrict);

    String getSubDistrict();

    void setUrbanVillage(String urbanVillage);

    String getUrbanVillage();

    void setHomeOwnerShip(String homeOwnerShip);

    String getHomeOwnerShip();

    void setLivedFor(String livedFor);

    String getLivedFor();

    void setOccupation(String occupation);

    String getOccupation();

    void setEmployeeId(String employeeId);

    String getEmployeeId();

    void setEmployerName(String employerName);

    String getEmployerName();

    void setEmployerAddress(String employerAddress);

    String getEmployerAddress();

    void setDepartment(String department);

    String getDepartment();

    void setBeenWorkingFor(String beenWorkingFor);

    String getBeenWorkingFor();

    void setDirectSuperiorName(String superiorName);

    String getDirectSuperiorName();

    void setEmployerNumber(String employerNumber);

    String getEmployerNumber();

    void setFieldToWork(String fieldToWork);

    String getFieldToWork();

    void setUserRelatedPhoneNumber(String relatedPhoneNumber);

    String getUserRelatePhoneNumber();

    void setUserRelatedPersonName(String relatedPersonName);

    String getUserRelatedPersonName();

    void setUserRelatedRelation(String userRelatedRelation);

    String getUserRelatedRelation();

    void setUserRelatedHomeNumber(String relatedHomeNumber);

    String getUserRelatedHomeNumber();

    void setUserRelatedBankAccountNumber(String relatedBankAccountNumber);

    String getUserRelatedBankAccountNumber();

    void setDependants(int count);

    int getDependants();

    void setUserRelatedAddress(String relatedAddress);

    String getUserRelatedAddress();

    void setIDCardImage(String idCardImage);

    String getIDCardImage();

    void setTaxIDImage(String taxIDImage);

    String getTaxIDImage();

    void setIDCardImageID(String idCardImageID);

    String getIDCardImageID();

    void setTaxIDImageID(String taxIDImageID);

    String getTaxIDImageID();

    void setBankID(int bankID);

    int getBankID();

    void setUserNationality(String nationality);

    String getUserNationality();

    void setUserNickname(String nickname);

    String getUserNickname();

    void setPublicTokenLender(String s);

    String getPublicTokenLender();

    void setAdminTokenLender(String s);

    String getAdminTokenLender();

    void setAgentLogged(boolean isAgentLogged);

    boolean isAgentLogged();

    void setAgentId(String idAgemt);

    String getAgentId();

    void setAgentName(String nameAgent);

    String getAgentName();

    void setAgentUserName(String userNameAgent);

    String getAgentUserName();

    void setAgentEmail(String emailAgent);

    String getAgentEmail();

    void setAgentPhone(String phoneAgent);

    String getAgentPhone();

    void setAgentProvider(String providerAgent);

    String getAgentProvider();

    void setBankAccountBorrower(String accountNumber);

    String getBankAccountBorrower();

    void setAgentCategory(String agentCategory);

    String getAgentCategory();

    void setAgentStatus(String agentStatus);

    String getAgentStatus();

    void setAgentBanks(String agentBanks);

    String getAgentBanks();

    void setAgentBanksName(String agentBanksName);

    String getAgentBanksName();
}
