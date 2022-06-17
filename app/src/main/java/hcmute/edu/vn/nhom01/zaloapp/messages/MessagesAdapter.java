package hcmute.edu.vn.nhom01.zaloapp.messages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.MessageFragment;
import hcmute.edu.vn.nhom01.zaloapp.R;
import hcmute.edu.vn.nhom01.zaloapp.chat.Chat;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    // messagesLists là một danh sách thể hiện thông tin các các người dùng mà bản thân có thể trò chuyện,
    // gồm tên người dùng, số điện thoại, tin nhắn cuối cùng, ảnh đại diện, id của cuộc hội thoại, tin nhắn chưa xem
    private List<MessagesList> messagesLists;

    // context thể hiện trạng thái hiện tại
    private final Context context;

    // Constructor ChatAdapter với 2 tham số
    public MessagesAdapter(List<MessagesList> messagesLists, Context context) {
        this.messagesLists = messagesLists;
        this.context = context;
    }

    // Tạo ra đối tượng ViewHolder có chứa messages_adapter_layout hiển thị dữ liệu
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, null));
    }

    // Đổ dữ liệu các phần tử vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Lấy MessagesList từ vị trí position
        MessagesList list2 = messagesLists.get(position);

        // Nếu người dùng đó đã có ảnh đại diện thì show lên profilePic
        if (!list2.getProfilePic().isEmpty()) {
            Picasso.get().load(list2.getProfilePic()).into(holder.profilePic);
        }

        // Đổ dữ liệu gồm tên, tin nhắn cuối cùng lên textView name và lastMessage
        holder.name.setText(list2.getName());
        holder.lastMessage.setText(list2.getLastMessage());

        // Nếu không có tin nhắn nào chưa xem, chỉ hiện ra dòng tin nhắn cuối với màu đen,
        // Ngược lại show số lượng tin nhắn chưa xem và đổi màu tin nhắn cuối, giúp người dùng
        // biết được mình chưa xem tin nhắn nào
        if (list2.getUnseenMessages() == 0) {
            holder.unseenMessages.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));
        } else {
            holder.unseenMessages.setVisibility(View.VISIBLE);
            holder.unseenMessages.setText(list2.getUnseenMessages() + "");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.theme_color_80));
        }

        // Với mỗi phần tử trong rootLayout, khi click vào từng phần tử sẽ start Chatactivity và gửi các thông tin của cuộc hội thoại đó qua intent
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("mobile", list2.getMobile());
                intent.putExtra("name", list2.getName());
                intent.putExtra("profile_pic", list2.getProfilePic());
                intent.putExtra("chat_key", list2.getChatKey());
                context.startActivity(intent);

            }
        });
    }

    // Cập nhật messagesLists khi có sự thay đổi
    public void updateData(List<MessagesList> messagesLists) {
        this.messagesLists = messagesLists;
        notifyDataSetChanged();
    }

    // Trả về số lượng phần tử trong messagesLists
    @Override
    public int getItemCount() {
        return messagesLists.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        // CircleImageView thể hiện avatar của người dùng
        private CircleImageView profilePic;

        // TextView name, lastMessage, unseenMessages thể hiện tên, tin nhắn cuối cùng và số lượng tin nhắn chưa xem
        private TextView name;
        private TextView lastMessage;
        private TextView unseenMessages;

        // LinearLayout của rootLayout, chứa các thành phần trên
        private LinearLayout rootLayout;

        // Thực hiện ánh xạ các LinearLayout, TextView, CircleImageView phía trên
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseenMessages = itemView.findViewById(R.id.unseenMessages);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }
}
