package com.example.erealtorapp.AdminPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserAccountsManagement.UserClassData.UserRegistrationClass;
import com.example.erealtorapp.UserAccountsManagement.UserSignupActivity;
import com.example.erealtorapp.databinding.ActivityRecruitAgentBinding;
import com.example.erealtorapp.databinding.ActivityUserSignupBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class RecruitAgent extends AppCompatActivity {

    ActivityRecruitAgentBinding binding;
    FirebaseDatabase database;
    DatabaseReference myref;
    FirebaseAuth auth;
    ProgressDialog dilog;
    private static final int SMS_PERMISSION_CODE = 100;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRecruitAgentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //line to hide actionbar
        getSupportActionBar().hide();
        //line to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        database= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myref = database.getReference();
        dilog = new ProgressDialog(RecruitAgent.this);
        dilog.setTitle("Creating Account");
        dilog.setMessage("Creating Agent Account");
        binding.RegisterButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dilog.show();
            pass = getAlphaNumericString(8);
            binding.ProfileImageView.buildDrawingCache();
            Bitmap bmap = binding.ProfileImageView.getDrawingCache();
            auth.createUserWithEmailAndPassword(binding.EmailSignupText.getEditText().getText().toString().trim(),
                    pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dilog.dismiss();
                    if(task.isSuccessful()){
                        String phoneno = binding.phonetextBox.getEditText().getText().toString().trim();
                        Log.d("Tag",phoneno);
                        if (ContextCompat.checkSelfPermission(RecruitAgent.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(RecruitAgent.this,
                                    new String[] { Manifest.permission.SEND_SMS },SMS_PERMISSION_CODE);
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneno
                                    , null, "Your Agent Credintials are\n Email: "
                                            +binding.EmailSignupText.getEditText().getText().toString().trim()+"\nPassword: "+pass,
                                    null, null);
                        } else {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneno
                                    , null, "Your Agent Credintials are\n Email: "
                                            +binding.EmailSignupText.getEditText().getText().toString().trim()+"\nPassword: "+pass,
                                    null, null);
                        }
                        //calling singnup constructor for UserRegistrationclass
                        UserRegistrationClass User=UserRegistrationClass.getInstance(binding.usernameTextbox
                                .getEditText().getText().toString().trim(),
                                binding.EmailSignupText.getEditText().getText().toString().trim(),pass
                                ,binding.phonetextBox.getEditText().getText().toString().trim(),"agent",BitmaptoString(bmap));

                        //get Userid from Authentication portal
                        String id=task.getResult().getUser().getUid();

                        //Add User to database
                        database.getReference().child("Users").child(id).setValue(User);

                        Toast.makeText(RecruitAgent.this,"Account Created Successfully!",Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        finish();
                    }
                    else{
                        Toast.makeText(RecruitAgent.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    });
        binding.UploadImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImagePicker.with(RecruitAgent.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
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

    public String BitmaptoString(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return result;
    }
    static String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

}