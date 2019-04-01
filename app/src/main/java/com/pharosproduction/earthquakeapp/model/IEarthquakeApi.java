package com.pharosproduction.earthquakeapp.model;

import com.pharosproduction.earthquakeapp.model.entity.EarthquakeData;
import rx.Observable;


public interface IEarthquakeApi {

  Observable<EarthquakeData> getEarthquakes();
}