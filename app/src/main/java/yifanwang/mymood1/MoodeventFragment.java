package yifanwang.mymood1;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MoodeventFragment extends Fragment {
    ImageButton fliter;
    ImageButton globe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_moodevent, container, false);
        fliter= (ImageButton)view.findViewById(R.id.fliter);
        fliter.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                ToInterest(v);}
        });
        globe= (ImageButton)view.findViewById(R.id.globe);
        globe.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                Tomain(v);}
        });

        return view;

    }
    public void ToInterest(View view)
    {
        Intent intent = new Intent(MoodeventFragment.this.getActivity(), Fliter.class);
        startActivity(intent);
    }
    public void Tomain(View view)
    {
        Intent intent = new Intent(MoodeventFragment.this.getActivity(), SeeMapActivity.class);
        startActivity(intent);
    }



}
