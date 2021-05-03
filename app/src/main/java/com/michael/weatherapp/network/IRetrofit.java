package com.michael.weatherapp.network;

import com.michael.weatherapp.models.CityWeatherModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRetrofit {

    @GET("weather")
    Call<CityWeatherModel> getCityWeatherModel(
            @Query("q") String cityNameQuery,
            @Query("appid") String apiKey
    );



}
