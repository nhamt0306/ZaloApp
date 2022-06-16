package hcmute.edu.vn.nhom01.zaloapp.messages;

public class MessagesList {
    // Các biến lưu tên người dùng, số điện thoại, tin nhắn cuối cùng, ảnh đại diện, id của cuộc hội thoại
    private String name, mobile, lastMessage, profilePic, chatKey;

    // Số lượng tin nhắn chưa đọc
    private int UnseenMessages;

    // Constructor
    public MessagesList(String name, String mobile, String lastMessage, String profilePic,int unseenMessages, String chatKey) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.profilePic = profilePic;
        this.UnseenMessages = unseenMessages;
        this.chatKey = chatKey;
    }

    // Getter của tên người dùng
    public String getName() {
        return name;
    }

    // Getter của số điện thoại người dùng
    public String getMobile() {
        return mobile;
    }

    // Getter của tin nhắn cuối cùng
    public String getLastMessage() {
        return lastMessage;
    }

    // Getter của ảnh đại diện
    public String getProfilePic() {
        return profilePic;
    }

    // Getter của số lượng tin nhắn chưa xem
    public int getUnseenMessages() {
        return UnseenMessages;
    }

    // Getter của id của cuộc hội thoại
    public String getChatKey() {
        return chatKey;
    }
}
