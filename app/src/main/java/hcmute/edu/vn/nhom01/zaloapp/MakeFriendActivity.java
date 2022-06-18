package hcmute.edu.vn.nhom01.zaloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MakeFriendActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseRef;
    private String getUserMobile = ""; // chứa số điện thoại của người dùng
    private String getUserName = "";   // chứa tên của người dùng
    private Button btnMakeFriend;
    private EditText edt_friendrequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_friend);

        btnMakeFriend=findViewById(R.id.btnMakeFriend);
        edt_friendrequest=findViewById(R.id.findfriend);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("makefriend");

        btnMakeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Url của profilePic được lấy từ Firebase với child "profile pic"
                        getUserMobile = MemoryData.getData(MakeFriendActivity.this); // lay so dien thoai cua user de them vào firebase
                        //gọi hàm upload comment
                        upload_friendRequest();
                    }

                    // Xử lý sự kiện cancel
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void upload_friendRequest() {
        getUserName = MemoryData.getName(MakeFriendActivity.this); // lay so ten cua user de them vào firebase
        getUserMobile = MemoryData.getData(MakeFriendActivity.this); // lay so dien thoai cua user de them vào firebase
        MakeFriendRequest friendRequest=new MakeFriendRequest(getUserMobile.toString(),edt_friendrequest.getText().toString().trim(),getUserName.toString());
        mDatabaseRef.child(edt_friendrequest.getText().toString().trim()).child(getUserMobile.toString()).setValue(friendRequest);
        Intent intent=new Intent(MakeFriendActivity.this,MakeFriendActivity.class);
        startActivity(intent);
    }
}