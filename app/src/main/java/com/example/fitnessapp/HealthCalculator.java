package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HealthCalculator extends AppCompatActivity {

    TextView txtDiabetes, txtCholesterol, txtBloodPressure, resDiabetes, resCholesterol, resBloodPressure;
    Button calculate;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_calculator);
        txtDiabetes = findViewById(R.id.txtDiabetes);
        txtCholesterol = findViewById(R.id.txtCholesterol);
        txtBloodPressure = findViewById(R.id.txtBloodPressure);
        calculate = findViewById(R.id.calcHealth);
        resDiabetes = findViewById(R.id.resDiabetes);
        resCholesterol = findViewById(R.id.resCholesterol);
        resBloodPressure = findViewById(R.id.resBloodPressure);
        layout = findViewById(R.id.displayHealthRec);

        layout.setVisibility(View.GONE);

        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CalculateAnswer();
            }

        });

    }

    public void HealthCalBack(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void CalculateAnswer() {

        HealthCalc healthCalc = new HealthCalc();

        String cholesterolLevel = txtCholesterol.getText().toString();
        String diabetesLevel = txtDiabetes.getText().toString();
        String bloodpresLevel = txtBloodPressure.getText().toString();

        if(TextUtils.isEmpty(cholesterolLevel) || TextUtils.isEmpty(diabetesLevel) || TextUtils.isEmpty(bloodpresLevel)) {
            Toast.makeText(this, "Please complete the fields",
                    Toast.LENGTH_LONG).show();
        } else {

            Float cholLvl = Float.parseFloat(cholesterolLevel);
            Float dibLvl = Float.parseFloat(diabetesLevel);
            Float bpLvl = Float.parseFloat(bloodpresLevel);

            String chResult = healthCalc.testCholesterol(cholLvl);
            String bpResult = healthCalc.testBloodPressure(bpLvl);
            String dbResult = healthCalc.testDiabetes(dibLvl);

            layout.setVisibility(View.VISIBLE);
            resCholesterol.setText(chResult);
            resBloodPressure.setText(bpResult);
            resDiabetes.setText(dbResult);

        }

    }
}