package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("related_phonenumber")
	private String relatedPhonenumber;

	@SerializedName("updated_time")
	private String updatedTime;

	@SerializedName("taxid_number")
	private String taxidNumber;

	@SerializedName("idcard_number")
	private String idcardNumber;

	@SerializedName("occupation")
	private String occupation;

	@SerializedName("gender")
	private String gender;

	@SerializedName("city")
	private String city;

	@SerializedName("mother_name")
	private String motherName;

	@SerializedName("direct_superiorname")
	private String directSuperiorname;

	@SerializedName("bank_accountnumber")
	private String bankAccountnumber;

	@SerializedName("employer_name")
	private String employerName;

	@SerializedName("hamlets")
	private String hamlets;

	@SerializedName("related_homenumber")
	private String relatedHomenumber;

	@SerializedName("suspended_time")
	private String suspendedTime;

	@SerializedName("neighbour_association")
	private String neighbourAssociation;

	@SerializedName("spouse_name")
	private String spouseName;

	@SerializedName("bank")
	private Bank bank;

	@SerializedName("otp_verified")
	private boolean otpVerified;

	@SerializedName("password")
	private String password;

	@SerializedName("field_of_work")
	private String fieldOfWork;

	@SerializedName("province")
	private String province;

	@SerializedName("spouse_birthday")
	private String spouseBirthday;

	@SerializedName("id")
	private int id;

	@SerializedName("department")
	private String department;

	@SerializedName("email")
	private String email;

	@SerializedName("created_time")
	private String createdTime;

	@SerializedName("lived_for")
	private int livedFor;

	@SerializedName("address")
	private String address;

	@SerializedName("spouse_lasteducation")
	private String spouseLasteducation;

	@SerializedName("other_income")
	private int otherIncome;

	@SerializedName("home_phonenumber")
	private String homePhonenumber;

	@SerializedName("monthly_income")
	private int monthlyIncome;

	@SerializedName("home_ownership")
	private String homeOwnership;

	@SerializedName("last_education")
	private String lastEducation;

	@SerializedName("marriage_status")
	private String marriageStatus;

	@SerializedName("related_personname")
	private String relatedPersonname;

	@SerializedName("related_relation")
	private String relatedRelation;

	@SerializedName("employer_address")
	private String employerAddress;

	@SerializedName("birthplace")
	private String birthplace;

	@SerializedName("been_workingfor")
	private int beenWorkingfor;

	@SerializedName("phone")
	private String phone;

	@SerializedName("subdistrict")
	private String subdistrict;

	@SerializedName("employee_id")
	private String employeeId;

	@SerializedName("other_incomesource")
	private String otherIncomesource;

	@SerializedName("urban_village")
	private String urbanVillage;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("nickname")
	private String nickname;

	@SerializedName("nationality")
	private String nationality;

	@SerializedName("employer_number")
	private String employerNumber;

	@SerializedName("related_address")
	private String relatedAddress;

	@SerializedName("dependants")
	private int dependants;

	@SerializedName("idcard_image")
	private IDCardImage idCardImage;

	@SerializedName("taxid_image")
	private TaxIDImage taxIDImage;

	public String getBirthday(){
		return birthday;
	}

	public String getRelatedPhonenumber(){
		return relatedPhonenumber;
	}

	public String getUpdatedTime(){
		return updatedTime;
	}

	public String getTaxidNumber(){
		return taxidNumber;
	}

	public String getIdcardNumber(){
		return idcardNumber;
	}

	public String getOccupation(){
		return occupation;
	}

	public String getGender(){
		return gender;
	}

	public String getCity(){
		return city;
	}

	public String getMotherName(){
		return motherName;
	}

	public String getDirectSuperiorname(){
		return directSuperiorname;
	}

	public String getBankAccountnumber(){
		return bankAccountnumber;
	}

	public String getEmployerName(){
		return employerName;
	}

	public String getHamlets(){
		return hamlets;
	}

	public String getRelatedHomenumber(){
		return relatedHomenumber;
	}

	public String getSuspendedTime(){
		return suspendedTime;
	}

	public String getNeighbourAssociation(){
		return neighbourAssociation;
	}

	public String getSpouseName(){
		return spouseName;
	}

	public Bank getBank(){
		return bank;
	}

	public boolean isOtpVerified(){
		return otpVerified;
	}

	public String getPassword(){
		return password;
	}

	public String getFieldOfWork(){
		return fieldOfWork;
	}

	public String getProvince(){
		return province;
	}

	public String getSpouseBirthday(){
		return spouseBirthday;
	}

	public int getId(){
		return id;
	}

	public String getDepartment(){
		return department;
	}

	public String getEmail(){
		return email;
	}

	public String getCreatedTime(){
		return createdTime;
	}

	public int getLivedFor(){
		return livedFor;
	}

	public String getAddress(){
		return address;
	}

	public String getSpouseLasteducation(){
		return spouseLasteducation;
	}

	public int getOtherIncome(){
		return otherIncome;
	}

	public String getHomePhonenumber(){
		return homePhonenumber;
	}

	public int getMonthlyIncome(){
		return monthlyIncome;
	}

	public String getHomeOwnership(){
		return homeOwnership;
	}

	public String getLastEducation(){
		return lastEducation;
	}

	public String getMarriageStatus(){
		return marriageStatus;
	}

	public String getRelatedPersonname(){
		return relatedPersonname;
	}

	public String getRelatedRelation(){
		return relatedRelation;
	}

	public String getEmployerAddress(){
		return employerAddress;
	}

	public String getBirthplace(){
		return birthplace;
	}

	public int getBeenWorkingfor(){
		return beenWorkingfor;
	}

	public String getPhone(){
		return phone;
	}

	public String getSubdistrict(){
		return subdistrict;
	}

	public String getEmployeeId(){
		return employeeId;
	}

	public String getOtherIncomesource(){
		return otherIncomesource;
	}

	public String getUrbanVillage(){
		return urbanVillage;
	}

	public String getFullname(){
		return fullname;
	}

	public String getNickname() {
		return nickname;
	}

	public String getNationality() {
		return nationality;
	}

	public String getEmployerNumber(){
		return employerNumber;
	}

	public String getRelatedAddress() {
		return relatedAddress;
	}

	public int getDependants() {
		return dependants;
	}

	public IDCardImage getIdCardImage() {
		return idCardImage;
	}

	public TaxIDImage getTaxIDImage() {
		return taxIDImage;
	}
}