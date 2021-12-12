package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

public class ViewActivity extends AppCompatActivity {

    private ImageView imageView;
    EditText planName;
    EditText timePeriod;

    EditText breakfast;
    EditText lunch;
    EditText tea;
    EditText dinner;
    TextView errorMeal;

    ImageButton btnDelete;
    Button btnUpdate;
    Button meals_save;
    Button meals_update;
    ImageButton btnMore, btnMore1, btnMore2, btnMore3, imageBtnMeal;
    Car car;

    DatabaseReference ref, DataRef, mealRef;
    StorageReference StorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        String CarKey = getIntent().getStringExtra("CarKey");

        imageView = findViewById(R.id.image_single_view_activity);
        planName = findViewById(R.id.PlanName_single_view_Activity);
        timePeriod = findViewById(R.id.TimePeriod_single_view_Activity);
        errorMeal = findViewById(R.id.errorMeal);

        breakfast = findViewById(R.id.Breakfast);
        lunch = findViewById(R.id.Lunch);
        tea = findViewById(R.id.Tea);
        dinner = findViewById(R.id.Dinner);
        meals_save = findViewById(R.id.meals_save);
        meals_update = findViewById(R.id.mealsUpdate);
        btnDelete = findViewById(R.id.imageButton1);
        btnMore = findViewById(R.id.imageButtonMore);
        btnMore1 = findViewById(R.id.imageButtonMore2);
        btnMore2 = findViewById(R.id.imageButtonMore3);
        btnMore3 = findViewById(R.id.imageButtonMore4);
        imageBtnMeal = findViewById(R.id.imageBtnMeal);

        mealRef = FirebaseDatabase.getInstance().getReference().child("meals");
        DataRef = FirebaseDatabase.getInstance().getReference().child("car").child(CarKey);
        StorageRef = FirebaseStorage.getInstance().getReference().child("carImage").child(CarKey+".jpg");

        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String PlanName = snapshot.child("PlanName").getValue().toString();
                    String TimePeriod = snapshot.child("TimePeriod").getValue().toString();
                    String ImageUrl = snapshot.child("ImageUrl").getValue().toString();

                    Picasso.get().load(ImageUrl).into(imageView);
                    planName.setText(PlanName);
                    timePeriod.setText(TimePeriod);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get user data
        mealRef.child(CarKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    if(dataSnapshot.child("Breakfast").getValue() != null){
                        breakfast.setText(dataSnapshot.child("Breakfast").getValue().toString());
                    }
                    if(dataSnapshot.child("Lunch").getValue() != null){
                        lunch.setText(dataSnapshot.child("Lunch").getValue().toString());
                    }
                    if(dataSnapshot.child("Tea").getValue() != null){
                        tea.setText(dataSnapshot.child("Tea").getValue().toString());
                    }
                    if(dataSnapshot.child("Dinner").getValue() != null){
                        dinner.setText(dataSnapshot.child("Dinner").getValue().toString());
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Please add meal details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //update button
        meals_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Breakfast = breakfast.getText().toString();
                final String Lunch = lunch.getText().toString();
                final String Tea= tea.getText().toString();
                final String Dinner = dinner.getText().toString();

                if(Breakfast.length() == 0){
                    errorMeal.setText("Please add Breakfast item.");
                }else if(Lunch.length() == 0){
                    errorMeal.setText("Please add Lunch item.");
                }else if(Tea.length() == 0){
                    errorMeal.setText("Please add Tea item.");
                }else if(Dinner.length() == 0){
                    errorMeal.setText("Please add Dinner item.");
                }else{
                    errorMeal.setText("");
                    updateMeals( Breakfast, Lunch, Tea, Dinner );
                }
            }
        });

        //delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(ViewActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                //mealRef.child(CarKey).removeValue();
            }
        });

        //upload button
        meals_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Breakfast = breakfast.getText().toString();
                final String Lunch = lunch.getText().toString();
                final String Tea= tea.getText().toString();
                final String Dinner = dinner.getText().toString();

                if(Breakfast.length() == 0){
                    errorMeal.setText("Please add Breakfast item.");
                }else if(Lunch.length() == 0){
                    errorMeal.setText("Please add Lunch item.");
                }else if(Tea.length() == 0){
                    errorMeal.setText("Please add Tea item.");
                }else if(Dinner.length() == 0){
                    errorMeal.setText("Please add Dinner item.");
                }else{
                    errorMeal.setText("");
                    uploadMeals( Breakfast, Lunch, Tea, Dinner );
                }
            }
        });

        //more data buttons
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.favfamilyrecipes.com/breakfast-quesadillas-2/"));
                startActivity(browserIntent);
            }
        });

        btnMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.loveandlemons.com/healthy-lunch-ideas/"));
                startActivity(browserIntent);
            }
        });

        btnMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.delicious.com.au/recipes/collections/gallery/52-recipes-for-the-ultimate-afternoon-tea/7oibmsbf"));
                startActivity(browserIntent);
            }
        });

        btnMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.countryliving.com/food-drinks/g648/quick-easy-dinner-recipes/"));
                startActivity(browserIntent);
            }
        });

        imageBtnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    //upload meals
    private void uploadMeals(final String Breakfast, final String Lunch, final String Tea, final String Dinner) {

        String CarKey = getIntent().getStringExtra("CarKey");
        //final String key1=mealRef.push().getKey();

        HashMap hashMap = new HashMap();
        hashMap.put("Breakfast",Breakfast);
        hashMap.put("Lunch",Lunch);
        hashMap.put("Tea",Tea);
        hashMap.put("Dinner",Dinner);

        mealRef.child(CarKey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                Toast.makeText(ViewActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //update meals
    private void updateMeals(final String Breakfast, final String Lunch, final String Tea, final String Dinner) {

        //final String key1=mealRef.push().getKey();
        String CarKey = getIntent().getStringExtra("CarKey");

        HashMap hashMap = new HashMap();
        hashMap.put("Breakfast",Breakfast);
        hashMap.put("Lunch",Lunch);
        hashMap.put("Tea",Tea);
        hashMap.put("Dinner",Dinner);

        mealRef.child(CarKey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                Toast.makeText(ViewActivity.this, "Data successfully updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}