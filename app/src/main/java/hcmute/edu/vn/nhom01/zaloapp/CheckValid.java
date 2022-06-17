package hcmute.edu.vn.nhom01.zaloapp;

// Class dùng để validate dữ liệu
public class CheckValid {

    // Validate email
    // VD: phianh@gmail.com -> hợp lệ
    static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    // Kiểm tra chuỗi chỉ có số
    static boolean isPhoneValid(String email) {
        String regexStr = "^[0-9]*$";
        return email.matches(regexStr);
    }
}
