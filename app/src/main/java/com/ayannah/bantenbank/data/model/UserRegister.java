package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

public class UserRegister{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("related_phonenumber")
	private String relatedPhonenumber;

	@SerializedName("taxid_number")
	private int taxidNumber;

	@SerializedName("idcard_number")
	private long idcardNumber;

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
	private int hamlets;

	@SerializedName("related_homenumber")
	private String relatedHomenumber;

	@SerializedName("neighbour_association")
	private int neighbourAssociation;

	@SerializedName("spouse_name")
	private String spouseName;

	@SerializedName("bank")
	private int bank;

	@SerializedName("password")
	private String password;

	@SerializedName("field_of_work")
	private String fieldOfWork;

	@SerializedName("province")
	private String province;

	@SerializedName("spouse_birthday")
	private String spouseBirthday;

	@SerializedName("department")
	private String department;

	@SerializedName("email")
	private String email;

	@SerializedName("lived_for")
	private int livedFor;

	@SerializedName("address")
	private String address;

	@SerializedName("spouse_lasteducation")
	private String spouseLasteducation;

	@SerializedName("other_income")
	private int otherIncome;

	@SerializedName("home_phonenumber")
	private int homePhonenumber;

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

	@SerializedName("dependants")
	private int dependants;

	@SerializedName("subdistrict")
	private String subdistrict;

	@SerializedName("employee_id")
	private int employeeId;

	@SerializedName("other_incomesource")
	private String otherIncomesource;

	@SerializedName("urban_village")
	private String urbanVillage;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("employer_number")
	private String employerNumber;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setRelatedPhonenumber(String relatedPhonenumber){
		this.relatedPhonenumber = relatedPhonenumber;
	}

	public String getRelatedPhonenumber(){
		return relatedPhonenumber;
	}

	public void setTaxidNumber(int taxidNumber){
		this.taxidNumber = taxidNumber;
	}

	public int getTaxidNumber(){
		return taxidNumber;
	}

	public void setIdcardNumber(long idcardNumber){
		this.idcardNumber = idcardNumber;
	}

	public long getIdcardNumber(){
		return idcardNumber;
	}

	public void setOccupation(String occupation){
		this.occupation = occupation;
	}

