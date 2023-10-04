package com.credinkamovil.pe.ui.maps;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.ui.base.BaseActivity;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class UbicacionMapsActivity extends BaseActivity implements UbicacionMvpView, LocationListener {
    public static String TAG = UbicacionMapsActivity.class.getName();
    @Inject
    UbicacionMvpPresenter<UbicacionMvpView> mUbicacionPresenter;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private ImageButton imageButtonGps;
    private static ArrayList<EnCoordinadas> oListaCoordinadas;
    private static final int PERMISSION_REQUEST_CODE = 400;
    private Toolbar mToolbar;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    private double latitudeUser;
    private double longitudeUser;

    String[] appPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_ubicacion_maps);
            getActivityComponent().inject(this);
            mUbicacionPresenter.onAttach(UbicacionMapsActivity.this);
            mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (checkAndRequestPermissions()) {
                cargarMapa();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitudeUser = location.getLatitude();
        longitudeUser = location.getLongitude();
    }

    @Override
    protected void setUpView() {
        imageButtonGps = findViewById(R.id.imb_gps_ubication);
        mToolbar = findViewById(R.id.tb_ubicacion);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onCompletarDatosCoordinadas(ArrayList<EnCoordinadas> oLista) {
        try {
            oListaCoordinadas = new ArrayList<>();
            if (oLista.size() > 0) {
                oListaCoordinadas = oLista;
                mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mGoogleMap = googleMap;
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mGoogleMap.clear();
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        for (int lat = 0; lat < oListaCoordinadas.size(); lat++) {
                            LatLng objPosition = new LatLng(oListaCoordinadas.get(lat).getnLatitud(), oListaCoordinadas.get(lat).getnLongitud());
                            MarkerOptions mMarkerOptions = new MarkerOptions();
                            mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_agencia));
                            mMarkerOptions.position(objPosition);
                            mMarkerOptions.title(oListaCoordinadas.get(lat).getsNombreAgencia());
                            mMarkerOptions.snippet(oListaCoordinadas.get(lat).getsDireccion());
                            Marker objMarker = mGoogleMap.addMarker(mMarkerOptions);
                            objMarker.setDraggable(false);
                        }
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                                mGoogleMap.setMyLocationEnabled(true);
                            } else {
                                checkAndRequestPermissions();
                            }
                        } else {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            mGoogleMap.setMyLocationEnabled(true);
                        }
                        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f), 1000, null);
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
                cargarMapa();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intentNextActivity(UbicacionMapsActivity.this, LoginActivity.class, true);
        overridePendingTransition(R.anim.transition_in_left, R.anim.transition_out_right);
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void cargarMapa() {
        setUpView();
        setUpListeners();
        initLoadData();
    }

    private void setUpListeners() {
        imageButtonGps.setOnClickListener(new onClicListenerGpsUbication());
        mToolbar.setNavigationOnClickListener(new onClicNavigationBack());
    }

    private void initLoadData() {
        mUbicacionPresenter.onInitLoaderData();
        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(60 * 1000);
        //mLocationRequest.setFastestInterval(20 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    class onClicListenerGpsUbication implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            setCenterMapUserPosition();
        }
    }

    class onClicNavigationBack implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            intentNextActivity(UbicacionMapsActivity.this, LoginActivity.class, true);
            overridePendingTransition(R.anim.transition_in_left, R.anim.transition_out_right);
        }
    }

    private void setCenterMapUserPosition() {
        try {
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            //Place current location marker
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
            //move map camera
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f), 1000, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            try {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    Location location = locationList.get(locationList.size() - 1);
                    Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                    mLastLocation = location;
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }
                    //Place current location marker
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                    //move map camera
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
