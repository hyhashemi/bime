package com.example.bime.payment;

import android.os.Bundle;

import com.example.bime.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
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

        mapView = findViewById(R.id.paymentmapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        MarkerOptions markerOptions1 = new MarkerOptions();
                        markerOptions1.setPosition(new LatLng(35.735900, 51.335837));
                        markerOptions1.setSnippet("ثابت 1");

                        MarkerOptions markerOptions2 = new MarkerOptions();
                        markerOptions2.setPosition(new LatLng(35.735766, 51.374241));
                        markerOptions2.setSnippet("ثابت 2");

                        mapboxMap.addMarker(markerOptions2);
                        mapboxMap.addMarker(markerOptions1);

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(35.7, 51.3)) // Sets the new camera position
                                .zoom(10) // Sets the zoom
                                .bearing(0) // Rotate the camera
                                .tilt(0) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder

                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position));
                    }
                });
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView = null;
    }
}
