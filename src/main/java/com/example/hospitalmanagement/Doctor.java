package com.example.hospitalmanagement;

public class Doctor {
  private   String FullName;
  private   String UserName;
  private   String Password;
  private   String Email;
  private   String PhoneNumber;
  private   String DateOfBirth;
  private   String Specialization;
  private   String EducationalQualification;
 private   String Address;
 private  int Status;

    public Doctor(String userName, String fullName, String password, String email, String phoneNumber, String dateOfBirth, String specialization, String educationalQualification,String Address,int Status) {
        this.UserName = userName;
        this.FullName = fullName;
        this.Password = password;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.DateOfBirth = dateOfBirth;
        this.Specialization = specialization;
        this.EducationalQualification = educationalQualification;
        this.Address = Address;
        this.Status=Status;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public void setEducationalQualification(String educationalQualification) {
        EducationalQualification = educationalQualification;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFullName() {
        return FullName;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public String getEducationalQualification() {
        return EducationalQualification;
    }

    public String getAddress() {
        return Address;
    }
    public int getStatus(){
        return Status;
    }


}
