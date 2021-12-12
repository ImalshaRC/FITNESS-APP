package com.example.fitnessapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderMeal extends RecyclerView.ViewHolder {
    TextView PlanName;

    View v;
    public MyViewHolderMeal(@NonNull View itemView) {
        super(itemView);

        PlanName= itemView.findViewById(R.id.PlanName_single_view);

        v = itemView;
    }
}
