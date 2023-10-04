package com.credinkamovil.pe.ui.maps.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapLocateFragment extends BaseFragment implements MapLocateMvpView, LocationListener {
    public static final String TAG = MapLocateFragment.class.getName();
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private ImageButton imageButtonGps;
    private static ArrayList<EnCoordinadas> oListaCoordinadas;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    private double latitudeUser;
    private double longitudeUser;

    @Inject
    MapLocateMvpPresenter<MapLocateMvpView> mMapLocatePresenter;

    public MapLocateFragment() {
    }

    public static MapLocateFragment newInstance() {
        MapLocateFragment fragment = new MapLocateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_map_locate, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mMapLocatePresenter.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
            mMapLocatePresenter.onErrorCallback();
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAPI);
            imageButtonGps = view.findViewById(R.id.imb_gps_ubication);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            cargarMapa();
        } catch (Exception ex) {
            mMapLocatePresenter.onErrorCallback();
            ex.printStackTrace();
        }
    }

    private void cargarMapa() {
        setUpListeners();
        initLoadData();
    }

    private void setUpListeners() {
        imageButtonGps.setOnClickListener(new onClicListenerGpsUbication());
    }

    private void initLoadData() {
        mMapLocatePresenter.onInitLoaderData();
        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(60 * 1000);
        //mLocationRequest.setFastestInterval(20 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onDestroyView() {
        mMapLocatePresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Nueva coordenada usuario= latitudeUser" + location.getLatitude());
        Log.i(TAG, "Nueva coordenada usuario= longitudeUser" + location.getLongitude());
        latitudeUser = location.getLatitude();
        longitudeUser = location.getLongitude();
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
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                                mGoogleMap.setMyLocationEnabled(true);
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
            mMapLocatePresenter.onErrorCallback();
            ex.printStackTrace();
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
            mMapLocatePresenter.onErrorCallback();
            ex.printStackTrace();
        }
    }

    class onClicListenerGpsUbication implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            setCenterMapUserPosition();
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
                    Log.i("MapLocateFragment", "Location: " + location.getLatitude() + " " + location.getLongitude());
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