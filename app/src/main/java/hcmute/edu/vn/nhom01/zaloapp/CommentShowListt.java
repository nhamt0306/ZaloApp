package hcmute.edu.vn.nhom01.zaloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.adapters.CommentAdapter;
import hcmute.edu.vn.nhom01.zaloapp.models.Comments;

public class CommentShowListt extends AppCompatActivity {
    private RecyclerView mRecyclerView;  // để ánh xja đến recyclerview
    private CommentAdapter mAdapter;  // tạo ra adapter từ CommentAdapter
    private ProgressBar mProgressCircle;
    private Integer getCommentCount; // lấy số lượng comment hiện có của bài post
    private String getProfileUrl = "";  // chứa link ảnh profile của người dùng
    private String getUserMobile = ""; // chứa số điện thoại của người dùng
    private String getUserName = "";   // chứa tên của người dùng
    private String post_key = "";       // chứa postid được lấy từ intent
    private String post_owner = "";     // chứa postowner được lấy từ intent
    private ImageButton btnSendText;   // btn gửi comment
    private EditText edt_InputComment;  // edittext để chứa comment

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    // getinstance firebase

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef_foradapter;   // dataref này dành cho adapter bởi nếu xài chung thì sẽ bị lỗi
    // vì cái ở trên không cần dẫn vào post_key cũng được nhưng cái
    // này cần để dẫn vào lấy các phần tử comment bài post đó ra
    private List<Comments> mComments;  // bỏ item vô list , chứa các Commments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        String mobile = MemoryData.getData(CommentShowListt.this); // lấy số điện thoại để truyền vào hàm bên dưới


        btnSendText = findViewById(R.id.sendText);
        edt_InputComment = findViewById(R.id.ImputComment);


        final CircleImageView userProfilePic = findViewById(R.id.userProfilePic); // lấy ảnh đại diện cho user

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Url của profilePic được lấy từ Firebase với child "profile pic"
                getUserMobile = MemoryData.getData(CommentShowListt.this); // lay so dien thoai cua user de them vào firebase

                final String profilePicUrl = snapshot.child("users").child(mobile).child("profile_pic").getValue(String.class);
                getCommentCount = (int) snapshot.child("comments").child(post_key).getChildrenCount();  //này dùng để lấy số lượng child có trong child post_key với giá trị tương ứng
                getProfileUrl = snapshot.child("users").child(getUserMobile.toString()).child("profile_pic").getValue(String.class);  // lấy link ảnh profile của user

                // Nếu Url không rỗng, thực hiện đẩy dữ liệu hình lên userProfilePic
                if (!profilePicUrl.isEmpty()) {
                    Picasso.get().load(profilePicUrl).into(userProfilePic);
                }// Ẩn progressDialog

            }

            // Xử lý sự kiện cancel
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        post_key = getIntent().getStringExtra("post_key_show");  // lấy postid từ intent
        post_owner = getIntent().getStringExtra("post_owner");   // lấy postowner từ intent

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("comments");

        // ở đây có 2 databaseref dùng cho 2 cái 1 cái dùng cho việc thêm vào firebase
        // cái dưới dùng cho adapter

        mDatabaseRef_foradapter = FirebaseDatabase.getInstance().getReference("comments/" + post_key);


        mRecyclerView = findViewById(R.id.recycler_view_newfeed);

        mRecyclerView.setHasFixedSize(true); // fix size
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle_newfeed);


        mComments = new ArrayList<>(); // tạo ra 1 List các phần tử Upload(hình ảnh và text) để chứa khi đổ dữ liệu từ firebase xuống


        btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Url của profilePic được lấy từ Firebase với child "profile pic"
                        getUserMobile = MemoryData.getData(CommentShowListt.this); // lay so dien thoai cua user de them vào firebase

                        final String profilePicUrl = snapshot.child("users").child(mobile).child("profile_pic").getValue(String.class);
                        getCommentCount = (int) snapshot.child("comments").child(post_key).getChildrenCount();  // lấy comment count trong từng postid

                        getProfileUrl = snapshot.child("users").child(getUserMobile.toString()).child("profile_pic").getValue(String.class); // lấy link profile picture

                        //gọi hàm upload comment
                        uploadComment();

                        // Nếu Url không rỗng, thực hiện đẩy dữ liệu hình lên userProfilePic
                        if (!profilePicUrl.isEmpty()) {
                            Picasso.get().load(profilePicUrl).into(userProfilePic);
                        }// Ẩn progress
                    }

                    // Xử lý sự kiện cancel
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        mDatabaseRef_foradapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())//lấy databasesnapshot cho mỗi children của Upload -> để bỏ vào từng activity_item
                {
                    Comments comments = postSnapshot.getValue(Comments.class);  // bỏ dữ liệu vào các phẩn tử của mảng cẩn thận nếu đặt sai tên với firebase thì sẽ không bỏ vô đc
                    mComments.add(comments);
                }
                mAdapter = new CommentAdapter(CommentShowListt.this, mComments); // đổ dữ liệu vào adapter
                System.out.println("Comment Activity: " + mAdapter.toString());
                mRecyclerView.setAdapter(mAdapter); // đổ dữ liệu vào recycler
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { // canncel quá trình
                Toast.makeText(CommentShowListt.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadComment() {
        getUserName = MemoryData.getName(CommentShowListt.this); // lay so ten cua user de them vào firebase
        getUserMobile = MemoryData.getData(CommentShowListt.this); // lay so dien thoai cua user de them vào firebase
        String post_key = getIntent().getStringExtra("post_key_show");  // get intent để lấy postid
        String post_owner = getIntent().getStringExtra("post_owner"); // get intent dẻ lấy post owner

        Comments comments = new Comments(post_key, getUserMobile.toString(), post_owner, getUserName.toString(), getProfileUrl.toString(), edt_InputComment.getText().toString().trim());
        // lấy theo thứ tự trong constructor được tạo bên class Comments

        mDatabaseRef.child(post_key).child(getCommentCount.toString()).setValue(comments);
        // đổ vào firebase


        Intent intent = new Intent(CommentShowListt.this, CommentShowListt.class); // start intent lần nữa để load lại
        intent.putExtra("post_key_show", post_key);
        startActivity(intent);
    }
}
