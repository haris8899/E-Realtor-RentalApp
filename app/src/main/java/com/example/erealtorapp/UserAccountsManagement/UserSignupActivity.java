package com.example.erealtorapp.UserAccountsManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserAccountsManagement.UserClassData.UserClass;
import com.example.erealtorapp.databinding.ActivityUserSignupBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class UserSignupActivity extends AppCompatActivity {

    String ImageUri;
    ActivityUserSignupBinding binding;
    //using Bom-to authenticate Firebase
    private FirebaseAuth auth;
    FirebaseDatabase database;
    //To  show loading
    ProgressDialog dilog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        //line to hide actionbar
        getSupportActionBar().hide();
        //line to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityUserSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        //Database instance
        database = FirebaseDatabase.getInstance();

        dilog = new ProgressDialog(UserSignupActivity.this);
        dilog.setTitle("Creating Account");
        dilog.setMessage("We're creating your account ");

        binding.RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilog.show();
                binding.ProfileImageView.buildDrawingCache();
                Bitmap bmap = binding.ProfileImageView.getDrawingCache();
                auth.createUserWithEmailAndPassword(binding.EmailSignupText.getEditText().getText().toString().trim(), binding.PasswordSignUpTextBox.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dilog.dismiss();
                        if (task.isSuccessful()) {
                            //calling singnup constructor for UserRegistrationclass
                            UserClass User = new UserClass(binding.usernameTextbox.getEditText().getText().toString().trim(), binding.EmailSignupText.getEditText().getText().toString().trim(), binding.PasswordSignUpTextBox.getEditText().getText().toString().trim(), binding.phonetextBox.getEditText().getText().toString().trim(), "Simple User", BitmaptoString(bmap));

                            //get Userid from Authentication portal
                            String id = task.getResult().getUser().getUid();

                            //Add User to database
                            database.getReference().child("Users").child(id).setValue(User);

                            Toast.makeText(UserSignupActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UserSignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        binding.UploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UserSignupActivity.this).crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        binding.ProfileImageView.setImageURI(uri);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String email = intent.getStringExtra("A1");
        String password = intent.getStringExtra("A2");

    }

    public String BitmaptoString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }
}