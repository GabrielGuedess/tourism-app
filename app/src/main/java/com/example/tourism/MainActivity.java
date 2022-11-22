package com.example.tourism;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.Cancelable;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private LocationComponentPlugin location;

    private LocationManager locationManager;

    private View bottomSheet;

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);

        mapView.getMapboxMap().loadStyleUri(Style.DARK);

        bottomSheet = findViewById(R.id.bottomSheet);
        BottomSheetBehavior.from(bottomSheet).setPeekHeight(86);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);

            return;
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 1, locationListenerGPS);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        final Cancelable cancelable = CameraAnimationsUtils.getCamera(mapView).easeTo(
                new CameraOptions.Builder()
                        .center(Point.fromLngLat(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude()))
                        .zoom(12.0)
                        .pitch(40.0)
                        .build(),
                new MapAnimationOptions.Builder()
                        .duration(5000)
                        .build()
        );

        List<List<Point>> points = List.of(List.of(
                Point.fromLngLat(-46.53339359444723, -23.45451046687174),
                Point.fromLngLat(-46.528378467719534, -23.459765977969326),
                Point.fromLngLat(-46.52800721404722, -23.4602204967362),
                Point.fromLngLat(-46.527405755245184, -23.46187570705507),
                Point.fromLngLat(-46.526984734083726, -23.46186782514974),
                Point.fromLngLat(-46.526469197967714, -23.461710186956537),
                Point.fromLngLat(-46.52598803092653, -23.462261919809933),
                Point.fromLngLat(-46.52578181647971, -23.462190982857976),
                Point.fromLngLat(-46.52634031393907, -23.461387028072707),
                Point.fromLngLat(-46.52663245107118, -23.461063868396465),
                Point.fromLngLat(-46.52642623662507, -23.46031508074256),
                Point.fromLngLat(-46.52776663052626, -23.45973181162684),
                Point.fromLngLat(-46.52876333368417, -23.45757211706254),
                Point.fromLngLat(-46.52905993456258, -23.457071457304593),
                Point.fromLngLat(-46.52903672593365, -23.456915325888787),
                Point.fromLngLat(-46.52878143101299, -23.45663854701533),
                Point.fromLngLat(-46.52884332069027, -23.456454027444792),
                Point.fromLngLat(-46.528990308780465, -23.45630499219665),
                Point.fromLngLat(-46.529841291850545, -23.456077890575614),
                Point.fromLngLat(-46.52991091773748, -23.455914661045327),
                Point.fromLngLat(-46.53018168507808, -23.455687558752956),
                Point.fromLngLat(-46.529980543625044, -23.455141092262878),
                Point.fromLngLat(-46.53018168507808, -23.455048831462477),
                Point.fromLngLat(-46.53018168507808, -23.454644302581258),
                Point.fromLngLat(-46.532858413642145, -23.45417589917072),
                Point.fromLngLat(-46.533260696547586, -23.45419719027008),
                Point.fromLngLat(-46.53339994832268, -23.45435332490092),
                Point.fromLngLat(-46.53339994832268, -23.45447397426068)
        ));

        AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations((MapPluginProviderDelegate) mapView);

        PolygonAnnotationManager polygonAnnotationManager = PolygonAnnotationManagerKt.createPolygonAnnotationManager(annotationAPI, mapView);

        PolygonAnnotationOptions polygonAnnotationOptions = new PolygonAnnotationOptions()
                .withPoints(points)
                .withFillColor("#7FDA23")
                .withFillOpacity(0.4);

        CircleAnnotationManager circleAnnotationManager = CircleAnnotationManagerKt.createCircleAnnotationManager(annotationAPI, mapView);

        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI, mapView);

        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(-46.529687773412746, -23.457351838840353))
                .withIconImage(BitmapFactory.decodeResource(this.getResources(), R.drawable.location));

        pointAnnotationManager.create(pointAnnotationOptions);

        CircleAnnotationOptions circleAnnotationOptions = new CircleAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(lastKnownLocation.getLongitude(), lastKnownLocation.getLatitude(), 17))
                .withCircleRadius(8.0)
                .withCircleColor("#7C3AED")
                .withCircleStrokeWidth(2.0)
                .withCircleStrokeColor("#ffffff");

        polygonAnnotationManager.create(polygonAnnotationOptions);
        circleAnnotationManager.create(circleAnnotationOptions);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}