package com.michael.weatherapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {


    public static Retrofit retrofit = null;

    public static IRetrofit getService() {
        if(retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(IRetrofit.class);
    }



}
