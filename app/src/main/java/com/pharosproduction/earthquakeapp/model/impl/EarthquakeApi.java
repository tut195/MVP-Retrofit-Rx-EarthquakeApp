package com.pharosproduction.earthquakeapp.model.impl;

import com.pharosproduction.earthquakeapp.model.IEarthquakeApi;
import com.pharosproduction.earthquakeapp.model.IEarthquakeService;
import com.pharosproduction.earthquakeapp.model.entity.EarthquakeData;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class EarthquakeApi implements IEarthquakeApi {

  @Override
  public Observable<EarthquakeData> getEarthquakes() {
    //Don't forget to add Adapters for RxJava and GsonConverter
    Retrofit retrofit = new Retrofit.Builder()
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://earthquake.usgs.gov/fdsnws/event/1/")
        .build();

    IEarthquakeService weatherService = retrofit.create(IEarthquakeService.class);

    return weatherService.getEarthquakeData();
  }
}
