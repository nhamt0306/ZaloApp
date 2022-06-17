package hcmute.edu.vn.nhom01.zaloapp.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.nhom01.zaloapp.MemoryData;
import hcmute.edu.vn.nhom01.zaloapp.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    // chatLists là một danh sách gồm các đoạn chat
    private List<ChatList> chatLists;

    // context thể hiện trạng thái hiện tại
    private final Context context;

    // Số điện thoại người dùng đang đăng nhập
    private String userMobile;

    // Constructor ChatAdapter với 2 tham số, userMobile được lấy từ Dữ liệu có sẵn trong thiết bị
    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
        this.userMobile = MemoryData.getData(context);
    }

    // Tạo ra đối tượng ViewHolder có chứa chat_adapter_layout hiển thị dữ liệu
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, null));
    }


    // Đổ dữ liệu các phần tử vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Lấy chatlist ở vị trí position
        ChatList list2 = chatLists.get(position);

        // Nếu là tin nhắn của người dùng thì hiển thị layout myLayout, set nội dung tin nhắn cho myMessage và hiển thị thời gian trên myMessageTime
        if (list2.getMobile().equals(userMobile)) {
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list2.getMessage());
            holder.myMessageTime.setText(list2.getDate()+ " " + list2.getTime());
        }
        // Nếu là tin nhắn của đối phương thì hiển thị layout oppoLayout, set nội dung tin nhắn cho oppoMessage và hiển thị thời gian trên oppoMessageTime
        else {
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(list2.getMessage());
            holder.oppoMessageTime.setText(list2.getDate()+ " " + list2.getTime());

        }
    }

    // Lấy kích cỡ của chatLists
    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    // Cập nhật lại chat list khi có sự thay đổi
    public void updateChatList(List<ChatList> chatLists) {
        this.chatLists = chatLists;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        // opppoLayout là layout thể hiện tin nhắn đối phương, myLayout là layout thể hiện tin nhắn của người dùng
        private LinearLayout oppoLayout, myLayout;

        // oppoMessage thể hiện nội dung văn bản của tin nhắn đối phương, myMessage thể hiện nội dung văn bản của tin nhắn người dùng
        private TextView oppoMessage, myMessage;

        // oppoMessageTime thời gian được gửi của tin nhắn đối phương, myMessageTime thể hiện thời gian được gửi của tin nhắn người dùng
        private TextView oppoMessageTime, myMessageTime;


        // Thực hiện ánh xạ các LinearLayout, TextView phía trên
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMessage);
            oppoMessageTime = itemView.findViewById(R.id.oppoMessageTime);
            myMessageTime = itemView.findViewById(R.id.myMessageTime);

        }
    }
}
