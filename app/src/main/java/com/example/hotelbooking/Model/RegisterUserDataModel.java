package com.example.hotelbooking.Model;

public class RegisterUserDataModel {
    String Username;
    String Emailid;
    String MobileNo;
    String Passwrd;
    String login_type;

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPasswrd() {
        return Passwrd;
    }

    public void setPasswrd(String passwrd) {
        Passwrd = passwrd;
    }
}
