package com.example.isustain_app;

public class Users {
    public String fullName , emailAdd , phoneNum ;

    public Users(){

    }
    public Users (String FullName, String EmailAdd, String PhoneNum){
        this.emailAdd = EmailAdd;
        this.fullName = FullName;
        this.phoneNum = PhoneNum;
        //this.points=points;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}


