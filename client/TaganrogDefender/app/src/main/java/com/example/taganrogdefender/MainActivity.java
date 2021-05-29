package com.example.taganrogdefender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    String server_ip = "192.168.43.124:3000";
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Boolean have_passport = true;
    GoogleMap map;

    Handler handler;
    Runnable r;

    request_real request = new request_real();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        handler = new Handler();
        r = new Runnable() {
            public void run() {
                UpdateMap();
                handler.postDelayed(this, 600000);
            }
        };
        handler.postDelayed(r, 0);
        UpdateMap();
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

                JSONObject json = request.GET("http://"+ server_ip + "/events/near");

                try {
                    JSONArray array = json.getJSONArray("res");
                    for (int i = 0; i < array.length(); i++) {
                        double x = array.getJSONObject(i).getJSONObject("EVENT_PLACE").getDouble("x");
                        double y = array.getJSONObject(i).getJSONObject("EVENT_PLACE").getDouble("y");
                        String name_event = array.getJSONObject(i).getString("EVENT_NAME");
                        int ID = array.getJSONObject(i).getInt("ID");
                        latLng = new LatLng(x, y);
                        marker = new MarkerOptions()
                                .position(latLng)
                                .title(name_event);
                        googleMap.addMarker(marker);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            handler.removeCallbacks(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            handler.postDelayed(r, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}