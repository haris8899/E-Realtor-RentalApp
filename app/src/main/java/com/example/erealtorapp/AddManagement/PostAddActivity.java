package com.example.erealtorapp.AddManagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.erealtorapp.R;
import com.example.erealtorapp.UserAccountsManagement.UserSignupActivity;
import com.example.erealtorapp.databinding.ActivityPostAddBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PostAddActivity extends AppCompatActivity {

    ActivityPostAddBinding Bind;
    List<Uri> images;
    int CurrentImageIndex = 0;
    int totalnumberofimages = 0;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        Bind = ActivityPostAddBinding.inflate(getLayoutInflater());
        setContentView(Bind.getRoot());
        images = new ArrayList<Uri>();

    }
    public void UploadImageFunction(View view)
    {
        ImagePicker.with(PostAddActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        Bind.PropertyImageView.setImageURI(uri);
        images.add(uri);
        totalnumberofimages = totalnumberofimages + 1;
        CurrentImageIndex = totalnumberofimages -1;
    }
    public String BitmaptoString(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return result;
    }
    public void PostAddFunction(View view)
    {
        Log.d("Tag","Hello");
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