package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewFeedback extends AppCompatActivity {

    EditText value;
    EditText autoFeedback;
    EditText Feedback;
    Button btnDelete;
    Button btnUpdate;
    FeedbackClass feed;
    ImageButton imageBtnFeedReset;
    TextView errorViewFeed;


    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        value = findViewById(R.id.Value_single_view_Activity);
        autoFeedback = findViewById(R.id.Autofeedback_single_view_Activity);
        Feedback = findViewById(R.id.Feedback_single_view_Activity);

        btnDelete = findViewById(R.id.btnDelete_feedback);
        btnUpdate = findViewById(R.id.btnUpdate_feedback);
        imageBtnFeedReset = findViewById(R.id.imageBtnFeedReset);
        errorViewFeed = findViewById(R.id.errorViewFeed);

        ref = FirebaseDatabase.getInstance().getReference().child("feedback");
        String Feedkey = getIntent().getStringExtra("FeedKey");

        //update method for feedback
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Value = value.getText().toString();
                final String AutoFeedback = autoFeedback.getText().toString();
                final String feedback= Feedback.getText().toString();

                if(Value.length() == 0){
                    errorViewFeed.setText("Please add Rate value");
                }else if(Integer.parseInt(Value) > 5){
                    errorViewFeed.setText("Rate value Should Be less than 6");
                }else if(AutoFeedback.length() == 0){
                    errorViewFeed.setText("Please add Auto feedback");
                }else if(feedback.length() == 0){
                    errorViewFeed.setText("Please add Feedback");
                }else{
                    errorViewFeed.setText("");
                    updateFeedback( Value, AutoFeedback, feedback );
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("feedback");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(Feedkey)) {
                            ref = FirebaseDatabase.getInstance().getReference().child("feedback").child(Feedkey);
                            ref.removeValue();
                            //clearControls();

                            Toast.makeText(getApplicationContext(), "Feedback Deleted Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),All_feedback.class));
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source To Display!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        ref.child(Feedkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String rate = snapshot.child("Value").getValue().toString();
                    String autofeedback = snapshot.child("AutoFeed").getValue().toString();
                    String feedBack = snapshot.child("Feedback").getValue().toString();

                    value.setText(rate);
                    autoFeedback.setText(autofeedback);
                    Feedback.setText(feedBack);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageBtnFeedReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), All_feedback.class));
            }
        });
    }

    private void updateFeedback(final String Rate, final String autoFeed, final String Feedback) {

        //final String key=ref.push().getKey();
        String Feedkey = getIntent().getStringExtra("FeedKey");

        HashMap hashMap = new HashMap();
        hashMap.put("Feedback",Feedback);
        hashMap.put("AutoFeed",autoFeed);
        hashMap.put("Value",Rate);

        ref.child(Feedkey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(getApplicationContext(),All_feedback.class));
                Toast.makeText(ViewFeedback.this, "Feedback successfully updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}