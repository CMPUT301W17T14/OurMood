package yifanwang.mymood1;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MoodeventFragment extends Fragment {
    public final static String EXTRA_USERNAME_MSG = "com.app.username";
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

        ListView moodlist = (ListView) view.findViewById(R.id.mood_list);

        Boolean offline = false;

        Context ctx = this.getActivity();
        User user = new User();
        //ArrayList<Mood> lists = new MoodList().getMoodLists(ctx,offline);
        String currentUserName = OurMoodApplication.getUsername();
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(currentUserName);

        try {
            user = getUserTask.get();
        }catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        Filter_data data = new Filter_data();
        final ArrayList<Mood> lists = data.getUserMood(user);
        Mood test = new Mood("h1","sad");
        moodlist.setAdapter(null);
        try{
            if (lists!=null){
                MoodlistAdpater moodlistAdpater = new MoodlistAdpater(ctx,lists);
                moodlist.setAdapter(moodlistAdpater);
            }
        }catch (Exception e){
            Log.i("Error","null moodlist");
        }


        moodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MoodeventFragment.this.getActivity(), ShowDetailActivity.class);
                Gson gS = new Gson();
                String target = gS.toJson(lists.get(position));
                intent.putExtra("MOOD",target);
                startActivity(intent);
            }
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
