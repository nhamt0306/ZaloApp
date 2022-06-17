package hcmute.edu.vn.nhom01.zaloapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom01.zaloapp.adapters.ImageAdapter;
import hcmute.edu.vn.nhom01.zaloapp.models.Upload;

public class NewFeedFragment extends Fragment {

    private ImageButton btn_add_post;
    private Button btn_like;
    private Button btn_comment;

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;


    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    public NewFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        btn_add_post = view.findViewById(R.id.add_new_feed);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        Context context = inflater.getContext();
        mRecyclerView = view.findViewById(R.id.recycler_view_newfeed);
        mRecyclerView.setHasFixedSize(true); // fix size
        //mRecyclerView.setLayoutManager(new LinearLayoutManager());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressCircle = view.findViewById(R.id.progress_circle_newfeed);

        mUploads = new ArrayList<>(); // tạo ra 1 List các phần tử Upload(hình ảnh và text) để chứa khi đổ dữ liệu từ firebase xuống

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())//lấy databasesnapshot cho mỗi children của Upload -> để bỏ vào từng activity_item
                {
                    String key = postSnapshot.getKey(); // Lấy key của từng bài post

                    Upload upload = postSnapshot.getValue(Upload.class);

                    upload.setUploadkey(key);
                    // bỏ dữ liệu vào các phẩn tử của mảng cẩn thận nếu đặt sai tên với firebase thì sẽ không bỏ vô đc
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(context, mUploads); // sử dụng context bởi vì ở trong Fragment thì không thê sử dụng class trực tiếp
                System.out.println("New Feed  " + mAdapter.getItemCount() + mAdapter.toString());
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(NewFeedFragment.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        btn_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

