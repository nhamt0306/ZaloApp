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

import hcmute.edu.vn.nhom01.zaloapp.Comments;
import hcmute.edu.vn.nhom01.zaloapp.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Comments> mComments;


    public CommentAdapter(Context context, List<Comments> comments) {
        mContext = context;
        mComments = comments;
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
        holder.textViewName.setText(uploadCurrent.getComment()); // lấy đoạn comment
        holder.txtUserName.setText(uploadCurrent.getmUserName()); // lấy username
        Picasso.get().load(uploadCurrent.getmUserProfile()).into(holder.imgUser_feed); // lấy ảnh đại diện của user
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        final ImageView imgUser_feed;
        private TextView txtUserName;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name_comment);  // tìm textview và imageview trong imageitem để đổ dữ liệu
            imgUser_feed = itemView.findViewById(R.id.imgUser_comment);  // có thể trùng tên với mấy file khác nên kiểm tra lại
            txtUserName = itemView.findViewById(R.id.txtUserName_comment);
        }
    }
}
