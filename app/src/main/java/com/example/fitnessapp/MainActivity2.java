package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageViewAdd;
    private TextView textViewProgress, errorViewActivity;
    private ProgressBar progressBar;
    private EditText inputPlanName;
    private EditText inputTimePeriod;
    private Button btnUpload;
    ImageButton imageBtnMain;

    Uri imageUri;
    boolean isImageAdded = false;

    DatabaseReference ref;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageViewAdd = findViewById(R.id.imageViewAdd);

        inputPlanName = findViewById(R.id.inputPlanName);
        inputTimePeriod = findViewById(R.id.inputTimePeriod);

        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);
        errorViewActivity = findViewById(R.id.errorViewActivity);

        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        btnUpload = findViewById(R.id.btnUpload);
        imageBtnMain = findViewById(R.id.imageBtnMain2);

        ref = FirebaseDatabase.getInstance().getReference().child("car");
        StorageRef = FirebaseStorage.getInstance().getReference().child("carImage");

        imageViewAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, REQUEST_CODE_IMAGE);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String PlanName =inputPlanName.getText().toString();
                final String TimePeriod =inputTimePeriod.getText().toString();
                //if(isImageAdded!=false && PlanName!=null && TimePeriod!=null){

                //}
                if(!isImageAdded){
                    errorViewActivity.setText("Please add image.");
                }else if(PlanName.length() == 0){
                    errorViewActivity.setText("Please enter plan name.");
                }else if(TimePeriod.length() == 0){
                    errorViewActivity.setText("Please enter time period.");
                }else{
                    errorViewActivity.setText("");
                    uploadPlan( PlanName, TimePeriod);
                }
            }
        });

        imageBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    private void uploadPlan(final String PlanName, final String TimePeriod) {
        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        final String key=ref.push().getKey();
        StorageRef.child(key+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageRef.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("PlanName",PlanName);
                        hashMap.put("TimePeriod",TimePeriod);
                        hashMap.put("ImageUrl",uri.toString());
                        assert key != null;
                        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress + " %");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE && data!=null){
            imageUri = data.getData();
            isImageAdded=true;
            imageViewAdd.setImageURI(imageUri);
        }
    }
}