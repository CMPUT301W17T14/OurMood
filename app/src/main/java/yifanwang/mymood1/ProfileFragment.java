package yifanwang.mymood1;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class ProfileFragment extends Fragment {

    Button FollowerRequest;
    Button MyMoodHistory;
    Button AddFollow;
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



        AddFollow= (Button)view.findViewById(R.id.AddFollow);
        AddFollow.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                ToAddFollow(v);}
        });

        addnew= (ImageButton)view.findViewById(R.id.addnew);
        addnew.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                ToEdit(v);}
        });

        //sync offline data
        if (OnlineChecker.isOnline(getActivity())) {
            OfflineMoodController of = new OfflineMoodController(OurMoodApplication.username, getActivity());
            of.SyncOfflineAction();
        }

        return view;

    }


    public void ToFollower(View view)
    {
        Intent intent = new Intent(ProfileFragment.this.getActivity(), FriendRequestActivity.class);
        startActivity(intent);
    }



    public void ToAddFollow(View view){
        Intent intent = new Intent(ProfileFragment.this.getActivity(), AddFollowActivity.class);
        startActivity(intent);
    }
    //New event
    public void ToEdit(View view){
        Intent intent = new Intent(ProfileFragment.this.getActivity(), AddNewEvent.class);
        startActivityForResult(intent, 1);
        //startActivity(intent);
    }

}
