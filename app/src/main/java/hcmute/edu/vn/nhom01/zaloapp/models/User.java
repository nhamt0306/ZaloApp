package hcmute.edu.vn.nhom01.zaloapp.models;

import android.widget.ImageView;

// Class lưu thông tin của từng User
public class User {
    // Số điện thoại người dùng
    String phoneNumberUser;
    // Mật khẩu
    String password;
    // Email của người dùng
    String emailUser;
    // Tên người dùng
    String nameUser;
    // Url của ảnh đại diện người dùng
    String profile_pic;


    // Constructor User với đủ 5 tham số đầu vào
    public User(String phoneNumberUser, String password, String emailUser, String nameUser, String profile_pic) {
        this.phoneNumberUser = phoneNumberUser;
        this.password = password;
        this.emailUser = emailUser;
        this.nameUser = nameUser;
        this.profile_pic = profile_pic;
    }

    public User(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    // Contructor rỗng
    public User() {
    }

    // Getter, Setter của profilepic
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    // Getter, Setter của số điện thoại người dùng
    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    // Getter, Setter của email người dùng
    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    // Getter, Setter của tên người dùng
    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    // Getter, Setter của mật khẩu người dùng

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
