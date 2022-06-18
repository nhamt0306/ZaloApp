package hcmute.edu.vn.nhom01.zaloapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.nhom01.zaloapp.Friends;
import hcmute.edu.vn.nhom01.zaloapp.MakeFriendRequest;
import hcmute.edu.vn.nhom01.zaloapp.R;

public class MakeFriendAdapter extends RecyclerView.Adapter<MakeFriendAdapter.ImageViewHolder> {

    private Context mContext; // Lưu trạng thái hiện tại
    private List<MakeFriendRequest> mMakeFriend;  // tạo 1 list từ class MakeFriendRequest để tham chiếu

    // Constuctor mặc định
    public MakeFriendAdapter(Context context, List<MakeFriendRequest> uploads) {
        mContext = context;
        mMakeFriend = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.acceptactivity_item, parent, false);
        return new ImageViewHolder(v); // tạo 1 imageitem để đổ dữ liệu là hình ảnh và text vào
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        MakeFriendRequest uploadCurrent = mMakeFriend.get(position);
        holder.txtUserName.setText(uploadCurrent.getmSender_name()); // lấy username

        holder.btnAccept.setOnClickListener(new View.OnClickListener() { // bắt sự kiện click vào button accept
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Friends.class);
                String abc = uploadCurrent.getmSender_mobile().toString(); // truyền vào sdt của người gửi request
                intent.putExtra("sender", abc); //truyền postowner tức là số điện thoại người dăng post
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMakeFriend.size();
    }


    static class ImageViewHolder extends RecyclerView.ViewHolder {
        //  final ImageView imgUser_feed;
        private TextView txtUserName; // chứa tên user
        private Button btnAccept;  // btn accept bạn bè

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName_comment); // ánh xạ đến UserName
            btnAccept = itemView.findViewById(R.id.btnAccept); // ánh xja đến button accept
        }
    }

}
