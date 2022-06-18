package hcmute.edu.vn.nhom01.zaloapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hcmute.edu.vn.nhom01.zaloapp.models.Comments;
import hcmute.edu.vn.nhom01.zaloapp.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ImageViewHolder> {

    private Context mContext; //     // context thể hiện trạng thái hiện tại
    private List<Comments> mComments;  //list class Comment để chứa các item

    public CommentAdapter(Context context, List<Comments> comments) { // constructor  để gọi bên các hàm show recycler
        mContext = context;  // context của class cần truy xuất
        mComments = comments; // list class comment
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return new ImageViewHolder(v); // tạo 1 commentitem để đổ dữ liệu là hình ảnh và text vào
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Comments uploadCurrent = mComments.get(position);
        holder.textViewName.setText(uploadCurrent.getComment()); // set đoạn comment
        holder.txtUserName.setText(uploadCurrent.getmUserName()); // set username
        Picasso.get().load(uploadCurrent.getmUserProfile()).into(holder.imgUser_feed); // set ảnh đại diện của user
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName; // textview chứa đoạn comment
        final ImageView imgUser_feed;  // avatar của user
        private TextView txtUserName;  // textview chứa tên khách hàng


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name_comment);  //  ánh xạ đến text_view_name của file commentitem
            imgUser_feed = itemView.findViewById(R.id.imgUser_comment);  //  ánh xạ đến imgUserfeed của file commentitem
            txtUserName = itemView.findViewById(R.id.txtUserName_comment);// ánh xạ đến txtUserName của file commentitem
        }
    }
}
