package ph.com.jeffreyvcabrera.weatherinformation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.weatherinformation.R;
import ph.com.jeffreyvcabrera.weatherinformation.activities.MainActivity;
import ph.com.jeffreyvcabrera.weatherinformation.adapters.WeatherListAdapter;
import ph.com.jeffreyvcabrera.weatherinformation.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.weatherinformation.models.WeatherInfoModel;
import ph.com.jeffreyvcabrera.weatherinformation.utils.API;
import ph.com.jeffreyvcabrera.weatherinformation.utils.AsyncAPI;
import ph.com.jeffreyvcabrera.weatherinformation.utils.Settings;

public class WeatherListFragment extends Fragment implements AsyncTaskListener {

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather_list, container, false);
        listView = (ListView) v.findViewById(R.id.item_list);
        init();

        return v;
    }

    public void init() {
        new AsyncAPI(getActivity(), WeatherListFragment.this, false).execute("GET", "/data/2.5/group?id=2643743,3067696,5391997&units=metric&APPID=c3dfdfe8fa6cc6c56be9829b96352efc");
//        new API(getActivity(), WeatherListFragment.this, Settings.base_url+"/data/2.5/group?id=2643743,3067696,5391997&units=metric&APPID=c3dfdfe8fa6cc6c56be9829b96352efc"); // alternative
    }

    @Override
    public void onTaskComplete(String result) {
        JSONObject jObj = null;
        if (result == "error") {
            Log.d("Error", "error");
        } else {

            final ArrayList<WeatherInfoModel> weatherInfoModel = new ArrayList<>();

            try {
                jObj = new JSONObject(result);
                Integer count = jObj.getInt("cnt");

                if (count > 0) {
                    JSONArray data = jObj.optJSONArray("list");

                    for (int x = 0; x < data.length(); x++) {
                        JSONObject a = data.getJSONObject(x);
                        JSONObject main = a.getJSONObject("main");
                        String location = a.getString("name");
                        Integer city_id = a.getInt("id");
                        JSONArray weather = a.getJSONArray("weather");

                        WeatherInfoModel wm = new WeatherInfoModel();

                        wm.setLocation(location);
                        wm.setCity_id(city_id);
                        wm.setTemperature(main.getString("temp"));
                        for(int w = 0; w < 1; w++) {
                            JSONObject weather_inner = weather.getJSONObject(w);
                            wm.setWeather(weather_inner.getString("description"));
                        }

                        weatherInfoModel.add(wm);
                    }

                    WeatherListAdapter weatherListAdapter = new WeatherListAdapter(getActivity(), weatherInfoModel);
                    listView.setAdapter(weatherListAdapter);
                    weatherListAdapter.notifyDataSetChanged();
                    listView.invalidateViews();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

                } else {

                    String message = jObj.getString("error");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void refreshList() {
        init();
    }



}
