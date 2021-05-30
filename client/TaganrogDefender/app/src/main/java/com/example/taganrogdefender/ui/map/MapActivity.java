package com.example.taganrogdefender.ui.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.taganrogdefender.R;
import com.example.taganrogdefender.request_real;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    String server_ip = "192.168.43.143:3000";
    Runnable run_update_map;
    request_real request = new request_real();
    Spinner spinner_filter;
    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_map);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_places);

        spinner_filter = findViewById(R.id.spinner_filter);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.map_filter));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(myAdapter);

        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if (i == 0) {
                     filter = "все";
                 } else if (i == 1) {
                     filter = "кафе";
                 } else if (i == 2) {
                     filter = "сувениры";
                 } else if (i == 3) {
                     filter = "отели";
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
        });

            Handler handler = new Handler();
        run_update_map = new Runnable() {
            public void run() {
                UpdateMap();
                handler.postDelayed(run_update_map, 600000);
            }
        };
        handler.postDelayed(run_update_map, 0);
    }

    private void UpdateMap() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng latLng = new LatLng(47.22, 38.76);
                MarkerOptions marker = new MarkerOptions()
                        .position(latLng)
                        .visible(false);
                googleMap.addMarker(marker);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                JSONObject json = request.GET("http://"+ server_ip + "/events/all");

                try {
                    JSONArray array = json.getJSONArray("res");
                    for (int i = 0; i < array.length(); i++) {
                        double x = array.getJSONObject(i).getJSONObject("EVENT_PLACE").getDouble("x");
                        double y = array.getJSONObject(i).getJSONObject("EVENT_PLACE").getDouble("y");
                        String name_event = array.getJSONObject(i).getString("EVENT_NAME");
                        int ID = array.getJSONObject(i).getInt("ID");
                        int PLACE_TYPE = array.getJSONObject(i).getInt("PLACE_TYPE");
                        String PLACE_GROUP = array.getJSONObject(i).getString("PLACE_GROUP");
                        if((PLACE_TYPE == 0 && filter.equals(PLACE_GROUP.toLowerCase()))|| (filter.equals("all") && PLACE_TYPE == 0))
                        {
                            latLng = new LatLng(x, y);
                            marker = new MarkerOptions()
                                    .position(latLng)
                                    .title(name_event);
                            googleMap.addMarker(marker);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}