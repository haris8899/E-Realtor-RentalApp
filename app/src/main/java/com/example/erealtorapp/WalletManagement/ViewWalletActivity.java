package com.example.erealtorapp.WalletManagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.databinding.ActivityViewWalletBinding;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ViewWalletActivity extends AppCompatActivity {

    Credentials credentials;
    ActivityViewWalletBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewWalletBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        AlertDialog();
        bind.CopyAddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClipboard(ViewWalletActivity.this,bind.AccountAddressTxt.getText().toString());
            }
        });
    }
    public void AlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    credentials = new Wallet().LoadWalletCrediantials(ViewWalletActivity.this,input.getText().toString());
                    Log.d("Tag",credentials.getAddress());
                    bind.AccountAddressTxt.setText(credentials.getAddress());
                    String balance = new Wallet().getbalance(credentials).toString();
                    bind.AccountBalanceTxt.setText(balance+" ETH");
                    dialog.cancel();
                } catch (CipherException | IOException e) {
                    Log.d("Tag",e.getMessage());
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}