package hcmute.edu.vn.nhom01.zaloapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.messages.MessagesAdapter;
import hcmute.edu.vn.nhom01.zaloapp.messages.MessagesList;

public class MessageFragment extends Fragment {

    // List chứa những đối tượng MessagesList, dùng lưu lại thông tin của các cuộc hội thoại vào một list
    private final List<MessagesList> messagesLists = new ArrayList<>();

    // Số điện thoại, email, tên của người dùng
    private String mobile;
    private String email;
    private String name;

    // Số lượng tin nhắn chưa xem, mặc định = 0
    private int unseenMessage = 0;

    // Dòng tin nhắn cuối cùng
    private String lastMessage = "";

    // Chatkey của cuộc hội thoại
    private String chatKey = "";

    // dataSet kiểm tra xem dữ liệu đã được đổ vào messagesLists hay chưa
    private boolean dataSet = false;

    // RecyclerView của khung chat
    private RecyclerView messagesRecyclerView;

    // Adapter của MessageList
    private MessagesAdapter messagesAdapter;

    // databaseReference giúp thao tác dữ liệu với Firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    // Constructor rỗng
    public MessageFragment() {
    }

    private ImageView makefriend;
    private ImageView AcceptFriend;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate fragment_messages cho fragment này
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        makefriend = view.findViewById(R.id.imageview_makefriend);
        AcceptFriend = view.findViewById(R.id.imageview_acceptfriend);

        //lấy dữ liệu số điện thoại, email, tên từ intent
        mobile = getActivity().getIntent().getStringExtra("mobile");
        email = getActivity().getIntent().getStringExtra("email");
        name = getActivity().getIntent().getStringExtra("name");

        // CircleImageView thể hiện avatar của người dùng ánh xạ từ userProfilePic
        final CircleImageView userProfilePic = view.findViewById(R.id.userProfilePic);

        // Ánh xạ messagesRecyclerView
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);

        // Đảm bảo các phần tử có width và height không đổi
        messagesRecyclerView.setHasFixedSize(true);

        // setLayoutManager có chức năng sắp xếp các item trong RecyclerView.
        // Truyền vào LinearLayoutManager hỗ trợ scroll các item theo chiều dọc.
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // set adapter cho Recycler view
        messagesAdapter = new MessagesAdapter(messagesLists, getActivity());
        messagesRecyclerView.setAdapter(messagesAdapter);

        // Tạo ProgressDialog tượng trưng cho việc load dữ liệu
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // lấy ảnh đại diện của người dùng đang đăng nhập từ Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Url của profilePic được lấy từ Firebase với child "profile pic"
                final String profilePicUrl = snapshot.child("users").child(mobile).child("profile_pic").getValue(String.class);

                // Nếu Url không rỗng, thực hiện đẩy dữ liệu hình lên userProfilePic
                if (!profilePicUrl.isEmpty()) {
                    Picasso.get().load(profilePicUrl).into(userProfilePic);
                }

                // Ẩn progressDialog
                progressDialog.dismiss();
            }

            // Xử lý sự kiện cancel
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        makefriend.setOnClickListener(new View.OnClickListener() {  // nhấn vào biểu tượng kính lúp để
            @Override                                        // tìm kiếm bạn bè
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeFriendActivity.class);
                startActivity(intent);
            }
        });
        AcceptFriend.setOnClickListener(new View.OnClickListener() { // nhấn vào biểu tượng thêm bạn bè
            @Override                                               // để thêm bạn bè
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AcceptFriendActivity.class);
                startActivity(intent);
            }
        });

        // Thực hiện cập nhật dữ liệu lên MessageFragment
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesLists.clear();
                unseenMessage = 0;
                lastMessage = "";
                chatKey = "";
                // Duyệt từng phần tử trong user
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    // Lấy số điện thoại của phần tử đang duyện
                    final String getMobile = dataSnapshot.getKey();
                    dataSet = false;

                    // Điều kiện rẽ nhánh tránh trường hợp thêm thông tin bản thân vào danh sách
                    if (!getMobile.equals(mobile)) {
                        // Lấy tên và avatar các người dùng khác
                        final String getName = dataSnapshot.child("nameUser").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("profile_pic").getValue(String.class);

                        // Trong chat, lấy từng key của từng cuộc hội thoại
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // Lấy số lượng cuộc hội thoại bằng các đếm các con trong chat
                                int getChatCounts = (int) snapshot.getChildrenCount();

                                // Nếu số lượng > 0
                                if (getChatCounts > 0) {
                                    // Duyệt từng phần tử trong chat
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                        // Lấy chatKey của phần từ
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;

                                        // Kiểm tra nếu đoạn hội thoại đã có tin nhắn nào trước đó hay chưa
                                        if (dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages")) {
                                            // Lấy thông tin của 2 người đang trò chuyện
                                            final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                            // Kiểm tra nếu cặp dữ liệu người dùng và đối phương có chính xác với dữ liệu trên Firebase
                                            if ((getUserOne.equals(getMobile) && getUserTwo.equals(mobile)) ||
                                                    (getUserOne.equals(mobile) && getUserTwo.equals(getMobile))) {

                                                // Duyệt từng phần tử trong message, lấy ra MessageKey, tin nhắn cuối cùng đã xem, kiểm tra nếu tin nhắn
                                                // mới nhất lớn hơn tin nhắn đã xem gần nhất thì tăng số lượng tin nhắn chưa xem lên
                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {
                                                    final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long getLastSeenMessages = Long.parseLong(MemoryData.getLastMsgTs(getActivity(), getKey));

                                                    lastMessage = chatDataSnapshot.child("msg").getValue(String.class);
                                                    if (getMessageKey > getLastSeenMessages) {
                                                        unseenMessage++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                // Đổ dữ liệu trên vào messagesLists
                                if (!dataSet) {
                                    dataSet = true;
                                    MessagesList messagesList = new MessagesList(getName, getMobile, lastMessage, getProfilePic, unseenMessage, chatKey);
                                    messagesLists.add(messagesList);
                                    messagesAdapter.updateData(messagesLists);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}