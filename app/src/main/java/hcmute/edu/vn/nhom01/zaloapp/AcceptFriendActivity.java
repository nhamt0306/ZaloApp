package hcmute.edu.vn.nhom01.zaloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom01.zaloapp.adapters.ImageAdapter;
import hcmute.edu.vn.nhom01.zaloapp.adapters.MakeFriendAdapter;
import hcmute.edu.vn.nhom01.zaloapp.models.Upload;

public class AcceptFriendActivity extends AppCompatActivity {

    private ImageView imageViewAccept;

    private RecyclerView mRecyclerView;
    private MakeFriendAdapter mAdapter;
    private ProgressBar mProgressCircle;

    private String getUserMobile = ""; // chứa số điện thoại của người dùng

    private DatabaseReference mDatabaseRef;
    private List<MakeFriendRequest> mUploads;  // bỏ item vô list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_friend);

        // ánh xạ đến recycler
        mRecyclerView = findViewById(R.id.recycler_view_acceptfriend);

        mRecyclerView.setHasFixedSize(true); // fix size
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ánh xạ đến progresscircle
        mProgressCircle = findViewById(R.id.progress_circle_newfeed);


        mUploads = new ArrayList<>(); // tạo ra 1 List các phần tử Upload(hình ảnh và text) để chứa khi đổ dữ liệu từ firebase xuống
        getUserMobile = MemoryData.getData(AcceptFriendActivity.this); // lay so dien thoai cua user de them vào firebase


        //
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("makefriend/"+getUserMobile.toString());



        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())//lấy databasesnapshot cho mỗi children của Upload -> để bỏ vào từng activity_item
                {
                    MakeFriendRequest upload = postSnapshot.getValue(MakeFriendRequest.class);  // bỏ dữ liệu vào các phẩn tử của mảng cẩn thận nếu đặt sai tên với firebase thì sẽ không bỏ vô đc
                    mUploads.add(upload); // thêm phần tử vào list
                }
                mAdapter = new MakeFriendAdapter(AcceptFriendActivity.this, mUploads); // bỏ vào adapter
                mRecyclerView.setAdapter(mAdapter); // đổ từ adapter vào recycler
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { // canncel quá trình
                Toast.makeText(AcceptFriendActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






















//        imageViewAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
}