package com.example.erealtorapp.UserAccountsManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserDashboardPackage.UserDashBoardMainActivity;
import com.example.erealtorapp.databinding.ActivityUserLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLoginActivity extends AppCompatActivity {

    ActivityUserLoginBinding binding;
    //using Bom-to authenticate Firebase
    private FirebaseAuth auth;
    //Firebase database instance
    FirebaseDatabase database;
    //To  show loading
    ProgressDialog dilog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //line to hide actionbar
        getSupportActionBar().hide();
        //line to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        //Initiallizing Dialog to display
        dilog = new ProgressDialog(UserLoginActivity.this);
        dilog.setTitle("Login");
        dilog.setMessage("Login to your account ");
        //Sign in user to Database
        binding.SignInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //For Input Validation
                String Semail=binding.EmailSignInTextbox.getEditText().getText().toString().trim();
                String Spass=binding.PasswordTextbox.getEditText().getText().toString().trim();
                if (Semail.isEmpty()){
                    binding.EmailSignInTextbox.setError("Email Address is Required");
                    return;
                }
                if (Spass.isEmpty()){
                    binding.PasswordTextbox.setError("Password is Required");
                    return;
                }

                //For Signin into the system
                dilog.show();
                auth.signInWithEmailAndPassword(binding.EmailSignInTextbox.getEditText().getText()
                                        .toString().trim()
                                , binding.PasswordTextbox.getEditText().getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //for Email Verification
                                    if(auth.getCurrentUser().isEmailVerified())
                                    {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myref = database.getReference("Users");
                                        ValueEventListener valueEventListener =
                                                myref.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Intent intent = new Intent(UserLoginActivity.this,
                                                                UserDashBoardMainActivity.class);
                                                        SharedPreferences sharedpreference =
                                                                getSharedPreferences("userfiles", 0);
                                                        SharedPreferences.Editor editor = sharedpreference.edit();
                                                        editor.clear();
                                                        editor.commit();
                                                        editor.putString("A1",auth.getUid());
                                                        editor.putString("A2",snapshot.child(auth
                                                                        .getUid()
                                                                        .toString())
                                                                .child("type")
                                                                .getValue().toString());
                                                        editor.commit();
                                                        startActivity(intent);
                                                        dilog.cancel();
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }else{
                                        Toast.makeText(UserLoginActivity.this,"Please verify your Email Address",Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(UserLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    dilog.cancel();
                                }
                            }
                        });
            }
        });

    }

    public void LaunchSignUpActivity(View view) {
        Intent intent = new Intent(this, UserSignupActivity.class);
        intent.putExtra("A1", binding.EmailSignInTextbox.getEditText().getText().toString().trim());
        intent.putExtra("A2", binding.PasswordTextbox.getEditText().getText().toString().trim());
        startActivity(intent);
    }
}