package hcmute.edu.vn.nhom01.zaloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.edu.vn.nhom01.zaloapp.models.User;

public class EditProfileActivity extends AppCompatActivity {
    private CircleImageView imgUserEditProfile; // CircleImageView để chứa avatar
    private TextView txtUserNameEditProfile; //TextView
    private EditText edtNameProfile, edtPhoneProfile, edtEmailProfile; // EditText của tên, số đt, email người dùng
    private Button btnSaveInfo; // nút Lưu thông tin
    private DatabaseReference databaseReference; // dùng để refer db firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        // Ánh xạ edittext, textview, button
        imgUserEditProfile = findViewById(R.id.imgUserEditProfile);
        txtUserNameEditProfile = findViewById(R.id.txtUserNameEditProfile);
        edtNameProfile = findViewById(R.id.edtNameProfile);
        edtPhoneProfile = findViewById(R.id.edtPhoneProfile);
        edtEmailProfile = findViewById(R.id.edtEmailProfile);
        btnSaveInfo = findViewById(R.id.btnSaveInfo);

        // lấy instace của firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // set thông tin người dùng lên các edittext
        setData();

        // Thêm vào các sự kiện onClick
        addControls();

    }

    private void setData() {
        // Số điện thoại đặt readonly, không thể chỉnh sửa
        edtPhoneProfile.setEnabled(false);

        // Lấy tên và số điện thoại người dùng từ bộ nhớ thiết bị
        String name = MemoryData.getName(EditProfileActivity.this);
        String mobile = MemoryData.getData(EditProfileActivity.this);

        // Lấy email và ảnh từ intent
        final String email = getIntent().getStringExtra("email");
        final String profile_pic = getIntent().getStringExtra("profile_pic");

        // set thông tin người dùng lên các edittext
        edtPhoneProfile.setText(mobile);
        edtNameProfile.setText(name);
        txtUserNameEditProfile.setText(name);
        edtEmailProfile.setText(email);

        // Kiểm tra nếu người dùng có avatar thì load lên imgUserEditProfile
        if (!profile_pic.isEmpty()) {
            Picasso.get().load(profile_pic).into(imgUserEditProfile);
        }
    }

    // Hàm xử lý sự kiện
    private void addControls() {
        // Lấy số điện thoại người dùng từ bộ nhớ thiết bị
        String mobile = MemoryData.getData(EditProfileActivity.this);

        // tạo sự kiện onClick cho nút lưu
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy email từ edittext
                String email = edtEmailProfile.getText().toString().trim();

                // Kiểm tra xem email có hợp lệ không, sử dụng hàm isEmailValid, nếu không hợp lệ, thông báo cho người dùng
                if (!CheckValid.isEmailValid(email)) {
                    Toast.makeText(EditProfileActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // thực hiện lưu dữ liệu mới lên firebase
                databaseReference.child("users").child(mobile).child("nameUser").setValue(edtNameProfile.getText().toString().trim());
                databaseReference.child("users").child(mobile).child("emailUser").setValue(edtEmailProfile.getText().toString().trim());
            }
        });
    }
}