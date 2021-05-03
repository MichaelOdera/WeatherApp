package com.michael.weatherapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.michael.weatherapp.R;
import com.michael.weatherapp.models.CityWeatherModel;
import com.michael.weatherapp.network.IRetrofit;
import com.michael.weatherapp.network.NetworkConstants;
import com.michael.weatherapp.network.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@SuppressLint("NonConstantResourceId")
public class CityDetailActivity extends AppCompatActivity {

    @BindView(R.id.temperature)
    TextView mTemperatureTextView;
    private CityWeatherModel mCityWeatherModel;
    private ProgressDialog mProgressDialog;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_app_bar);



        mProgressDialog = new ProgressDialog(this);

        String cityName = getIntent().getStringExtra("cityName");
        makeACallForCityDetail(cityName);




    }

    private void makeACallForCityDetail(String cityName) {
        mProgressDialog.show();
        IRetrofit client = RetrofitService.getService();
        Call<CityWeatherModel> call = client.getCityWeatherModel(cityName, NetworkConstants.WEATHER_MAP_API_KEY);

        call.enqueue(new Callback<CityWeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<CityWeatherModel> call,@NonNull Response<CityWeatherModel> response) {
                if(response.isSuccessful()){
                    mProgressDialog.dismiss();
                    Toast.makeText(CityDetailActivity.this, "Successfully Fetched Data", Toast.LENGTH_SHORT).show();
                    mCityWeatherModel = response.body();
                    String minimumTemperature = mCityWeatherModel.getMain().getTempMin().toString();
                    mTemperatureTextView.setText(minimumTemperature);
                }

                if(!response.isSuccessful()){
                    mProgressDialog.dismiss();
                    Toast.makeText(CityDetailActivity.this, "Could not fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityWeatherModel> call,@NonNull Throwable t) {
                mProgressDialog.dismiss();

            }
        });
    }


}