	public String getOccupation(){
		return occupation;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setMotherName(String motherName){
		this.motherName = motherName;
	}

	public String getMotherName(){
		return motherName;
	}

	public void setDirectSuperiorname(String directSuperiorname){
		this.directSuperiorname = directSuperiorname;
	}

	public String getDirectSuperiorname(){
		return directSuperiorname;
	}

	public void setBankAccountnumber(String bankAccountnumber){
		this.bankAccountnumber = bankAccountnumber;
	}

	public String getBankAccountnumber(){
		return bankAccountnumber;
	}

	public void setEmployerName(String employerName){
		this.employerName = employerName;
	}

	public String getEmployerName(){
		return employerName;
	}

	public void setHamlets(int hamlets){
		this.hamlets = hamlets;
	}

	public int getHamlets(){
		return hamlets;
	}

	public void setRelatedHomenumber(String relatedHomenumber){
		this.relatedHomenumber = relatedHomenumber;
	}

	public String getRelatedHomenumber(){
		return relatedHomenumber;
	}

	public void setNeighbourAssociation(int neighbourAssociation){
		this.neighbourAssociation = neighbourAssociation;
	}

	public int getNeighbourAssociation(){
		return neighbourAssociation;
	}

	public void setSpouseName(String spouseName){
		this.spouseName = spouseName;
	}

	public String getSpouseName(){
		return spouseName;
	}

	public void setBank(int bank){
		this.bank = bank;
	}

	public int getBank(){
		return bank;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setFieldOfWork(String fieldOfWork){
		this.fieldOfWork = fieldOfWork;
	}

	public String getFieldOfWork(){
		return fieldOfWork;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setSpouseBirthday(String spouseBirthday){
		this.spouseBirthday = spouseBirthday;
	}

	public String getSpouseBirthday(){
		return spouseBirthday;
	}

	public void setDepartment(String department){
		this.department = department;
	}

	public String getDepartment(){
		return department;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLivedFor(int livedFor){
		this.livedFor = livedFor;
	}

	public int getLivedFor(){
		return livedFor;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setSpouseLasteducation(String spouseLasteducation){
		this.spouseLasteducation = spouseLasteducation;
	}

	public String getSpouseLasteducation(){
		return spouseLasteducation;
	}

	public void setOtherIncome(int otherIncome){
		this.otherIncome = otherIncome;
	}

	public int getOtherIncome(){
		return otherIncome;
	}

	public void setHomePhonenumber(int homePhonenumber){
		this.homePhonenumber = homePhonenumber;
	}

	public int getHomePhonenumber(){
		return homePhonenumber;
	}

	public void setMonthlyIncome(int monthlyIncome){
		this.monthlyIncome = monthlyIncome;
	}

	public int getMonthlyIncome(){
		return monthlyIncome;
	}

	public void setHomeOwnership(String homeOwnership){
		this.homeOwnership = homeOwnership;
	}

	public String getHomeOwnership(){
		return homeOwnership;
	}

	public void setLastEducation(String lastEducation){
		this.lastEducation = lastEducation;
	}

	public String getLastEducation(){
		return lastEducation;
	}

	public void setMarriageStatus(String marriageStatus){
		this.marriageStatus = marriageStatus;
	}

	public String getMarriageStatus(){
		return marriageStatus;
	}

	public void setRelatedPersonname(String relatedPersonname){
		this.relatedPersonname = relatedPersonname;
	}

	public String getRelatedPersonname(){
		return relatedPersonname;
	}

	public void setRelatedRelation(String relatedRelation){
		this.relatedRelation = relatedRelation;
	}

	public String getRelatedRelation(){
		return relatedRelation;
	}

	public void setEmployerAddress(String employerAddress){
		this.employerAddress = employerAddress;
	}

	public String getEmployerAddress(){
		return employerAddress;
	}

	public void setBirthplace(String birthplace){
		this.birthplace = birthplace;
	}

	public String getBirthplace(){
		return birthplace;
	}

	public void setBeenWorkingfor(int beenWorkingfor){
		this.beenWorkingfor = beenWorkingfor;
	}

	public int getBeenWorkingfor(){
		return beenWorkingfor;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setDependants(int dependants){
		this.dependants = dependants;
	}

	public int getDependants(){
		return dependants;
	}

	public void setSubdistrict(String subdistrict){
		this.subdistrict = subdistrict;
	}

	public String getSubdistrict(){
		return subdistrict;
	}

	public void setEmployeeId(int employeeId){
		this.employeeId = employeeId;
	}

	public int getEmployeeId(){
		return employeeId;
	}

	public void setOtherIncomesource(String otherIncomesource){
		this.otherIncomesource = otherIncomesource;
	}

	public String getOtherIncomesource(){
		return otherIncomesource;
	}

	public void setUrbanVillage(String urbanVillage){
		this.urbanVillage = urbanVillage;
	}

	public String getUrbanVillage(){
		return urbanVillage;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setEmployerNumber(String employerNumber){
		this.employerNumber = employerNumber;
	}

	public String getEmployerNumber(){
		return employerNumber;
	}

	@Override
 	public String toString(){
		return 
			"UserRegister{" + 
			"birthday = '" + birthday + '\'' + 
			",related_phonenumber = '" + relatedPhonenumber + '\'' + 
			",taxid_number = '" + taxidNumber + '\'' + 
			",idcard_number = '" + idcardNumber + '\'' + 
			",occupation = '" + occupation + '\'' + 
			",gender = '" + gender + '\'' + 
			",city = '" + city + '\'' + 
			",mother_name = '" + motherName + '\'' + 
			",direct_superiorname = '" + directSuperiorname + '\'' + 
			",bank_accountnumber = '" + bankAccountnumber + '\'' + 
			",employer_name = '" + employerName + '\'' + 
			",hamlets = '" + hamlets + '\'' + 
			",related_homenumber = '" + relatedHomenumber + '\'' + 
			",neighbour_association = '" + neighbourAssociation + '\'' + 
			",spouse_name = '" + spouseName + '\'' + 
			",bank = '" + bank + '\'' + 
			",password = '" + password + '\'' + 
			",field_of_work = '" + fieldOfWork + '\'' + 
			",province = '" + province + '\'' + 
			",spouse_birthday = '" + spouseBirthday + '\'' + 
			",department = '" + department + '\'' + 
			",email = '" + email + '\'' + 
			",lived_for = '" + livedFor + '\'' + 
			",address = '" + address + '\'' + 
			",spouse_lasteducation = '" + spouseLasteducation + '\'' + 
			",other_income = '" + otherIncome + '\'' + 
			",home_phonenumber = '" + homePhonenumber + '\'' + 
			",monthly_income = '" + monthlyIncome + '\'' + 
			",home_ownership = '" + homeOwnership + '\'' + 
			",last_education = '" + lastEducation + '\'' + 
			",marriage_status = '" + marriageStatus + '\'' + 
			",related_personname = '" + relatedPersonname + '\'' + 
			",related_relation = '" + relatedRelation + '\'' + 
			",employer_address = '" + employerAddress + '\'' + 
			",birthplace = '" + birthplace + '\'' + 
			",been_workingfor = '" + beenWorkingfor + '\'' + 
			",phone = '" + phone + '\'' + 
			",dependants = '" + dependants + '\'' + 
			",subdistrict = '" + subdistrict + '\'' + 
			",employee_id = '" + employeeId + '\'' + 
			",other_incomesource = '" + otherIncomesource + '\'' + 
			",urban_village = '" + urbanVillage + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",employer_number = '" + employerNumber + '\'' + 
			"}";
		}
}