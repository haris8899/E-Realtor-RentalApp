package com.example.erealtorapp.AddManagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserAccountsManagement.UserSignupActivity;
import com.example.erealtorapp.databinding.ActivityPostAddBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PostAddActivity extends AppCompatActivity {

    ActivityPostAddBinding Bind;
    List<Uri> images;
    List<String> BmpImageStrings;
    List<Bitmap> bmpImages;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Bitmap bmp;
    DatabaseReference myRef;
    String timeStamp;
    int CurrentImageIndex = 0;
    int totalnumberofimages = 0;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        Bind = ActivityPostAddBinding.inflate(getLayoutInflater());
        setContentView(Bind.getRoot());
         //line to hide actionbar
         getSupportActionBar().hide();
         //line to hide status bar
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        images = new ArrayList<Uri>();
        bmpImages = new ArrayList<Bitmap>();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myRef = database.getReference("Ads");

    }
    public void UploadImageFunction(View view)
    {
        ImagePicker.with(PostAddActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
//        Bind.PropertyImageView.buildDrawingCache();
//        bmp = Bind.button.getDrawingCache();
//        if(bmp!= null)
//            Log.d("Tag","Null reference Continues");
//            bmpImages.add(bmp);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        Bind.PropertyImageView.setImageURI(uri);
        totalnumberofimages = totalnumberofimages + 1;
        CurrentImageIndex = totalnumberofimages -1;
        images.add(uri);
        try {
            bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver() , uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bmpImages.add(bmp);
    }
    public String BitmaptoString(Bitmap bitmap)
    {
        if(bitmap == null)
        {
            Log.d("Tag","Failed Initialization");
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return result;
    }
    public List<String> BitmapListtoStringList(List<Bitmap> bmp)
    {
        List<String> imgstr = new ArrayList<String>();
        Log.d("Tag","BMPSIZE: "+Integer.toString(bmpImages.size()));
        for(int i = 0; i< bmp.size();i++)
        {

            imgstr.add(BitmaptoString(bmp.get(i)));
        }
        return imgstr;
    }

    public void PostAddFunction(View view)
    {
        Log.d("Tag","Hello: "+ Integer.toString(bmpImages.size()));
        for(int i =0; i <bmpImages.size();i++)
        {
            if(bmpImages.get(i)==null)
            {
                Log.d("Tag","Bmpfail");
            }
        }
        BmpImageStrings = new ArrayList<String>();
        for(int i =0; i<bmpImages.size();i++)
        {
            BmpImageStrings.add(BitmaptoString(bmpImages.get(i)));
        }
//        BmpImageStrings = BitmapListtoStringList(bmpImages);
        timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        AddDataClass data = new AddDataClass(Bind.PropertyTitleText.getEditText().getText().toString().trim(),Bind.PropertyAddresText.getEditText().getText().toString().trim()
                ,Integer.parseInt(Bind.NoOfBedroomsText.getEditText().getText().toString().trim()),Integer.parseInt(Bind.RentAmountText.getEditText().getText().toString().trim())
                ,Integer.parseInt(Bind.SizeOfPlotText.getEditText().getText().toString().trim()),auth.getUid().toString(),Boolean.FALSE, BmpImageStrings);
        myRef.child(timeStamp).setValue(data);
        Toast.makeText(PostAddActivity.this,"Ad Created Successfully!",Toast.LENGTH_SHORT).show();
        finish();

    }
    public void PreviousImageFunction(View view)
    {
        if(CurrentImageIndex > 0)
        {
            Log.d("Tag","back");
            CurrentImageIndex = CurrentImageIndex -1;
        }
        Bind.PropertyImageView.setImageURI(images.get(CurrentImageIndex));
    }
    public void NextImageFunction(View view)
    {
        if(CurrentImageIndex < totalnumberofimages-1)
        {
            Log.d("Tag","forward");
            CurrentImageIndex = CurrentImageIndex +1;
        }
            Bind.PropertyImageView.setImageURI(images.get(CurrentImageIndex));
    }

}