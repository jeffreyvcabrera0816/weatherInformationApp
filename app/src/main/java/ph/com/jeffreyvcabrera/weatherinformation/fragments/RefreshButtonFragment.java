package ph.com.jeffreyvcabrera.weatherinformation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ph.com.jeffreyvcabrera.weatherinformation.R;

public class RefreshButtonFragment extends Fragment {

    Button refreshBtn;
    RefreshListener refreshListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refresh_button_fragment, container, false);

        refreshBtn = (Button) v.findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshListener.refresh();
            }
        });
        return v;
    }

    public interface RefreshListener {
        void refresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            refreshListener = (RefreshListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

}
