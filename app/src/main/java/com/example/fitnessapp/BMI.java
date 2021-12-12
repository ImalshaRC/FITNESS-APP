package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BMI extends AppCompatActivity{

    private EditText height;
    private EditText weight;

    private TextView result;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    String _HEIGHT,_WEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    _HEIGHT = userProfile.height;
                    _WEIGHT = userProfile.weight;

                    height.setText(_HEIGHT);
                    weight.setText(_WEIGHT);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BMI.this, "Something Wrong Happend!" ,Toast.LENGTH_LONG).show();
            }
        });


    }

    public void calculateBMI(View v) {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();


            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);

            float bmi = weightValue / (heightValue * heightValue);
            displayBMI(bmi);

    }

    private void displayBMI(float bmi) {
        String bmiLabel = "";

        if (bmi <=18.5){
            bmiLabel = "Under Weight";
        } else if (bmi <=24.9) {
            bmiLabel = "Normal Weight";
        } else if (bmi <=29.9) {
            bmiLabel = "Over Weight";
        } else {
            bmiLabel = "Obesity";
        }

        bmiLabel = "BMI Value: "+String.format("%.2f",bmi) + "\n\n" + "Category: "+bmiLabel;
        result.setText(bmiLabel);
    }

    public void ClickBack(View view){
        NavDrawer.redirectActivity(this, Home.class);
    }


}