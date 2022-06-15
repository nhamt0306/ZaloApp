package hcmute.edu.vn.nhom01.zaloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import hcmute.edu.vn.nhom01.zaloapp.chat.Chat;

public class PostActivity extends AppCompatActivity {

    private static final int PCIK_IMAGE_REQUUEST=1;
    private Button mButttonChooseImage;
    private Button mButtonUpload;

    private TextView mTextViewShowUpLoads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private String getProfileUrl="";
    private String getUserMobile = "";
    private String getUserName = "";

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mButttonChooseImage=findViewById(R.id.btnChooseImage);
        mButtonUpload=findViewById(R.id.btnUpload);
        mTextViewShowUpLoads=findViewById(R.id.tv_showuploads);
        mEditTextFileName=findViewById(R.id.edt_filename);
        mImageView=findViewById(R.id.image_view);
        mProgressBar=findViewById(R.id.progress_bar);

        mStorageRef= FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");

        mButttonChooseImage.setOnClickListener(new View.OnClickListener() { // mở file chooser
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask!=null && mUploadTask.isInProgress()) // bấm nhiều lần nhưng khi nào xong progress thì mới vô
                {
                    Toast.makeText(PostActivity.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getUserMobile = MemoryData.getData(PostActivity.this); // lay so dien thoai cua user de them vào firebase
                 getProfileUrl = dataSnapshot.child("users").child(getUserMobile.toString()).child("profile_pic").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mTextViewShowUpLoads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageActivity(); // show hinh len
            }
        });
    }
    private void openFileChooser() //mở file chooser để chọn ảnh
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PCIK_IMAGE_REQUUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PCIK_IMAGE_REQUUEST && resultCode==RESULT_OK && data!=null // kiểm tra hình ảnh đã được chọn chưa để show lên Image View trong chỗ upload hình ảnh
                && data.getData()!=null)
        {
            mImageUri=data.getData();
            Picasso.get().load(mImageUri).into(mImageView); //show hình ảnh lên ImageView bằng Picasso
        }
    }

    private String getFileExtension(Uri uri)  // lấy file extension
    {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private  void uploadFile()   //up file lên firebase từ trang upload
    {
        if(mImageUri!=null)
        {

            StorageReference fileReference=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri)); // tham chiếu đến tập tin hình ảnh bỏ vào "uploads" trên firebase
            //fileReference=mStorageRef.child()
            mUploadTask=fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //nếu thành công
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },5000);
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl(); // lấy địa chỉ url
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult(); // lấy địa chỉ


                            getUserName = MemoryData.getName(PostActivity.this); // lay so ten cua user de them vào firebase
                            getUserMobile = MemoryData.getData(PostActivity.this); // lay so dien thoai cua user de them vào firebase
                            System.out.println("So dien thoai"+getUserMobile.toString());
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),downloadUrl.toString(),getUserMobile.toString(),getUserName.toString(),getProfileUrl.toString()); // thêm text và usermobile vào firebase cùng với url
                            System.out.println("sau khi goi ham"+upload.getName());
                            System.out.println("sau khi goi ham"+upload.getMuserMobile());
                            System.out.println("sau khi goi ham"+ upload.getmUserName());
                            System.out.println("day la profile url" +getProfileUrl.toString());
                            System.out.println("sau khi goi ham"+upload.getmUserProfile());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                            //mDatabaseRef.child("uploads").child(chatKey).child("user_2").setValue(getMobile);

//                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
                            // không dùng code này vì nó bị không thể load hình từ firebase

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() { // nếu không thành côngg
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PostActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() { // trong quá trình upload hình ảnh
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show(); // kiểm tra không có file nào được chọn
        }
    }
    private void openImageActivity() // show list hình ảnh đã upload được
    {
        Intent intent =new Intent(this,ImagesActivity.class);
        startActivity(intent);
    }
}