package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    NavigationBarView bottomNavigationView;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView_Newsfeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<NewsfeedModel> options =
                new FirebaseRecyclerOptions.Builder<NewsfeedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("newsfeed"), NewsfeedModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_nav:
                        return true;
                    case R.id.workout_nav:
                        startActivity(new Intent(getApplicationContext(), Workout.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.food_nav:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.me_nav:
                        startActivity(new Intent(getApplicationContext(), Me.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

    }

    //Nav Drawer

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome (View view){ recreate(); }

    public void ClickWorkout (View view){
        redirectActivity(this, Workout.class);
    }

    public void ClickFood (View view){
        redirectActivity(this, HomeActivity.class);
    }

    public void ClickMyProfile (View view){
        redirectActivity(this, Profile.class);
    }

    public void ClickMyPosts (View view){
        redirectActivity(this, MyPosts.class);
    }

    public void ClickBMI (View view){
        redirectActivity(this, BMI.class);
    }

    public void ClickHealthCal (View view){
        redirectActivity(this, HealthCalculator.class);
    }

    public void ClickAboutUs (View view){
        redirectActivity(this, AboutUs.class);
    }

    public void ClickLogOut(View view){
        NavDrawer.logout(this);
    }

    public void StepCounter (View view){ redirectActivity(this,StepCounter.class); }

    public void FeedbackBtn (View view){ redirectActivity(this,All_feedback.class); }

    public void CalorieCal (View view){ redirectActivity(this,CalorieCal.class); }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawer.closeDrawer(drawerLayout);
    }

    public static void redirectActivity(Activity activity,Class aClass) {
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

}