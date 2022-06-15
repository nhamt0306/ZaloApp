package hcmute.edu.vn.nhom01.zaloapp.models;

import android.widget.ImageView;

public class User {
    String phoneNumberUser;
    String password;
    String emailUser;
    String nameUser;
    String profile_pic;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String phoneNumberUser, String password, String emailUser, String nameUser, String profile_pic) {
        this.phoneNumberUser = phoneNumberUser;
        this.password = password;
        this.emailUser = emailUser;
        this.nameUser = nameUser;
        this.profile_pic = profile_pic;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public User() {
    }


    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
