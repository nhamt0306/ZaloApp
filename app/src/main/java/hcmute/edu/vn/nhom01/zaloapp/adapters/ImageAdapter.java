package hcmute.edu.vn.nhom01.zaloapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import hcmute.edu.vn.nhom01.zaloapp.CommentShowListt;
import hcmute.edu.vn.nhom01.zaloapp.MemoryData;
import hcmute.edu.vn.nhom01.zaloapp.R;
import hcmute.edu.vn.nhom01.zaloapp.models.Upload;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;


    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v); // tạo 1 imageitem để đổ dữ liệu là hình ảnh và text vào
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.txtUserName.setText(uploadCurrent.getmUserName());


        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .placeholder(R.mipmap.ic_launcher) // thay thế cho hình ảnh khi nó chưa kịp load lên
                .fit()
                .centerInside() // điều chỉnh sự xuất hiện của hình ảnh
                .into(holder.imageView); //
        Picasso.get().load(uploadCurrent.getmUserProfile())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(holder.imgUser_feed);

        holder.txtLikesAmount.setText(String.valueOf(uploadCurrent.getLikes()));

//        Picasso.get()
//                .load(uploadCurrent.getmUserProfile())
//                .placeholder(R.mipmap.ic_launcher) // thay thế cho hình ảnh khi nó chưa kịp load lên
//                .fit()
//                .centerInside() // điều chỉnh sự xuất hiện của hình ảnh
//                .into(holder.imgUser_feed); //

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // Xử lý sự kiện click like
        holder.btnLike_newfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = uploadCurrent.getUploadkey();

                // Lấy sđt user đang đăng nhập
                String mobile = MemoryData.getData(mContext);

                mDatabase.child("users").child(mobile).child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean liked = false;

                        // Kiểm tra xem user này đã like hình chưa
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.getKey().equals(key)) {
                                liked = true;
                                mDatabase.child("users").child(mobile).child("Likes").child(key).removeValue();
                            }
                        }

                        // Nếu user đã like thì giảm số Like đi
                        if (liked) {
                            mDatabase.child("uploads").child(key).child("Likes").setValue(uploadCurrent.getLikes() - 1);
                        } else { // Nếu chưa Like, thêm vào db, tăng lượt like
                            mDatabase.child("users").child(mobile).child("Likes").child(key).setValue("");
                            mDatabase.child("uploads").child(key).child("Likes").setValue(uploadCurrent.getLikes() + 1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        // Xử lý sự kiện click comment
        holder.btnComment_newfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentShowListt.class);
                //  String key=
                String key = uploadCurrent.getUploadkey().toString();
                intent.putExtra("post_key_show", key);      // truyền post_id qua để có thể sử dụng tham chiếu đến post trên firebase
                System.out.println(key);
                String abc = uploadCurrent.getMuserMobile().toString();
                System.out.println(abc);
                intent.putExtra("post_owner", abc); //truyền postowner tức là số điện thoại người dăng post
                //  intent.putExtra()
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;
        private ImageView imgUser_feed;
        private TextView txtUserName;
        private Button btnLike_newfeed, btnComment_newfeed;
        private TextView txtLikesAmount;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);  // tìm textview và imageview trong imageitem để đổ dữ liệu
            imageView = itemView.findViewById(R.id.image_view_upload);
            imgUser_feed = itemView.findViewById(R.id.imgUser_feed);  // có thể trùng tên với mấy file khác nên kiểm tra lại
            txtUserName = itemView.findViewById(R.id.txtUserName_feed);
            btnLike_newfeed = itemView.findViewById(R.id.btnLike_newfeed);
            btnComment_newfeed = itemView.findViewById(R.id.btnComment_newfeed);
            txtLikesAmount = itemView.findViewById(R.id.txtLikesAmount);
        }
    }

}
