package yifanwang.mymood1;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;


public class NearbyFragment extends Fragment implements OnMapReadyCallback{
    private View rootView;
    GoogleMap myMap;
    MapView mMapView;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_nearby, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        if ((ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED))
            if ((ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
            }
        else {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
    }


}