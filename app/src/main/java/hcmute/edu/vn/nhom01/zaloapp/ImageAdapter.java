package hcmute.edu.vn.nhom01.zaloapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.messages.MessagesList;

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
        Picasso.get().load(uploadCurrent.getmUserProfile()).into(holder.imgUser_feed);

//        Picasso.get()
//                .load(uploadCurrent.getmUserProfile())
//                .placeholder(R.mipmap.ic_launcher) // thay thế cho hình ảnh khi nó chưa kịp load lên
//                .fit()
//                .centerInside() // điều chỉnh sự xuất hiện của hình ảnh
//                .into(holder.imgUser_feed); //
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

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);  // tìm textview và imageview trong imageitem để đổ dữ liệu
            imageView = itemView.findViewById(R.id.image_view_upload);
            imgUser_feed = itemView.findViewById(R.id.imgUser_feed);  // có thể trùng tên với mấy file khác nên kiểm tra lại
            txtUserName = itemView.findViewById(R.id.txtUserName_feed);
        }
    }

}
