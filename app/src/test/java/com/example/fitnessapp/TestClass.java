package com.example.fitnessapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestClass {

    private Calculations calculations;

    @BeforeEach
    public void setup(){
        calculations = new Calculations();
    }

    @Test
    public void testCalorie(){
        float result = calculations.CalculateCalorie(20,10,10,10,10);
        assertEquals(60, result);
    }
}
