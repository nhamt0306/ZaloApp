package hcmute.edu.vn.nhom01.zaloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.edu.vn.nhom01.zaloapp.adapters.MakeFriendAdapter;

public class Friends extends AppCompatActivity {


    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef_friends;
    private String getUserMobile = ""; // chứa số điện thoại của người dùng

    private String sender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        getUserMobile = MemoryData.getData(Friends.this); // lay so dien thoai cua user de them vào firebase
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("makefriend");
        sender=getIntent().getStringExtra("sender");
        mDatabaseRef_friends = FirebaseDatabase.getInstance().getReference("friends");


        System.out.println(getUserMobile.toString()+"sender"+sender.toString());


        mDatabaseRef.child(getUserMobile.toString()).child(sender.toString()).getRef().setValue(null);



        mDatabaseRef_friends.child(getUserMobile.toString()).child(sender.toString()).setValue(sender.toString());
        mDatabaseRef_friends.child(sender.toString()).child(getUserMobile.toString()).setValue(getUserMobile.toString());

    }
}