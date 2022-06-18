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

    private RecyclerView mRecyclerView; // dùng để ánh xạ tới recyler
    private ImageAdapter mAdapter;     // dùng để chứa list<Upload> để tham chiếu đến ImageAdapter
    private ProgressBar mProgressCircle; // dùng để ánh xạ tới progresscircle


    private DatabaseReference mDatabaseRef;  // gọi database đến firebase
    private List<Upload> mUploads;  // bỏ item vô list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);


        //ánh xạ đến recycler,progresscircle
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // fix size
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);




        mUploads = new ArrayList<>(); // tạo ra 1 List các phần tử Upload(hình ảnh và text) để chứa khi đổ dữ liệu từ firebase xuống

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads"); // gọi đến firebase tới đối tượng uploads

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())//lấy databasesnapshot cho mỗi children của Upload -> để bỏ vào từng activity_item
                {
                    Upload upload = postSnapshot.getValue(Upload.class);  // bỏ dữ liệu vào các phẩn tử của mảng cẩn thận nếu đặt sai tên với firebase thì sẽ không bỏ vô đc
                    mUploads.add(upload); // bỏ phần tử vào list
                }
                mAdapter = new ImageAdapter(ImagesActivity.this, mUploads); // tham chiếu đến Adapter để show lên
                mRecyclerView.setAdapter(mAdapter); // đổ dl từ Adapter lên Recycler
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { // canncel quá trình
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}