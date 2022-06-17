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

    Button btnCreateAccount;
    EditText edtPass, edtPhone, edtEmail, edtRepass;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        addControls();
        addEvents();
    }

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

                writeNewUser(phone, email, pass, null, null);
                Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        edtPhone = findViewById(R.id.phoneNumberUser);
        edtEmail = findViewById(R.id.emailUser);
        edtPass = findViewById(R.id.passwordUser);
        edtRepass = findViewById(R.id.rePasswordUser);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
    private void writeNewUser(String phone, String email, String pass, String name, String profileurl)
    {
        User user = new User(phone, pass, email, "Chưa cập nhật tên!", "");
        mDatabase.child("users").child(phone).setValue(user);
    }
}