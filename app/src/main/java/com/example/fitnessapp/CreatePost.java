package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CreatePost extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageView;
    private EditText description;
    private ProgressBar progressBar;
    private Button btnUpload;
    private TextView textViewProgress;
    Uri imageUri;
    boolean isImageAdded = false;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        imageView = findViewById(R.id.createPostImg_CP);
        description = findViewById(R.id.description_CP);
        progressBar = findViewById(R.id.progressBar_CP);
        btnUpload = findViewById(R.id.createPost_CP);
        textViewProgress = findViewById(R.id.progressText_CP);

        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("newsfeed");
        storageReference = FirebaseStorage.getInstance().getReference().child("NewsfeedImages");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //Get User Information
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    fullName = userProfile.fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreatePost.this, "Something Wrong Happend!" ,Toast.LENGTH_LONG).show();
            }

        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String descript = description.getText().toString();
                if(isImageAdded != false && descript.length()>0 && descript.length()<=150) {
                    uploadImage(descript);
                } else {

                    if(isImageAdded == false) {
                        Toast.makeText(CreatePost.this, "Please Add An Image!" ,Toast.LENGTH_LONG).show();
                    }

                    if(descript.length()==0) {
                        description.setError("Description is required");
                        description.requestFocus();
                    }

                    if(descript.length()>150) {
                        description.setError("Letter limit is Exceeded");
                        description.requestFocus();
                    }

                }
            }
        });

    }

    private void uploadImage(String description) {
        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        final String key = databaseReference.push().getKey();
        storageReference.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap= new HashMap();
                        hashMap.put("description", description);
                        hashMap.put("postImage", uri.toString());
                        hashMap.put("username", fullName);
                        hashMap.put("userID", userID);
                        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CreatePost.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }

                        });
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress + "%");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_IMAGE && data!=null) {
            imageUri = data.getData();
            isImageAdded = true;
            imageView.setImageURI(imageUri);
        }
    }

    public void BacktoMyPosts(View view) {
        Intent intent = new Intent(this, MyPosts.class);
        startActivity(intent);
    }

}