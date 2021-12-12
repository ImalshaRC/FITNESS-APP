package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CalorieCal extends AppCompatActivity {

    private Spinner spinner;
    Button calorieCal;
    EditText year, feet, weight;
    TextView amount, errorCalorie;
    RadioButton male, feMale;
    ImageButton imageBtnCalorie;
    Float calorieAmount, val, val2 = 0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_cal);

        spinner = findViewById(R.id.spinner);
        calorieCal = findViewById(R.id.calorie_cal);
        year = findViewById(R.id.calorie_year);
        feet = findViewById(R.id.calorie_feet);
        weight = findViewById(R.id.calorie_weight);
        amount = findViewById(R.id.calorie_amount);
        errorCalorie = findViewById(R.id.ErrCalorie);
        male = findViewById(R.id.Male);
        feMale = findViewById(R.id.Female);
        imageBtnCalorie = findViewById(R.id.imageBtnCalorie);

        String[] items = {"Inactive", "Somewhat active", "Active", "Very active"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,items);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    val = 10F;
                }else if(i == 1){
                    val = 20F;
                }else if(i == 2){
                    val = 30F;
                }else{
                    val = 40F;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calorieCal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if(year.length() == 0){
                    errorCalorie.setText("Please enter Age");
                }else if(feet.length() == 0){
                    errorCalorie.setText("Please enter Height");
                }else if(weight.length() == 0){
                    errorCalorie.setText("Please enter Weight");
                }else if(!male.isChecked() && !feMale.isChecked()) {
                    errorCalorie.setText("Please select gender");
                }else{
                    errorCalorie.setText("");
                    if(male.isChecked()){
                        val2 = 10F;
                    }else{
                        val2 = 20F;
                    }
                    Float y = Float.parseFloat(year.getText().toString());
                    Float h = Float.parseFloat(feet.getText().toString());
                    Float w = Float.parseFloat(weight.getText().toString());

                    CalculateCalorie(val, val2, y, h, w);
                }
            }
        });

        imageBtnCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void CalculateCalorie(Float val, Float val2, Float y, Float h, Float w){
        calorieAmount = val + val2 + y + h + w;
        amount.setText(calorieAmount.toString());
    }
}