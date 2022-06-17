package hcmute.edu.vn.nhom01.zaloapp.chat;

public class ChatList {

    // số điện thoại, tên người gửi, nội dung tin nhắn, ngày gửi, thời gian
    private String mobile, name, message, date, time;

    // Constructor ChatList
    public ChatList(String mobile, String name, String message, String date, String time) {
        this.mobile = mobile;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    // Các hàm get cho Mobile, Name, Message, Date, Time
    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
