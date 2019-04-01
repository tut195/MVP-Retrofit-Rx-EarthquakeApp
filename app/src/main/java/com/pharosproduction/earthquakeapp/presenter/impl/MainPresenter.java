package com.pharosproduction.earthquakeapp.presenter.impl;


import com.pharosproduction.earthquakeapp.model.entity.EarthquakeData;
import com.pharosproduction.earthquakeapp.model.entity.Feature;
import com.pharosproduction.earthquakeapp.model.impl.EarthquakeApi;
import com.pharosproduction.earthquakeapp.presenter.IMainPresenter;
import com.pharosproduction.earthquakeapp.view.IMainView;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Volt on 09.11.2016.
 */

public class MainPresenter implements IMainPresenter {

  private final EarthquakeApi _earthquakeApi;
  private final IMainView _view;

  public MainPresenter(IMainView view) {
    _earthquakeApi = new EarthquakeApi();
    _view = view;
  }

  @Override
  public void getEarthquakesData(boolean isUpdate) {
    //Get Observable from our Model layer
    Observable<EarthquakeData> dataObservable = _earthquakeApi.getEarthquakes();

    //Really cool thing in RxAndroid is Schedulers. It helps us execute Network requests in new thread (subscribeOn)
    //and update our widgets in main thread with new data (observeOn)
    dataObservable
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(earthquakeData ->
        {
          List<Feature> earthquakes = new ArrayList<>();
          earthquakes.addAll(earthquakeData.getFeatures());

          _view.hideLoadingIndicator();

          if (earthquakes.isEmpty()) {
            _view.setEmptyResponseText("There is no earthquakes");
          } else if (isUpdate) {
            _view.updateEarthquakesListView(earthquakes);
          } else {
            _view.setEarthquakesListViewData(earthquakes);
          }
        });
  }
}
