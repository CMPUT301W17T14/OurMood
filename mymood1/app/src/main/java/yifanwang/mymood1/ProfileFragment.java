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


public class ProfileFragment extends Fragment {

    Button FollowerRequest;
    Button MyMoodHistory;
    ImageButton addnew;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FollowerRequest= (Button)view.findViewById(R.id.FollowerRequest);
        FollowerRequest.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                ToFollower(v);}
        });


        MyMoodHistory= (Button)view.findViewById(R.id.MyMoodHistory);
        MyMoodHistory.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                ToMyMoodHistory(v);}
        });
        addnew= (ImageButton)view.findViewById(R.id.addnew);
        addnew.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                ToEdit(v);}
        });

        return view;

    }


    public void ToFollower(View view)
    {
        Intent intent = new Intent(ProfileFragment.this.getActivity(), FriendRequestActivity.class);
        startActivity(intent);
    }


    public void ToMyMoodHistory(View view){
        Intent intent = new Intent(ProfileFragment.this.getActivity(), MyHistoryActivity.class);
        startActivity(intent);
    }
    public void ToEdit(View view){
        Intent intent = new Intent(ProfileFragment.this.getActivity(), AddNewEvent.class);
        startActivity(intent);
    }

}
