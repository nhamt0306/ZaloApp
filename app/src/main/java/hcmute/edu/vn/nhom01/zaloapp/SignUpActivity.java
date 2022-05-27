package hcmute.edu.vn.nhom01.zaloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewUser(edtPhone.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString(), null, null);
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
    private void writeNewUser(String phone, String email, String pass, String name, ImageView img)
    {
        User user = new User(phone, pass, email, "Mai Thanh Nhã");
        mDatabase.child("users").child(phone).setValue(user);
    }
}