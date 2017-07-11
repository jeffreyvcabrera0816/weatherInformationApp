package ph.com.jeffreyvcabrera.weatherinformation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.weatherinformation.R;
import ph.com.jeffreyvcabrera.weatherinformation.adapters.WeatherDetailsAdapter;
import ph.com.jeffreyvcabrera.weatherinformation.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.weatherinformation.models.WeatherDetailModel;
import ph.com.jeffreyvcabrera.weatherinformation.utils.AsyncAPI;

public class WeatherDetails extends AppCompatActivity implements AsyncTaskListener {

    ListView listView;
    TextView temperature, weather, location;
    Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_weather_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        init();
    }

    public void initView() {

        listView = (ListView) findViewById(R.id.listWeather);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.detail_weather_header, listView,false);
        ViewGroup footerView = (ViewGroup)getLayoutInflater().inflate(R.layout.refresh_button_fragment, listView,false);
        temperature = (TextView) headerView.findViewById(R.id.det_temperature);
        location = (TextView) headerView.findViewById(R.id.det_location);
        weather = (TextView) headerView.findViewById(R.id.det_weather);
        refreshBtn = (Button) footerView.findViewById(R.id.refresh_btn);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

    }

    public void init() {
        Integer city_id = getIntent().getExtras().getInt("city_id");
        new AsyncAPI(this, this, false).execute("GET", "/data/2.5/weather?id="+city_id+"&units=metric&APPID=c3dfdfe8fa6cc6c56be9829b96352efc");
    }

    @Override
    public void onTaskComplete(String result) {

        final ArrayList<WeatherDetailModel> weatherDetailModel = new ArrayList<>();
        try {
            JSONObject jObj = new JSONObject(result);
            JSONArray weatherArr = jObj.getJSONArray("weather");
            String loc = jObj.getString("name");
            JSONObject main = jObj.getJSONObject("main");
            String temp = main.getString("temp");
            location.setText("Location: "+loc);
            temperature.setText("Temperature: "+temp);

            for(int x=0; x<weatherArr.length();x++){

                JSONObject a = weatherArr.getJSONObject(x);
                WeatherDetailModel wd = new WeatherDetailModel();
                wd.setWeather("Weather: "+a.getString("description"));
                wd.setIcon(a.getString("icon"));

                weatherDetailModel.add(wd);

            }

            WeatherDetailsAdapter weatherDetailsAdapter = new WeatherDetailsAdapter(this, weatherDetailModel);
            listView.setAdapter(weatherDetailsAdapter);



        } catch (JSONException e) {
            Log.e("error", "Error parsing data" + e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void refresh() {
        init();
    }
}
