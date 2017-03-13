package yifanwang.mymood1;


import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.cc.android.map.MainActivity;
import com.cc.android.map.R;
import com.cc.android.map.constant.Constants;



import java.util.AbstractMap;

public class MapFragment extends Fragment {

    private static MapFragment fragment=null;
    public static final int POSITION=0;

    private MapView mapView;
    private AMap aMap;
    private View mapLayout;

    public static Fragment newInstance(){
        if(fragment==null){
            synchronized(MapFragment.class){
                if(fragment==null){
                    fragment=new MapFragment();
                }
            }
        }
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mapLayout == null) {
            Log.i("sys", "MF onCreateView() null");
            mapLayout = inflater.inflate(R.layout.map, null);
            mapView = (MapView) mapLayout.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            if (aMap == null) {
                aMap = mapView.getMap();
            }
        }else {

            if (mapLayout.getParent() != null) {
                ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
            }
        }
        return mapLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(Constants.MAP_FRAGMENT);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        Log.i("sys", "mf onResume");
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onPause() {
        Log.i("sys", "mf onPause");
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("sys", "mf onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

   
    @Override
    public void onDestroy() {
        Log.i("sys", "mf onDestroy");
        super.onDestroy();
        mapView.onDestroy();
    }
}