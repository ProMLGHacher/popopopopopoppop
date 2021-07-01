package com.example.uktgfmyapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import retrofit.RestAdapter;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleMap.OnMyLocationClickListener, GoogleMap.OnPoiClickListener {

    MainActivity mainActivity;

    private GoogleMap mMap;
    GPSTracker gpsTracker;

    double latitude;
    double longitude;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.setBuildingsEnabled(true);
            mMap.setOnPoiClickListener(mainActivity);


//        mMap.addMarker(new MarkerOptions().position(orenburg).title("Это Оренбург")
//                .snippet("В Сиднее много котов")
//                .draggable(true))
//                //для того чтобы отобразить окно с инфой
//                .showInfoWindow();

//        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(orenburg));


            if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        2);

                Log.e("IKUGHRFIYUBIGYERG", "lrijhsbdfgihlekrjbgfihjlrekbgiuhjklybfjg");



            }

            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationClickListener(mainActivity);


            moveCameraOnLocation();

            LatLng pos = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            LatLng orenburg = new LatLng(51.767577, 55.097376);


            mMap.addMarker(new MarkerOptions().position(orenburg).title("Это Оренбург")
                .snippet("пролати")
                .draggable(true))
                //для того чтобы отобразить окно с инфой
                .showInfoWindow();

            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(orenburg));






//            RestAdapter restAdapter = new RestAdapter.Builder()
//                    .setEndpoint("https://maps.googleapis.com")
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                    .build();
//            RouteApiInterface routeService = restAdapter.create(RouteApiInterface.class);
//            RouteResponse routeResponse = routeService.getRoute("51,55", "51,58", "AIzaSyAiBHJGxafBL4v8PgVV70zvNIW_70eMbZM");







        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mainActivity = (MainActivity)requireActivity();

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    2);

            Log.e("IKUGHRFIYUBIGYERG", "lrijhsbdfgihlekrjbgfihjlrekbgiuhjklybfjg");
        }

        gpsTracker = new GPSTracker(mainActivity, mainActivity);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }



    }




    public void setMaxMinZoom() {
        mMap.setMinZoomPreference(5);
        mMap.setMaxZoomPreference(20);
    }

    // рисует круг
    public void addCircle() {
        LatLng seattle = new LatLng(47.6062095, -122.3320708);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(seattle);
        circleOptions.radius(8500);
        circleOptions.fillColor(Color.BLUE);
        circleOptions.strokeColor(Color.RED);
        circleOptions.strokeWidth(4);

        mMap.addCircle(circleOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
    }

    // можно ставить несколько точек(больше 2х) и будет рисовать линию поочерёдно
    public void addPolyLine() {
        LatLng issaquah = new LatLng(47.5301011, -122.0326191);
        LatLng seattle = new LatLng(47.6062095, -122.3320708);
        LatLng bellevue = new LatLng(47.6101497, -122.2015159);
        LatLng sammamish = new LatLng(47.6162683, -122.0355736);
        LatLng redmond = new LatLng(47.6739881, -122.121512);

        PolylineOptions plo =  new PolylineOptions();
        plo.add(issaquah, sammamish, redmond, seattle);
        plo.color(Color.RED);
        plo.geodesic(true);
        plo.startCap(new RoundCap());
        plo.width(20);
        plo.jointType(JointType.BEVEL);

        mMap.addPolyline(plo);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(issaquah));
        mMap.setMinZoomPreference(10);

    }

    // строит фигуру из линий
    public void addPolygon() {
        LatLng issaquah = new LatLng(47.5301011, -122.0326191);
        LatLng seattle = new LatLng(47.6062095, -122.3320708);
        LatLng bellevue = new LatLng(47.6101497, -122.2015159);
        LatLng sammamish = new LatLng(47.6162683, -122.0355736);
        LatLng redmond = new LatLng(47.6739881, -122.121512);

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(issaquah, sammamish, redmond, seattle);
        polygonOptions.strokeJointType(JointType.ROUND);
        polygonOptions.strokeColor(Color.RED);
        polygonOptions.strokeWidth(10);

        mMap.addPolygon(polygonOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
        mMap.setMinZoomPreference(10);
    }

    //ставит фотку привязанную к позиции которая крутится и перемещается с картой
    public void addGroundOverlay() {
        LatLng bellevue = new LatLng(47.6101497, -122.2015159);

        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions ();
        groundOverlayOptions.position(bellevue, 100, 100 )
                .image( BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background));

        mMap.addGroundOverlay(groundOverlayOptions);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(bellevue));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    // при нажатии на точку местоположения
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng pos = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(pos).title("ты тут")
                .snippet("лох"))
                //для того чтобы отобразить окно с инфой
                .showInfoWindow();

        mMap.setMinZoomPreference(10);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
    }

    //поставить марке на местороложение
    public void takeLocation() {
        LatLng pos = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        mMap.addMarker(new MarkerOptions().position(pos).title("ты тут")
                .snippet("лох"))
                //для того чтобы отобразить окно с инфой
                .showInfoWindow();

        mMap.setMinZoomPreference(10);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
    }

    //поставить марке на местороложение
    public void moveCameraOnLocation() {
        LatLng pos = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        mMap.setMinZoomPreference(15);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
    }

    //при нажатии на объект
    @Override
    public void onPoiClick(@NonNull PointOfInterest pointOfInterest) {
        Toast.makeText(mainActivity, "Clicked: " +
                        pointOfInterest.name + "\nPlace ID:" + pointOfInterest.placeId +
                        "\nLatitude:" + pointOfInterest.latLng.latitude +
                        " Longitude:" + pointOfInterest.latLng.longitude,
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}