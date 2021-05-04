package com.michael.weatherapp.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.michael.weatherapp.R;
import com.michael.weatherapp.adapters.CitiesListAdapter;
import com.michael.weatherapp.citylistdatasource.CityList;
import com.michael.weatherapp.models.Main;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> fetchedCitiesListArray;
    CityList mCityList;

    @BindView(R.id.citiesRecyclerView) RecyclerView mCitiesRecyclerView;
    private CitiesListAdapter mCitiesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mCityList = new CityList();
        fetchedCitiesListArray = mCityList.getCityList();
        //System.out.println("My fetched cities list ______ +++++ _______" +fetchedCitiesListArray.toString());

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.main_activity_appbar);






        View view = getSupportActionBar().getCustomView();

        inflateRecyclerView();

    }

    private void inflateRecyclerView() {
        LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mCitiesListAdapter = new CitiesListAdapter(fetchedCitiesListArray, MainActivity.this);
        mCitiesRecyclerView.setLayoutManager(layoutManager);
        mCitiesRecyclerView.setAdapter(mCitiesListAdapter);
        mCitiesRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
}