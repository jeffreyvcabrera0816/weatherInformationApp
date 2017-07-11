package ph.com.jeffreyvcabrera.weatherinformation.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.weatherinformation.R;
import ph.com.jeffreyvcabrera.weatherinformation.activities.WeatherDetails;
import ph.com.jeffreyvcabrera.weatherinformation.models.WeatherInfoModel;

/**
 * Created by Jeffrey on 7/11/2017.
 */

public class WeatherListAdapter extends BaseAdapter {

    Context c;
    ArrayList<WeatherInfoModel> weatherInfoModel;
    private LayoutInflater inflater;

    public WeatherListAdapter(Context c, ArrayList<WeatherInfoModel> weatherInfoModel) {
        this.c = c;
        this.weatherInfoModel = weatherInfoModel;

    }

    @Override
    public int getCount() {
        return weatherInfoModel.size();
    }

    @Override
    public Object getItem(int i) {
        return weatherInfoModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v;
        if (view == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.fragment_weather_list_row, viewGroup, false);
        } else {
            v = view;
        }

        LinearLayout row = (LinearLayout) v.findViewById(R.id.item_row);
        TextView location = (TextView) v.findViewById(R.id.tv_location);
        TextView weather = (TextView) v.findViewById(R.id.tv_weather);
        TextView temperature = (TextView) v.findViewById(R.id.tv_temperature);

        location.setText(weatherInfoModel.get(i).getLocation());
        weather.setText(weatherInfoModel.get(i).getWeather());
        temperature.setText(weatherInfoModel.get(i).getTemperature());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, WeatherDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("city_id", weatherInfoModel.get(i).getCity_id());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        return v;
    }

}
