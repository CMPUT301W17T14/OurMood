package yifanwang.mymood1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class NearbyFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private View rootView;
    GoogleMap myMap;
    MapView mMapView;
    Location currentLocation;
    LocationRequest mLocationRequest;
    FusedLocationProviderApi fusedLocationProviderApi;
    GoogleApiClient mGoogleApiClient;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;

        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

    public void onHiddenChanged (boolean hidden) {
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        else {
            show();
        }
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

    private void show() {
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (currentLocation != null) {
            ElasticsearchController.AllUserTask allUserTask= new ElasticsearchController.AllUserTask();
            allUserTask.execute("");
            try {
                ArrayList<User> userlist = allUserTask.get();
                System.out.println(userlist);
                for (User user : userlist) {
                    ArrayList<Mood> moodList = user.getMoodlist();
                    for (Mood mood : moodList) {

                        if (mood.getLocation() != null){
                            if (currentLocation.distanceTo(mood.getLocation()) < 5000) {
                                String moodStatus = mood.getMood();

                                if (moodStatus.equals("angry")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.angry))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("confused")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.confused))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("disgust")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.disgust))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("happy")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.happy))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("fear")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.afraid))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("sad")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.sad))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("shame")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.shame))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                } else if (moodStatus.equals("surprise")) {
                                    myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.surprise))
                                            .position(new LatLng(mood.getLocation().getLatitude(),
                                                    mood.getLocation().getLongitude())));
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){}
            myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                show();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        else {
            show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {}

    public void onConnectionSuspended(int cause) {}

    public void onConnectionFailed (ConnectionResult result) {}
}