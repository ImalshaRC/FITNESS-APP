 package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

 public class AboutUs extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout = findViewById(R.id.drawer_layout);
    }


    public void ClickMenu(View view){
        NavDrawer.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        NavDrawer.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        NavDrawer.redirectActivity(this,Home.class);
    }

    public void ClickProfile (View view){
         NavDrawer.redirectActivity(this,Profile.class);
     }

    public void ClickAboutUs (View view){
        NavDrawer.redirectActivity(this,AboutUs.class);
    }

     public void ClickBMI (View view){

         NavDrawer.redirectActivity(this,BMI.class);
     }


     public void ClickLogOut(View view){
        NavDrawer.logout(this);
    }

     @Override
     protected void onPause() {
         super.onPause();
         NavDrawer.closeDrawer(drawerLayout);
     }
 }
