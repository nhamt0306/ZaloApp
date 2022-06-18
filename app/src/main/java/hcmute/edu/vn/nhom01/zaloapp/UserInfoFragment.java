package hcmute.edu.vn.nhom01.zaloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoFragment extends Fragment {
    // Button thêm ảnh đại diện
    private Button btn_EditAvatar;

    // Textview đăng xuất, cloud, sửa thông tin người dùng, cài đặt, tên người dùng
    private TextView txtLogout, txtCloud, txtEditProfile, txtSetting;
    private TextView txtUserName;

    // Show ảnh đại diện nguoif dùng
    private CircleImageView imgUser;

    // Constructor rỗng của UserInfoFragment
    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate fragment_info layout
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // Ánh xạ các textview, CircleImageView, button
        txtEditProfile = view.findViewById(R.id.txtEditProfile);
        txtCloud = view.findViewById(R.id.txtCloud);
        txtLogout = view.findViewById(R.id.txtLogout);
        txtSetting = view.findViewById(R.id.txtSetting);
        txtUserName = view.findViewById(R.id.txtUserName);
        imgUser = view.findViewById(R.id.imgUser);
        btn_EditAvatar = view.findViewById(R.id.btnEditImage);

        // Hàm set tên người dùng và ảnh đại diện
        setData();

        // Tạo xử lý click cho textview đăng xuất
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo intent dẫn đến trang đăng nhập, kết thúc activity hiện tại
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Tạo xử lý click cho nút đặt ảnh đại diện
        btn_EditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo intent dẫn đến trang đổi ảnh đại diện
                Intent intent = new Intent(getActivity(), EditAvatarActivity.class);
                startActivity(intent);
            }
        });

        // Tạo xử lý click cho nút cloud (chưa xử lý)
        txtCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chưa xử lý tính năng này!", Toast.LENGTH_SHORT).show();
            }
        });

        // Tạo xử lý click cho nút cài đặt (chưa xử lý)
        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chưa xử lý tính năng này!", Toast.LENGTH_SHORT).show();
            }
        });

        // Tạo xử lý click cho textview chỉnh sưa thông tin cá nhân
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ intent, gồm số đt, email, tên, url ảnh đại diện
                String mobile = getActivity().getIntent().getStringExtra("mobile");
                String email = getActivity().getIntent().getStringExtra("email");
                String name = getActivity().getIntent().getStringExtra("name");
                String profile_pic = getActivity().getIntent().getStringExtra("profile_pic");

                // Tạo intent dẫn đến trang thay đổi thông tin cá nhân
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);

                // Thêm các thông tin vừa lấy được vào intent, thực hiện start trang thay đổi thông tin cá nhân
                intent.putExtra("mobile", mobile);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("profile_pic", profile_pic);

                startActivity(intent);
            }
        });

        return view;
    }

    // Hàm load ảnh đại diên và tên người dùng
    private void setData() {
        // Lấy url ảnh đại diện từ intent
        final String profile_pic = getActivity().getIntent().getStringExtra("profile_pic");
        // Nếu url không rỗng thì set cho imgUser
        if (!profile_pic.isEmpty()) {
            Picasso.get().load(profile_pic).into(imgUser);
        }
        // Lấy tên người dùng từ bộ nhớ thiết bị và set cho txtUserName
        String name = MemoryData.getName(getActivity());
        txtUserName.setText(name);
    }
}