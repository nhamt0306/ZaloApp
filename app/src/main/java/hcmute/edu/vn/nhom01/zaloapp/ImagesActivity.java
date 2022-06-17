package hcmute.edu.vn.nhom01.zaloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
import hcmute.edu.vn.nhom01.zaloapp.models.Upload;

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;


    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;  // bỏ item vô list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true); // fix size
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);


        mUploads = new ArrayList<>(); // tạo ra 1 List các phần tử Upload(hình ảnh và text) để chứa khi đổ dữ liệu từ firebase xuống

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())//lấy databasesnapshot cho mỗi children của Upload -> để bỏ vào từng activity_item
                {
                    Upload upload = postSnapshot.getValue(Upload.class);  // bỏ dữ liệu vào các phẩn tử của mảng cẩn thận nếu đặt sai tên với firebase thì sẽ không bỏ vô đc
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { // canncel quá trình
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}