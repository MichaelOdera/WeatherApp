package com.michael.weatherapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.weatherapp.R;
import com.michael.weatherapp.models.CityWeatherModel;
import com.michael.weatherapp.network.IRetrofit;
import com.michael.weatherapp.network.NetworkConstants;
import com.michael.weatherapp.network.RetrofitService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("NonConstantResourceId")
public class CityDetailActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.temperature) TextView mTemperatureTextView;
    @BindView(R.id.updateProgressBar) ProgressBar mUpdateProgressBar;
    @BindView(R.id.cloudsTextView) TextView mCloudsTextView;
    @BindView(R.id.windsTextView) TextView mWindTextView;
    @BindView(R.id.latitudeTextView) TextView mLatitudeTextView;
    @BindView(R.id.longitudeTextView) TextView mLongitudeTextView;

    @BindView(R.id.humidityTextView) TextView mHumidityTextView;
    @BindView(R.id.minimumTempeartureTextView) TextView mMinimumTemperatureTextView;
    @BindView(R.id.maximumTemperatureTextView) TextView mMaximumTemperatureTextView;
    @BindView(R.id.pressureValueTextView) TextView mPressureValueTextView;
    @BindView(R.id.messageTextView) TextView mMessageTextView;

    @BindView(R.id.sunriseTextView) TextView mSunriseTextView;
    @BindView(R.id.sunsetTextView) TextView mSunsetTextView;

    @BindView(R.id.mainScrollView)
    HorizontalScrollView mMainScrollView;
    @BindView(R.id.mainCardView)
    CardView mMainCardView;
    @BindView(R.id.bottomCardView)CardView mBottomCardView;

    TextView mHeadingCityName;
    private CityWeatherModel mCityWeatherModel;

    FontTextView backArrow;
    String mCentigradeWithSymbol;



    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_app_bar);

        View view = getSupportActionBar().getCustomView();
        backArrow = view.findViewById(R.id.backToCitiesIcon);
        backArrow.setOnClickListener(this);

        mHeadingCityName = view.findViewById(R.id.headingCityName);



        String cityName = getIntent().getStringExtra("cityName");
        mHeadingCityName.setText(cityName);
        makeACallForCityDetail(cityName);

    }

    private void makeACallForCityDetail(String cityName) {
        mUpdateProgressBar.setVisibility(View.VISIBLE);
        IRetrofit client = RetrofitService.getService();
        Call<CityWeatherModel> call = client.getCityWeatherModel(cityName, NetworkConstants.WEATHER_MAP_API_KEY);

        call.enqueue(new Callback<CityWeatherModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<CityWeatherModel> call,@NonNull Response<CityWeatherModel> response) {
                if(response.isSuccessful()){
                    mUpdateProgressBar.setVisibility(View.GONE);
                    mCityWeatherModel = response.body();
                    assert mCityWeatherModel != null;
                    assignTextViewsToModels(mCityWeatherModel);
                    showHiddenViews();

                }

                if(!response.isSuccessful()){

                    mUpdateProgressBar.setVisibility(View.GONE);
                    Toast.makeText(CityDetailActivity.this, "Could not fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CityWeatherModel> call,@NonNull Throwable t) {
                mUpdateProgressBar.setVisibility(View.GONE);

            }
        });
    }

    private void showHiddenViews() {
        mMainScrollView.setVisibility(View.VISIBLE);
        mMainCardView.setVisibility(View.VISIBLE);
        mBottomCardView.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"SimpleDateFormat", "DefaultLocale", "SetTextI18n"})

    private void assignTextViewsToModels(CityWeatherModel mCityWeatherModel) {
        String temperature = mCityWeatherModel.getMain().getTemp().toString();
        changeTemperatureToCentigrade(temperature);

        String clouds = mCityWeatherModel.getClouds().getAll().toString();
        mCloudsTextView.setText(clouds);

        String wind = String.valueOf(mCityWeatherModel.getWind().getSpeed().intValue());
        String windText =  wind+"m/s";
        mWindTextView.setText(windText);

        float latitude = mCityWeatherModel.getCoord().getLat().floatValue();
        String latitudeText = String.format("%.2f", latitude);

        mLatitudeTextView.setText("Lat: "+latitudeText);

        float longitude = mCityWeatherModel.getCoord().getLon().floatValue();
        String longitudeText = String.format("%.2f", longitude);
        mLongitudeTextView.setText("Long: "+longitudeText);

        String humidity = mCityWeatherModel.getMain().getHumidity().toString();
        mHumidityTextView.setText(humidity + "%");

        float minimumTemperature = mCityWeatherModel.getMain().getTempMin().floatValue();
        float floatMinimumTemperature = (float) (minimumTemperature-273.15);

        String mMinimumTemperature = String.format("%.2f", floatMinimumTemperature);
        mMinimumTemperatureTextView.setText("Min: "+mMinimumTemperature);


        float maximumTemperature = mCityWeatherModel.getMain().getTempMin().floatValue();
        float floatMaximumTemperature = (float) (maximumTemperature-273.15);

        String mMaximumTemperature = String.format("%.2f", floatMaximumTemperature);
        mMaximumTemperatureTextView.setText("Max: "+mMaximumTemperature);


        int pressure = mCityWeatherModel.getMain().getPressure();
        String pressureText = String.valueOf(pressure);
        mPressureValueTextView.setText(pressureText+"hPa");


        int sunset = mCityWeatherModel.getSys().getSunset();
        int timezone = mCityWeatherModel.getTimezone();

        int sunrise = mCityWeatherModel.getSys().getSunrise();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Instant instant = Instant.ofEpochSecond(timezone + sunset);


            mSunsetTextView.setText(instant.toString().replace("T", " ").replace("Z", " "));

            Instant sunriseInstant = Instant.ofEpochSecond(sunrise  - timezone);

            mSunriseTextView.setText(sunriseInstant.toString().replace("T", " ").replace("Z", " "));

            String messageTitle = mCityWeatherModel.getWeather().get(0).getMain();
            String messageBody = mCityWeatherModel.getWeather().get(0).getDescription();

            mMessageTextView.setText(messageTitle+" : "+messageBody);
        }

    }


    @SuppressLint("DefaultLocale")
    private void changeTemperatureToCentigrade(String minimumTemperature) {
        float temperature = Float.parseFloat(minimumTemperature);
        float centigrade = (float) (temperature-273.15);
        String mCentigrade = String.format("%.2f",centigrade);
        mCentigradeWithSymbol = mCentigrade+"\u2103";
        mTemperatureTextView.setText(mCentigradeWithSymbol);

    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        String cityName = getIntent().getStringExtra("cityName");
        makeACallForCityDetail(cityName);

    }


    @Override
    public void onClick(View v) {
        if(v == backArrow) {
            Intent intent = new Intent(CityDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }
}