package com.ayannah.asira.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserBorrower implements Parcelable, Serializable {

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

	@SerializedName("agnet_id")
	private AgentId agentId;

	@SerializedName("status")
	private String status;

	@SerializedName("nth_loans")
	private int nthLoans;

	protected UserBorrower(Parcel in) {
		birthday = in.readString();
		relatedPhonenumber = in.readString();
		updatedTime = in.readString();
		taxidNumber = in.readString();
		idcardNumber = in.readString();
		occupation = in.readString();
		gender = in.readString();
		city = in.readString();
		motherName = in.readString();
		directSuperiorname = in.readString();
		bankAccountnumber = in.readString();
		employerName = in.readString();
		hamlets = in.readString();
		relatedHomenumber = in.readString();
		suspendedTime = in.readString();
		neighbourAssociation = in.readString();
		spouseName = in.readString();
		bank = in.readParcelable(Bank.class.getClassLoader());
		otpVerified = in.readByte() != 0;
		password = in.readString();
		fieldOfWork = in.readString();
		province = in.readString();
		spouseBirthday = in.readString();
		id = in.readInt();
		department = in.readString();
		email = in.readString();
		createdTime = in.readString();
		livedFor = in.readInt();
		address = in.readString();
		spouseLasteducation = in.readString();
		otherIncome = in.readInt();
		homePhonenumber = in.readString();
		monthlyIncome = in.readInt();
		homeOwnership = in.readString();
		lastEducation = in.readString();
		marriageStatus = in.readString();
		relatedPersonname = in.readString();
		relatedRelation = in.readString();
		employerAddress = in.readString();
		birthplace = in.readString();
		beenWorkingfor = in.readInt();
		phone = in.readString();
		subdistrict = in.readString();
		employeeId = in.readString();
		otherIncomesource = in.readString();
		urbanVillage = in.readString();
		fullname = in.readString();
		nickname = in.readString();
		nationality = in.readString();
		employerNumber = in.readString();
		relatedAddress = in.readString();
		dependants = in.readInt();
	}

	public UserBorrower() {}

	public static final Creator<UserBorrower> CREATOR = new Creator<UserBorrower>() {
		@Override
		public UserBorrower createFromParcel(Parcel in) {
			return new UserBorrower(in);
		}

		@Override
		public UserBorrower[] newArray(int size) {
			return new UserBorrower[size];
		}
	};

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

	public AgentId getAgentId() {
		return agentId;
	}

	public String getStatus() {
		return status;
	}

	public int getNthLoans() {
		return nthLoans;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(birthday);
		dest.writeString(relatedPhonenumber);
		dest.writeString(updatedTime);
		dest.writeString(taxidNumber);
		dest.writeString(idcardNumber);
		dest.writeString(occupation);
		dest.writeString(gender);
		dest.writeString(city);
		dest.writeString(motherName);
		dest.writeString(directSuperiorname);
		dest.writeString(bankAccountnumber);
		dest.writeString(employerName);
		dest.writeString(hamlets);
		dest.writeString(relatedHomenumber);
		dest.writeString(suspendedTime);
		dest.writeString(neighbourAssociation);
		dest.writeString(spouseName);
		dest.writeParcelable(bank, flags);
		dest.writeByte((byte) (otpVerified ? 1 : 0));
		dest.writeString(password);
		dest.writeString(fieldOfWork);
		dest.writeString(province);
		dest.writeString(spouseBirthday);
		dest.writeInt(id);
		dest.writeString(department);
		dest.writeString(email);
		dest.writeString(createdTime);
		dest.writeInt(livedFor);
		dest.writeString(address);
		dest.writeString(spouseLasteducation);
		dest.writeInt(otherIncome);
		dest.writeString(homePhonenumber);
		dest.writeInt(monthlyIncome);
		dest.writeString(homeOwnership);
		dest.writeString(lastEducation);
		dest.writeString(marriageStatus);
		dest.writeString(relatedPersonname);
		dest.writeString(relatedRelation);
		dest.writeString(employerAddress);
		dest.writeString(birthplace);
		dest.writeInt(beenWorkingfor);
		dest.writeString(phone);
		dest.writeString(subdistrict);
		dest.writeString(employeeId);
		dest.writeString(otherIncomesource);
		dest.writeString(urbanVillage);
		dest.writeString(fullname);
		dest.writeString(nickname);
		dest.writeString(nationality);
		dest.writeString(employerNumber);
		dest.writeString(relatedAddress);
		dest.writeInt(dependants);
	}
}