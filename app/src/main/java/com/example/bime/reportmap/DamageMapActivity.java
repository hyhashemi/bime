package com.example.bime.reportmap;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.bime.R;
import com.example.bime.report.ReportActivity;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DamageMapActivity extends AppCompatActivity {
    MapView mapView;
    private Marker addressPin;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_damagelocation);

        mapView = findViewById(R.id.paymentmapview);
        button = findViewById(R.id.setlocationbutton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("محل وقوع حادثه");

        final ImageView dropPinView = new ImageView(this);
        dropPinView.setImageResource(R.drawable.location_icon);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        float density = getResources().getDisplayMetrics().density;
                        params.bottomMargin = (int) (12 * density);
                        dropPinView.setLayoutParams(params);
                        mapView.addView(dropPinView);
                        LatLng position = mapboxMap.getProjection().fromScreenLocation(new PointF(dropPinView.getLeft() + (dropPinView.getWidth() / 2), dropPinView.getBottom()));
                        addressPin = mapboxMap.addMarker(new MarkerOptions().title("mhj").position(position));
                        mapboxMap.selectMarker(addressPin);

                        CameraPosition camposition = new CameraPosition.Builder()
                                .target(new LatLng(35.73, 51.35)) // Sets the new camera position
                                .zoom(11) // Sets the zoom
                                .bearing(0) // Rotate the camera
                                .tilt(0) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder

                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(camposition));
                    }
                });
            }



        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
