package hcmute.edu.vn.nhom01.zaloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Friends extends AppCompatActivity {

    // tạo 2 databaseref để tham chiếu đến 2 đối tượng trong firebase : friends và makefriend
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef_friends;
    private String getUserMobile = ""; // chứa số điện thoại của người dùng

    private String sender = ""; // chứa số điện thoại người gửi request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        getUserMobile = MemoryData.getData(Friends.this); // lay so dien thoai cua user de them vào firebase
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("makefriend"); // tham chiếu đến makefriend trên firebase
        sender = getIntent().getStringExtra("sender"); // lấy số điện thoại người gửi request
        mDatabaseRef_friends = FirebaseDatabase.getInstance().getReference("friends"); // tham chiếu đến friends trên firebase

        // đầu tiên sẽ xóa dữ liệu bên đối tượng makefriend
        mDatabaseRef.child(getUserMobile.toString()).child(sender.toString()).getRef().setValue(null);
        // sau đó thêm vào bên đối tượng friends lần lượt là số điện thoại người nhận - người gửi và ngược lại để
        // khi truy xuất dữ liệu show các đối tượng đã kết bạn thì cả 2 đề có mặt trong list của người kia
        mDatabaseRef_friends.child(getUserMobile.toString()).child(sender.toString()).setValue(sender.toString());
        mDatabaseRef_friends.child(sender.toString()).child(getUserMobile.toString()).setValue(getUserMobile.toString());

    }
}