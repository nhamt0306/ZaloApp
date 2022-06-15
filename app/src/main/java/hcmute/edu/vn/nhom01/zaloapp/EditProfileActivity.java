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
    private CircleImageView imgUserEditProfile;
    private TextView txtUserNameEditProfile;
    private EditText edtNameProfile, edtAgeProfile, edtPhoneProfile, edtEmailProfile, edtAddressProfile;
    private Button btnSaveInfo;
    private DatabaseReference databaseReference;
    private Button btn_EditAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);



        imgUserEditProfile = findViewById(R.id.imgUserEditProfile);
        txtUserNameEditProfile = findViewById(R.id.txtUserNameEditProfile);
        edtNameProfile = findViewById(R.id.edtNameProfile);
//        edtAgeProfile = findViewById(R.id.edtAgeProfile);
        edtPhoneProfile = findViewById(R.id.edtPhoneProfile);
        edtEmailProfile = findViewById(R.id.edtEmailProfile);
//        edtAddressProfile = findViewById(R.id.edtAddressProfile);
        btnSaveInfo = findViewById(R.id.btnSaveInfo);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        setData();
        addControls();

    }

    private void setData() {
        edtPhoneProfile.setEnabled(false);

        // Lấy tên và số điện thoại người dùng từ bộ nhớ thiết bị
        String name = MemoryData.getName(EditProfileActivity.this);
        String mobile = MemoryData.getData(EditProfileActivity.this);

        // Lấy email và ảnh từ intent
        final String email = getIntent().getStringExtra("email");
        final String profile_pic = getIntent().getStringExtra("profile_pic");

        edtPhoneProfile.setText(mobile);
        edtNameProfile.setText(name);
        txtUserNameEditProfile.setText(name);
        edtEmailProfile.setText(email);


        if (!profile_pic.isEmpty()) {
            Picasso.get().load(profile_pic).into(imgUserEditProfile);
        }
    }

    private void addControls() {
        String mobile = MemoryData.getData(EditProfileActivity.this);

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("users").child(mobile).child("nameUser").setValue(edtNameProfile.getText().toString().trim());
                databaseReference.child("users").child(mobile).child("emailUser").setValue(edtEmailProfile.getText().toString().trim());
            }
        });
    }
}