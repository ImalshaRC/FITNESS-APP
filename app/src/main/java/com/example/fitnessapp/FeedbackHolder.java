package com.example.fitnessapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackHolder extends RecyclerView.ViewHolder {

    TextView AutoFeed;
    TextView Value;
    TextView Feedback;

    View vv;
    public FeedbackHolder(@NonNull View itemView) {
        super(itemView);

        AutoFeed= itemView.findViewById(R.id.AutoFeedback_single_view);
        Value= itemView.findViewById(R.id.Value_single_view);
        Feedback= itemView.findViewById(R.id.Feedback_single_view);
        vv = itemView;
    }
}
