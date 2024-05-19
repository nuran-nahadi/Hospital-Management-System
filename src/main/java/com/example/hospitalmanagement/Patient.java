package com.example.hospitalmanagement;

public class Patient {
    // Private fields for encapsulation
    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String disease;
    private String doctorName;
    private String address;

    // Constructor
    public Patient(String fullName, String userName, String email, String phoneNumber, String dateOfBirth, String disease, String doctorName, String address) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.disease = disease;
        this.doctorName = doctorName;
        this.address = address;
    }

    public Patient(String fullName,String userName,String email,String phoneNumber,String dateOfBirth,String disease){
        this.fullName=fullName;
        this.userName=userName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.dateOfBirth=dateOfBirth;
        this.disease=disease;
    }

    // Getter and setter methods for each field
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
