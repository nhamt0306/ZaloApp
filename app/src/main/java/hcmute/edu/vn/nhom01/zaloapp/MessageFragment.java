package hcmute.edu.vn.nhom01.zaloapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.messages.MessagesAdapter;
import hcmute.edu.vn.nhom01.zaloapp.messages.MessagesList;

public class MessageFragment extends Fragment {

    // List chứa những đối tượng MessagesList, dùng lưu lại thông tin của các cuộc hội thoại vào một list
    private final List<MessagesList> messagesLists = new ArrayList<>();

    private String mobile;
    private String email;
    private String name;

    private int unseenMessage = 0;
    private String lastMessage = "";
    private String chatKey = "";

    private boolean dataSet = false;
    private RecyclerView messagesRecyclerView;
    private MessagesAdapter messagesAdapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        // Ánh xạ

        //lấy dữ liệu từ intent
        mobile = getActivity().getIntent().getStringExtra("mobile");
        email = getActivity().getIntent().getStringExtra("email");
        name = getActivity().getIntent().getStringExtra("name");

        final CircleImageView userProfilePic = view.findViewById(R.id.userProfilePic);

        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        messagesRecyclerView.setHasFixedSize(true);

        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // set adapter cho Recycler view
        messagesAdapter = new MessagesAdapter(messagesLists, getActivity());

        messagesRecyclerView.setAdapter(messagesAdapter);

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // lấy ảnh đại diện từ Fire base
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                final String profilePicUrl = snapshot.child("users").child(mobile).child("profile_pic").getValue(String.class);
//
//                if (!profilePicUrl.isEmpty()) {
//
//                    // set profile pic cho circle image view
//                    Picasso.get().load(profilePicUrl).into(userProfilePic);
//                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();
                unseenMessage = 0;
                lastMessage = "";
                chatKey = "";
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {

                    final String getMobile = dataSnapshot.getKey();

                    dataSet = false;
                    if (!getMobile.equals(mobile)) {
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("profile_pic").getValue(String.class);

                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts = (int) snapshot.getChildrenCount();

                                if (getChatCounts > 0) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;

                                        if (dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages")) {
                                            final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                            if ((getUserOne.equals(getMobile) && getUserTwo.equals(mobile)) ||
                                                    (getUserOne.equals(mobile) && getUserTwo.equals(getMobile))) {

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