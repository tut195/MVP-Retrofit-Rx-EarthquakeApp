package com.pharosproduction.earthquakeapp.view;

import com.pharosproduction.earthquakeapp.model.entity.Feature;
import java.util.List;

public interface IMainView {

  void setEarthquakesListViewData(List<Feature> earthquakes);
  void updateEarthquakesListView(List<Feature> earthquakes);
  void setEmptyResponseText(String text);
  void hideLoadingIndicator();
  void showNoConnectionMessage();

}
