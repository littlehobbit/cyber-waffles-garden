package com.example.taganrogdefender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String server_ip = "192.168.43.143:3000";
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Boolean have_passport = true;
    GoogleMap map;


    CardView buy_passport_card;
    CardView plug_passport;

    RecyclerView recyclerView;

    Handler handler;
    Runnable run_update_map;
    Runnable run_refresh;

    private PassportAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    PassportArray passportArray = new PassportArray();

    request_real request = new request_real();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        recyclerView = findViewById(R.id.recyclerview_passports);
        plug_passport = findViewById(R.id.plug_passport);
        buy_passport_card = findViewById(R.id.buy_passport_card);
        //
        passportArray.loadArray(this);

        if(passportArray.getPassports().isEmpty()) {
            plug_passport.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            plug_passport.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            buildRecyclerView();
        }

        handler = new Handler();
        run_update_map = new Runnable() {
            public void run() {
                UpdateMap();
                handler.postDelayed(run_update_map, 600000);
            }
        };

        run_refresh = new Runnable() {
            public void run() {
                Refresh();
                handler.postDelayed(run_refresh, 600000);
            }
        };

        handler.postDelayed(run_update_map, 0);
        handler.postDelayed(run_refresh, 600000);


        buy_passport_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        buy_passport_dialog dialog = new buy_passport_dialog();
        dialog.setActivity(this);

        dialog.show(getSupportFragmentManager(), "buy_passport_dialog");
    }

    private void Refresh() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", request_real.refresh_token_access);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject response = request.POST("http://"+ server_ip + "/refresh", jsonObject.toString());
        try {
            request_real.token_access = response.getString("accesstoken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void updateRecyclerView() {
        passportArray.loadArray(MainActivity.this);
        _adapter.notifyDataSetChanged();
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview_passports);
        recyclerView.setHasFixedSize(true);
        _layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        _adapter = new PassportAdapter(passportArray.getPassports());

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(_layoutManager);
        recyclerView.setAdapter(_adapter);

        _adapter.setOnItemClickListener(new PassportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "Вы нажали на билеты, молодец", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            handler.removeCallbacks(run_update_map);
            handler.removeCallbacks(run_refresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            handler.postDelayed(run_update_map, 0);
            handler.postDelayed(run_refresh, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}