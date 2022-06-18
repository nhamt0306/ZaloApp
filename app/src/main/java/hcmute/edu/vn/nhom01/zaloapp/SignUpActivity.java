package hcmute.edu.vn.nhom01.zaloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hcmute.edu.vn.nhom01.zaloapp.models.User;

public class SignUpActivity extends AppCompatActivity {
    // Nút tạo tài khoản
    Button btnCreateAccount;
    // edittext mật khẩu, số điện thoại, email, nhập lại mật khẩu
    EditText edtPass, edtPhone, edtEmail, edtRepass;
    // DatabaseReference refer đến firebase
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đặt layout là signup_layout
        setContentView(R.layout.signup_layout);
        // Gọi hàm Ánh xạ và thêm xử lý sự kiện click
        addControls();
        addEvents();
    }

    // Hàm tạo listener xử lý sự kiện click
    private void addEvents() {
        // Xử lý sự kiện nhấn tạo tài khoản
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy dữ liệu từ editText
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String repass = edtRepass.getText().toString().trim();

                // Kiểm tra email có hợp lệ hay không
                if (!CheckValid.isEmailValid(email)) {
                    Toast.makeText(SignUpActivity.this, "Thử lại! Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra số điện thoại có hợp lệ không
                if (!CheckValid.isPhoneValid(phone) && phone.length() > 12) {
                    Toast.makeText(SignUpActivity.this, "Thử lại! Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra độ dài mật khẩu
                if (pass.length() < 8) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu cần có ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu có khơp không
                if (!pass.equals(repass)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Gọi hàm thêm User mới vào Firebase
                writeNewUser(phone, email, pass, null, null);
                Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                // Tạo intent dẫn đến trang Đăng nhập
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm ánh xạ các button, edittext, DatabaseReference
    private void addControls() {
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        edtPhone = findViewById(R.id.phoneNumberUser);
        edtEmail = findViewById(R.id.emailUser);
        edtPass = findViewById(R.id.passwordUser);
        edtRepass = findViewById(R.id.rePasswordUser);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Tạo thêm user trên Fire base trong child users
    private void writeNewUser(String phone, String email, String pass, String name, String profileurl) {
        // Tạo đối tương user với các thông tin truyền vào từ người dùng, mặc định khi vừa tạo tài
        // khoản tên và url avatar sẽ chưa có, người dùng sẽ phải cập nhật sau
        User user = new User(phone, pass, email, "Chưa cập nhật tên!", "");
        // Thực hiện tạo user trên child users
        mDatabase.child("users").child(phone).setValue(user);
    }
}