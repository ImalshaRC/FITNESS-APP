package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    TextView tvFeedback, errorFeedback;
    TextView tvValue;
    RatingBar tbStars;
    private EditText feedback;
    private Button btnSubmit;


    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        tvFeedback = findViewById(R.id.txfeedback);
        tvValue = findViewById(R.id.txValue);
        tbStars = findViewById(R.id.rbStarts);
        feedback = findViewById(R.id.feedback);
        btnSubmit = findViewById(R.id.btnsend);
        errorFeedback = findViewById(R.id.errorFeed);

        ref = FirebaseDatabase.getInstance().getReference().child("feedback");

        tbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0 || rating==0.5){
                    tvFeedback.setText("Very Disappointed");
                    tvValue.setText("0");
                }
                else if(rating==1 || rating==1.5){
                    tvFeedback.setText("Disappointed");
                    tvValue.setText("1");
                }
                else if(rating==2 || rating==2.5){
                    tvFeedback.setText("OK");
                    tvValue.setText("2");
                }
                else if(rating==3 || rating==3.5){
                    tvFeedback.setText("Satisfied");
                    tvValue.setText("3");
                }
                else if(rating==4 || rating==4.5){
                    tvFeedback.setText("Good");
                    tvValue.setText("4");
                }
                else{
                    tvFeedback.setText("Better");
                    tvValue.setText("5");
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Feedback = feedback.getText().toString();
                final String autoFeed = tvFeedback.getText().toString();
                final String Rate= tvValue.getText().toString();

                if(Feedback.length() == 0){
                    errorFeedback.setText("Please add your feedback");
                }else{
                    errorFeedback.setText("");
                    uploadFeedback( Feedback, autoFeed, Rate );
                }
            }
        });
    }

    private void uploadFeedback(final String Feedback, final String autoFeed, final String Rate) {

        final String key=ref.push().getKey();

            HashMap hashMap = new HashMap();
            hashMap.put("Feedback",Feedback);
            hashMap.put("AutoFeed",autoFeed);
            hashMap.put("Value",Rate);

            ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    startActivity(new Intent(getApplicationContext(),All_feedback.class));
                    Toast.makeText(Feedback.this, "Feedback successfully added", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}