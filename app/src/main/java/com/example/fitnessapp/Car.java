package com.example.fitnessapp;

public class Car {
    private String imageUrl;
    private String PlanName;
    private String TimePeriod;

    public Car(String imageUrl, String PlanName, String TimePeriod) {
        this.imageUrl = imageUrl;
        this.PlanName = PlanName;
        this.TimePeriod = TimePeriod;
    }

    public Car() {
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getTimePeriod() {
        return TimePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        TimePeriod = timePeriod;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
