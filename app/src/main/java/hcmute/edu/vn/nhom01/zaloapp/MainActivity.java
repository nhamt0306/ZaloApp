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

    Button btnContinue;
    TextView txtForgotPass, txtSignUp;
    EditText edtPhone, edtPass;
    ArrayList<User> listUsers = null;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        addControls();
        addEventSignUpClick();
        addEventForgotPassClick();
        addEventContinueClick();
    }

    private void addEventContinueClick() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPhone.getText().toString().equals("") || edtPass.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                     Lấy dữ liệu từ Firebase
                    mDatabase.child("users").child(edtPhone.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                if (user.getPassword().equals(edtPass.getText().toString()))
                                {
                                    // lưu số điện thoại vào bộ nhớ
                                    MemoryData.saveData(user.getPhoneNumberUser(), MainActivity.this);

                                    // lưu tên vào bộ nhớ
                                    MemoryData.saveName(user.getNameUser(), MainActivity.this);

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("mobile", user.getPhoneNumberUser());
                                    intent.putExtra("name", user.getNameUser());
                                    intent.putExtra("email", user.getEmailUser());
                                    intent.putExtra("profile_pic", user.getProfile_pic());
                                    startActivity(intent);
//                                Toast.makeText(MainActivity.this,"Login success!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Login fail!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this,"Get data fail!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    private void addEventForgotPassClick() {
        String forgotText = "Forgot password?";
        SpannableString ss2 = new SpannableString(forgotText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(MainActivity.this, "Bạn đã click vào Forgot password?", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
        ss2.setSpan(clickableSpan,0,16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtForgotPass.setText(ss2);
        txtForgotPass.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addEventSignUpClick() {
        String signUpText = "Don't have an account? Sign up";
        SpannableString ss = new SpannableString(signUpText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(MainActivity.this, "Bạn đã click vào Sign up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
            }
        };
        ss.setSpan(clickableSpan,23,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSignUp.setText(ss);
        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addControls() {
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnContinue = findViewById(R.id.btnContinue);
        edtPass = findViewById(R.id.edtPass);
        edtPhone = findViewById(R.id.edtPhone);
    }
}