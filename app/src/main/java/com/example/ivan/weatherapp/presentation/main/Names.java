package com.example.ivan.weatherapp.presentation.main;

/**
 * Created by ivan
 */

public class Names {

    String name;
    int age;
    long birt;



    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getBirt() {
        return birt;
    }


    @Override
    public String toString() {
        return "name : " + name + " / Age : " + age + " / birt : " + birt;
    }

}
