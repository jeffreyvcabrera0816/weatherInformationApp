package ph.com.jeffreyvcabrera.weatherinformation.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import ph.com.jeffreyvcabrera.weatherinformation.interfaces.AsyncTaskListener;

/**
 * Created by Jeffrey on 7/11/2017.
 */

public class API {

    final String TAG = "response";
    AsyncTaskListener callback;
    Context context;
    String url;

    public API(final Context context, AsyncTaskListener cb, String url) {
        this.callback = cb;
        this.context = context;
        this.url = url;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        callback.onTaskComplete(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onTaskComplete("error");
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to the Internet. Please check your connection.";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again later.";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to the Internet. Please check your connection.";
                } else if (volleyError instanceof ParseError) {
                    message = "Connection Error. Please try again later.";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to the Internet. Please check your connection.";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut. Please check your internet connection.";
                }

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}

