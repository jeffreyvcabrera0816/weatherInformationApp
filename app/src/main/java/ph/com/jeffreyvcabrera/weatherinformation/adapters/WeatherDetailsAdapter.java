package ph.com.jeffreyvcabrera.weatherinformation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.weatherinformation.R;
import ph.com.jeffreyvcabrera.weatherinformation.models.WeatherDetailModel;
import ph.com.jeffreyvcabrera.weatherinformation.utils.Settings;

/**
 * Created by Jeffrey on 7/11/2017.
 */

public class WeatherDetailsAdapter extends BaseAdapter {
    Context c;
    ArrayList<WeatherDetailModel> wdm;
    private AQuery aq;

    public WeatherDetailsAdapter(Context c, ArrayList<WeatherDetailModel> wdm) {
        this.c = c;
        this.wdm = wdm;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return wdm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        View v;
        if (convertView == null) {

            LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.weather_detail_row, viewGroup, false);

        } else {
            v = convertView;
        }

        this.aq = new AQuery(v);
        TextView weather = (TextView) v.findViewById(R.id.det_weather);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);

        String imgaq = Settings.base_url + "/img/w/"+wdm.get(i).getIcon()+".png";
        aq.id(icon).image(imgaq, false, true);

        weather.setText(wdm.get(i).getWeather());

        return v;
    }

}
