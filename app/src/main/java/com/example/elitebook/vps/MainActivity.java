package com.example.elitebook.vps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
/*import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.VPS;*/

public class MainActivity extends AppCompatActivity {

    private Button mUploadBtn;
    private ImageView mImageView;

    private FirebaseStorage Storage;

    private StorageReference mStorageRef;
    private ProgressDialog mProgress;

    private static final int CAMERA_REQUEST_CODE = 1;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);


        mImageView = (ImageView) findViewById(R.id.imageView);
        mUploadBtn = (Button) findViewById(R.id.upload);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){

            mProgress.setMessage("Uploading Image");
            mProgress.show();

            Uri uri  =  data.getData();

            StorageReference filepath = mStorageRef.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();

                    Toast.makeText(MainActivity.this, "Uploading Finished", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
