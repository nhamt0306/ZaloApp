package hcmute.edu.vn.nhom01.zaloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.edu.vn.nhom01.zaloapp.models.User;

public class MainActivity extends AppCompatActivity {
    // Nút đăng nhập
    Button btnContinue;
    // TextView quên mật khẩu, đăng ký
    TextView txtForgotPass, txtSignUp;
    // EditText số điện thoại và mật khẩu người dùng
    EditText edtPhone, edtPass;
    // Danh sách các user
    ArrayList<User> listUsers = null;
    // Dùng để refer đến Firebase
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đặt layout là login_layout
        setContentView(R.layout.login_layout);

        // Gọi hàm ánh xạ
        addControls();

        // Gọi hàm xử lý thao tác click nút đăng ký
        addEventSignUpClick();

        // Gọi hàm xử lý thao tác click nút đăng nhập
        addEventContinueClick();
    }

    // Hàm xử lý thao tác click nút đăng nhập
    private void addEventContinueClick() {
        // Đặt click listener cho nút đăng nhập
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra người dùng có bỏ trống mục nào không
                if (edtPhone.getText().toString().equals("") || edtPass.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Refer đến Firebase
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    // Lấy dữ liệu user có số điện thoại vừa nhập
                    mDatabase.child("users").child(edtPhone.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Biến user lưu lại thông tin user (nếu có)
                            User user = dataSnapshot.getValue(User.class);
                            // Trường hợp user có tồn tại, kiểm tra mật khẩu có chính xác không
                            if (user != null) {
                                if (user.getPassword().equals(edtPass.getText().toString())) {
                                    // Lưu số điện thoại vào bộ nhớ thiết bị
                                    MemoryData.saveData(user.getPhoneNumberUser(), MainActivity.this);

                                    // Lưu tên vào bộ nhớ
                                    MemoryData.saveName(user.getNameUser(), MainActivity.this);

                                    // Tạo intent dẫn đến trang chủ, gửi kèm theo số điện thoại, tên, email, url avatar người dùng
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("mobile", user.getPhoneNumberUser());
                                    intent.putExtra("name", user.getNameUser());
                                    intent.putExtra("email", user.getEmailUser());
                                    intent.putExtra("profile_pic", user.getProfile_pic());
                                    startActivity(intent);
                                }
                            } else {
                                // Nếu sai mật khẩu, thông báo đăng nhập thất bại
                                Toast.makeText(MainActivity.this, "Login fail!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Trường hợp lấy dữ liệu không thành công
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Get data fail!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    // Hàm xử lý khi nhấn vào đăng ký
    private void addEventSignUpClick() {
        // Tạo text cho nút và tạo listener onclick
        String signUpText = "Don't have an account? Sign up";
        SpannableString ss = new SpannableString(signUpText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(MainActivity.this, "Bạn đã click vào Sign up", Toast.LENGTH_SHORT).show();

                // Tạo intent dẫn đến trang đăng ký
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
            }
        };
        ss.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Set text cho txtSignUp
        txtSignUp.setText(ss);
        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // Hàm ánh xạ các textview, button, edittext
    private void addControls() {
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnContinue = findViewById(R.id.btnContinue);
        edtPass = findViewById(R.id.edtPass);
        edtPhone = findViewById(R.id.edtPhone);
    }
}