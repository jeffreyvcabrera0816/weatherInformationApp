package ph.com.jeffreyvcabrera.weatherinformation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ph.com.jeffreyvcabrera.weatherinformation.R;
import ph.com.jeffreyvcabrera.weatherinformation.fragments.RefreshButtonFragment;
import ph.com.jeffreyvcabrera.weatherinformation.fragments.WeatherListFragment;

public class MainActivity extends AppCompatActivity implements RefreshButtonFragment.RefreshListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void refresh() {
        WeatherListFragment weatherListFragment = (WeatherListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTop);
        weatherListFragment.refreshList();
    }
}
