package com.example.fitnessapp;

public class HealthCalc {

    public String testCholesterol(float cholLvl) {

        if(cholLvl < 0) {
            return ("Invalid");
        } else if(0<=cholLvl && cholLvl < 200) {
            return ("Normal");
        } else if(200<= cholLvl && cholLvl < 240) {
            return ("Attention");
        } else {
            return ("Higher");
        }

    }

    public String testBloodPressure(float bpLvl) {

        if(bpLvl < 0) {
            return ("Invalid");
        } else if(0<=bpLvl && bpLvl < 80) {
            return ("Normal");
        } else if(80<= bpLvl && bpLvl < 90) {
            return ("Attention");
        } else {
            return ("Higher");
        }

    }

    public String testDiabetes(float dibLvl) {

        if(dibLvl < 0) {
            return ("Invalid");
        } else if(0<=dibLvl && dibLvl < 100) {
            return ("Normal");
        } else if(100<= dibLvl && dibLvl < 125) {
            return ("Attention");
        } else {
            return ("Higher");
        }

    }

}
