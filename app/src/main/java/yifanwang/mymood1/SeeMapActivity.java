package yifanwang.mymood1;

import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;

public class SeeMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

        int caller = getIntent().getIntExtra("caller", 0);
        ArrayList<Mood> mMoodList;

        switch (caller) {
            case 1:
                mMoodList = MoodeventFragment.filteredMoodList;
                Collections.reverse(mMoodList);
                for (Mood mood : mMoodList) {
                    if (mood.getLocation() != null ) {
                        String moodStatus = mood.getMood();

                        if (moodStatus.equals("angry")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.angry))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("confused")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.confused))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("disgust")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.disgust))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("happy")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.happy))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("fear")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.afraid))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("sad")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.sad))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("shame")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.shame))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        } else if (moodStatus.equals("surprise")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.surprise))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())));
                        }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mood.getLocation().getLatitude(),
                                mood.getLocation().getLongitude())));
                    }
                }
                break;
            case 2:
                mMoodList = AllMoodeventFragment.filteredMoodList;
                Collections.reverse(mMoodList);
                for (Mood mood : mMoodList) {
                    String moodStatus = mood.getMood();
                    if (mood.getLocation() != null ) {
                        if (moodStatus.equals("angry")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.angry))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("confused")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.confused))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("disgust")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.disgust))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("happy")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.happy))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("fear")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.afraid))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("sad")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.sad))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("shame")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.shame))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        } else if (moodStatus.equals("surprise")) {
                            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.surprise))
                                    .position(new LatLng(mood.getLocation().getLatitude(),
                                            mood.getLocation().getLongitude())).title(mood.getName()));
                        }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mood.getLocation().getLatitude(),
                                mood.getLocation().getLongitude())));
                    }
                }
                break;
        }

        /**
         // Add a marker in Sydney and move the camera
         LatLng sydney = new LatLng(-34, 151);
         mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */
    }
}