package yifanwang.mymood1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView motonView;
    private TextView trigerView;
    private TextView dateView;
    private TextView socialView;
    private TextView locationView;
    private Button edit;
    private Button delete;
    private String userName;
    public Mood mood;
    private User user = new User();
    private ArrayList<Mood> moodArrayList;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Intent intent = getIntent();


        String target = intent.getStringExtra("MOOD");
        userName = OurMoodApplication.getUsername();
        //moodArrayList = user.getMoodlist();
        //mood = moodArrayList.get(position);

        Gson gS = new Gson();
        mood = gS.fromJson(target,Mood.class);
        /**
         * show the detail of the mood
         */
        nameView=(TextView)findViewById(R.id.nameVIEW);
        nameView.setText(mood.getName());
        trigerView = (TextView)findViewById(R.id.resonView);
        trigerView.setText(mood.getTrigger());
        motonView = (TextView)findViewById(R.id.motionView);
        motonView.setText(mood.getMood());
        dateView = (TextView)findViewById(R.id.timeView);
        dateView.setText(mood.getDate().toString());
        socialView = (TextView)findViewById(R.id.socialView);
        socialView.setText(mood.getSocial());

        //if (mood.getImage() != null) {
            ImageView preview = (ImageView) findViewById(R.id.imageView3);
            preview.setImageBitmap(mood.getImage());
        //}
        if (mood.getLocation() != null) {
            locationView = (TextView) findViewById(R.id.loactionView);
            locationView.setText(mood.getLocation().getProvider());
        }

        /**
         * if the delete button is pressed,
         * we check if the device is online or not,
         * if the device is online, we delete the mood in the server,
         * if the device is offline, we store the delete move into
         * a json file and wait for the device to come online and
         * delete the mood in the server
         */
        delete = (Button)findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnlineChecker.isOnline(getApplicationContext())) {
                    ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
                    getUserTask.execute(userName);
                    try {
                        user = getUserTask.get();

                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the User out of the async object");
                    }
                    moodArrayList = user.getMoodlist();
                    for (int i = 0; i < moodArrayList.size(); i++) {
                        //Log.i("moodtest",moodArrayList.get(i).toString());
                        if (moodArrayList.get(i).toString().equals(mood.toString())) {
                            moodArrayList.remove(i);
                        } else {
                            Log.i("Error", "not working");
                        }

                    }
                    ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                    addUserTask.execute(user);
                    //Toast.makeText(this, "New mood adds successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    OfflineMoodController of = new OfflineMoodController(userName, getApplicationContext());
                    Toast.makeText(ShowDetailActivity.this,"Offline Activity Saved!", Toast.LENGTH_LONG).show();
                    of.addOfflineAction("DELETE", mood);
                    finish();
                }
            }
        });
        edit = (Button)findViewById(R.id.EditButton);

    }

    /**
     * if the edit button is pressed,
     * we jump to editActivity.
     * @param view
     */
    public void editMood(View view){
        Intent intent = new Intent(this, EditMoodActivity.class);
        Gson gS = new Gson();
        String target = gS.toJson(mood);
        intent.putExtra("MOOD",target);
        startActivity(intent);
        finish();
    }
}
