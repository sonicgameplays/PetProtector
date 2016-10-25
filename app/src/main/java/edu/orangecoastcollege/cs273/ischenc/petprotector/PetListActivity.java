package edu.orangecoastcollege.cs273.ischenc.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;

    // this member variable stores the URI to whatever image has been selected
    // the default is none.png R.drawable.none
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);

        //constructs full URI to any android resource
        imageUri = getUriToResource(this, R.drawable.none);

        // set image uri of image view in code
        petImageView.setImageURI(imageUri);


    }


    public void selectPetImage(View view)
    {
        // store an array list of permission strings
        ArrayList<String> permList = new ArrayList<String>();

        // start
        // by seeing if we have permission to the camera
        int cameraPermission = ContextCompat.
                checkSelfPermission(this, Manifest.permission.CAMERA);

        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        int writePermission = ContextCompat.
                checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int readPermission = ContextCompat.
                checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (readPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permList.size() > 0)
        {
            //convert ArrayList to array of strings
            String[] perms = new String[permList.size()];
            //request permission for the user
            ActivityCompat.requestPermissions(this, permList.toArray(perms), 0);
        }




    }

    public static Uri getUriToResource
            (@NonNull Context context,
             @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId) +
                '/' + res.getResourceTypeName(resId) +
                '/' + res.getResourceEntryName(resId));
    }
}
