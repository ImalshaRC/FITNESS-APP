package com.example.fitnessapp;

public class FeedbackClass {
    private String AutoFeed;
    private String Value;
    private String Feedback;

    public FeedbackClass(String autoFeed, String value, String feedback) {
        AutoFeed = autoFeed;
        Value = value;
        Feedback = feedback;
    }

    public FeedbackClass() {
    }

    public String getAutoFeed() {
        return AutoFeed;
    }

    public void setAutoFeed(String autoFeed) {
        AutoFeed = autoFeed;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }
}
