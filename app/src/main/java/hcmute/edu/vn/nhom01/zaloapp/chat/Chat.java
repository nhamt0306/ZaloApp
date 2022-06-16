package hcmute.edu.vn.nhom01.zaloapp.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.MemoryData;
import hcmute.edu.vn.nhom01.zaloapp.R;

public class Chat extends AppCompatActivity {
    // refer database từ Firebase bằng cách truyền vào url của realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    // List gồm các chatList lưu lại các tin nhắn hội thoại
    private final List<ChatList> chatLists = new ArrayList<>();

    // chatKey được sử dụng để phân biệt các đoạn chat khác nhau
    private String chatKey;

    // getUserMobile lấy dữ liệu số điện thoại của người gửi tin nhắn
    private String getUserMobile = "";

    // RecyclerView của chat
    private RecyclerView chattingRecyclerView;
    private ChatAdapter chatAdapter;

    // loadingFirstTime kiểm tra xem có phải tin nhắn đầu tiên của cuộc hội thoại hay không
    private boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Ánh xạ
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameTV = findViewById(R.id.name);
        final EditText messageEditTxt = findViewById(R.id.messageEditTxt);
        final ImageView sendBtn = findViewById(R.id.sendBtn);
        final CircleImageView profilePic = findViewById(R.id.profilePic);

        chattingRecyclerView = findViewById(R.id.chattingRecyclerView);

        // Lấy dữ liệu từ message adapter class
        final String getName = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");
        final String getMobile = getIntent().getStringExtra("mobile");

        // Lấy số điện thoại người dùng từ MemoryData
        getUserMobile = MemoryData.getData(Chat.this);

        nameTV.setText(getName);

        // Load hình đại diện lên nếu có
        if (!getProfilePic.isEmpty()) {
            Picasso.get().load(getProfilePic).into(profilePic);
        }

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        // Tạo adapter Chat dùng để show các đoạn chat
        chatAdapter = new ChatAdapter(chatLists, Chat.this);
        chattingRecyclerView.setAdapter(chatAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Kiểm tra nếu chatKey có rỗng không
                if (chatKey.isEmpty()) {
                    // Tạo chat key, cho giá trị default = 1
                    chatKey = "1";

                    // Kiểm tra xem có Child "chat" hay chưa, thực hiện tạo một chat key mới thứ tự tăng dần (1,2,3..)
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }

                if (snapshot.hasChild("chat")) {
                    if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                        chatLists.clear();
                        for (DataSnapshot messageSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {
                            if (messageSnapshot.hasChild("msg") && messageSnapshot.hasChild("mobile")) {
                                final String messageTimestamps = messageSnapshot.getKey();
                                final String getMobile = messageSnapshot.child("mobile").getValue(String.class);
                                final String getMsg = messageSnapshot.child("msg").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                ChatList chatList = new ChatList(getMobile, getName, getMsg, simpleDateFormat.format(date), simpleTimeFormat.format(date));
                                chatLists.add(chatList);
                                if (loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(MemoryData.getLastMsgTs(Chat.this, chatKey))) {
                                    loadingFirstTime = false;
                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat.this);

                                    chatAdapter.updateChatList(chatLists);
                                    chattingRecyclerView.scrollToPosition(chatLists.size() - 1);
                                }
                            }

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Xứ lý sự kiện nhấn vào nút gửi
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy tin nhắn từ edittext
                final String getTxtMessages = messageEditTxt.getText().toString();

                // lấy thời điểm nhắn tin
                final String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                MemoryData.saveLastMsgTS(currentTimeStamp, chatKey, Chat.this);
                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserMobile);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getMobile);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimeStamp).child("msg").setValue(getTxtMessages);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimeStamp).child("mobile").setValue(getUserMobile);

                // xóa edit text sau khi nhấn gửi
                messageEditTxt.setText("");

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}