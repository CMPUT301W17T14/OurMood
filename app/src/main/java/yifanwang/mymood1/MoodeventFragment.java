package yifanwang.mymood1;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import yifanwang.mymood1.MoodeventAdapter;

public class MoodeventFragment extends Fragment {
    public final static String EXTRA_USERNAME_MSG = "com.app.username";
    ImageButton fliter;
    ImageButton globe;

    private ListView moodEventsListView;
    private CheckBox filterDate;
    private CheckBox filterWeek;
    private EditText filterTrigger;
    private ImageButton applyFilters;
    private Boolean dateFilterSelected;
    private Boolean weekFilterSelected;
    private Boolean moodFilterSelected;
    private Boolean triggerFilterSelected;
    private Spinner moodSpinner;
    private boolean satisfiesMood;
    private boolean satisfiesDate;
    private boolean satisfiesTrigger;
    private ArrayList<Mood> filteredMoodList;
    private ArrayList<Mood> unfilteredMoodList;
    protected MoodeventAdapter adapter;
    private final long oneWeek = 604800000L;    // one weeks time


    private User user = new User();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_moodevent, container, false);
        globe= (ImageButton)rootView.findViewById(R.id.globe);
        globe.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v){
                Tomain(v);}
        });


        String currentUserName = OurMoodApplication.getUsername();
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(currentUserName);

        try {
            user = getUserTask.get();
            if (user != null) {
                unfilteredMoodList = user.getMoodlist();
                filteredMoodList = new ArrayList<Mood>(unfilteredMoodList);
            }
        }catch(Exception e){
            Log.i("Error", "Failed to get the User");
        }


        //initialize clickables
        moodEventsListView = (ListView) rootView.findViewById(R.id.moodEventsList);
        filterWeek = (CheckBox)rootView.findViewById(R.id.weekfilter);
        filterTrigger = (EditText) rootView.findViewById(R.id.filterreason);
        applyFilters= (ImageButton)rootView.findViewById(R.id.apply);
        moodSpinner = (Spinner) rootView.findViewById(R.id.moodsspinner);


        // set the elements for the spinner
        List<String> moodCategories = new ArrayList<String>();
        moodCategories.add("None");     // default option
        MoodState[] moodStates = MoodState.values();
        for (MoodState moodState : moodStates) {
            moodCategories.add(moodState.toString());
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, moodCategories);
        moodSpinner.setAdapter(adapterSpinner);






        //apply the filters now
        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });


        /**
         * show the mood detail of the moodeventlist
         */
        moodEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowDetailActivity.class);


                intent.putExtra("MOOD",String.valueOf(position));
                startActivity(intent);
            }
        });

        return rootView;

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



    private void filter() {
        filteredMoodList.clear();


     //check which fliter has been selected

        checkFilterSelected();


        for (Mood moodEvent : unfilteredMoodList) {

            satisfiesMood = true;
            satisfiesDate = true;
            satisfiesTrigger = true;
            try {
                //mood filter
                if(moodEvent!=null) {
                    if (moodFilterSelected && !(moodEvent.getMood().equals(
                            moodSpinner.getSelectedItem().toString())))
                        satisfiesMood = false;

                    //trigger filter
                    if (triggerFilterSelected && !moodEvent.getTrigger().toLowerCase().
                            contains(filterTrigger.getText().toString().toLowerCase()))
                        satisfiesTrigger = false;

                    //date filter
                    if (weekFilterSelected && moodEvent.getDate().before(
                            new Date(new Date().getTime() - oneWeek)))
                        satisfiesDate = false;

                    //the combination of the fliters
                    if (satisfiesMood && satisfiesDate && satisfiesTrigger)
                        filteredMoodList.add(moodEvent);
                }
                else {
                    Log.i("Error222","t");
                }
            }catch (Exception e){
                Log.i("Error","test");
            }
        }

        // sort mood events in reverse chronological Order
        Collections.sort(filteredMoodList, new Order());

    }

    /**
     * which fliter has been selected
     */
    private void checkFilterSelected() {
        weekFilterSelected = (filterWeek.isChecked());
        triggerFilterSelected = (!filterTrigger.getText().toString().
                equals(""));
        moodFilterSelected = (!moodSpinner.getSelectedItem().toString().
                equals("None"));

    }

    @Override
    public void onStart() {
        super.onStart();



        adapter = new MoodeventAdapter(getActivity(),R.layout.item_example, filteredMoodList);
        moodEventsListView.setAdapter(adapter);
        refresh();
    }


    /**
     * filter and refresh the display
     */
    private void refresh() {
        filter();
        adapter.notifyDataSetChanged();
    }


}
