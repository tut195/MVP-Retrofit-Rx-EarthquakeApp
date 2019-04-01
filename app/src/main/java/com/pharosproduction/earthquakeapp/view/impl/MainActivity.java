package com.pharosproduction.earthquakeapp.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.pharosproduction.earthquakeapp.presenter.IMainPresenter;
import com.pharosproduction.earthquakeapp.R;
import com.pharosproduction.earthquakeapp.adapter.EarthquakesAdapter;
import com.pharosproduction.earthquakeapp.model.entity.Feature;
import com.pharosproduction.earthquakeapp.presenter.impl.MainPresenter;
import com.pharosproduction.earthquakeapp.view.IMainView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView {
  private IMainPresenter _presenter;

  private List<Feature> _earthquakes;

  private ListView _earthquakesListView;
  private TextView _oopsTextView;
  private EarthquakesAdapter _adapter;
  private ProgressBar _loadingIndicator;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    _earthquakesListView = (ListView) findViewById(R.id.earthquakes_list_view);

    _oopsTextView = (TextView) findViewById(R.id.oopsTextView);
    _earthquakesListView.setEmptyView(_oopsTextView);

    _loadingIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
    _loadingIndicator.getIndeterminateDrawable().setColorFilter(ContextCompat
        .getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);

    ConnectivityManager connMgr = (ConnectivityManager)
        getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    if (networkInfo != null && networkInfo.isConnected()) {
      if (_presenter == null) _presenter = new MainPresenter(this);

      _presenter.getEarthquakesData(false);

      _earthquakesListView.setOnItemClickListener((adapterView, view, i, l) -> {
        String url = _earthquakes.get(i).getProperties().getUrl();
        Intent browseIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        startActivity(browseIntent);
      });
    } else {
      showNoConnectionMessage();
    }


  }

  @Override
  public void setEarthquakesListViewData(List<Feature> earthquakes) {
    _earthquakes = earthquakes;
    _adapter = new EarthquakesAdapter(getApplicationContext(), _earthquakes);
    _earthquakesListView.setAdapter(_adapter);
  }

  @Override
  public void updateEarthquakesListView(List<Feature> earthquakes) {
    _earthquakes = earthquakes;
    _adapter.notifyDataSetChanged();
  }

  @Override
  public void setEmptyResponseText(String text) {
    _oopsTextView.setText(text);
  }

  @Override
  public void hideLoadingIndicator() {
    _loadingIndicator.setVisibility(View.GONE);
  }

  @Override
  public void showNoConnectionMessage() {
    _loadingIndicator.setVisibility(View.GONE);
    _oopsTextView.setText(R.string.no_internet_connection);
  }
}