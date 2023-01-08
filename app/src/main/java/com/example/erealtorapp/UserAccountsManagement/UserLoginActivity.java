package com.example.erealtorapp.UserAccountsManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

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
        binding=ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //line to hide actionbar
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        //Initiallizing Dialog to display
        dilog=new ProgressDialog(UserLoginActivity.this);
        dilog.setTitle("Login");
        dilog.setMessage("Login to your account ");
        //Sign in user to Database
        binding.SignInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilog.show();
                auth.signInWithEmailAndPassword(binding.EmailSignInTextbox.getEditText().getText().toString().trim(),binding.PasswordTextbox.getEditText().getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent= new Intent(UserLoginActivity.this, UserDashBoardMainActivity.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    Toast.makeText(UserLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    public void LaunchSignUpActivity(View view) {
        Intent intent= new Intent(this,UserSignupActivity.class);
        //intent.putExtra("K1","Ali");
        //intent.putExtra("K2","Ahmad");
        intent.putExtra("A1",binding.EmailSignInTextbox.getEditText().getText().toString().trim());
        intent.putExtra("A2",binding.PasswordTextbox.getEditText().getText().toString().trim());
        startActivity(intent);
    }
}