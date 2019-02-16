package com.example.bime.payment;

import android.os.Bundle;

import com.example.bime.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_payment);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        MarkerOptions markerOptions1 = new MarkerOptions();
                        markerOptions1.setPosition(new LatLng(20, 30));
                        markerOptions1.setSnippet("ثابت 1");

                        MarkerOptions markerOptions2 = new MarkerOptions();
                        markerOptions2.setPosition(new LatLng(30, 40));
                        markerOptions2.setSnippet("ثابت 1");

                        mapboxMap.addMarker(markerOptions2);
                        mapboxMap.addMarker(markerOptions1);

//                        mapboxMap.setLatLngBoundsForCameraTarget(new LatLngBounds(35.798701, 51.5, 35.4315, 51.1764 ));
                    }
                });
            }
        });
    }

}
