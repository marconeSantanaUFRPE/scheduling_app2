package com.example.scheduling_app.Infra;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private  static Retrofit instance;


    public static Retrofit getInstance(){

        if(instance==null) {

            instance = new Retrofit.Builder().baseUrl("https://scheduling-260514.appspot.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    return instance;
    }



}
