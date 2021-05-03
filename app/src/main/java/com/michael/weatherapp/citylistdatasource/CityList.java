package com.michael.weatherapp.citylistdatasource;

import java.util.ArrayList;
import java.util.Arrays;

public class CityList {


    String[] cities = {"Lisbon", "Berlin", "Paris", "Madrid", "Copenhagen", "Rome", "London", "Dublin", "Prague", "Vienna", "Nairobi"};

    ArrayList<String> citiesArrayList = new ArrayList<>(Arrays.asList(cities));

    public ArrayList<String> getCityList(){
        return  citiesArrayList;
    }
}
