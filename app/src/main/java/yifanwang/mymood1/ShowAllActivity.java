package yifanwang.mymood1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowAllActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView motonView;
    private TextView trigerView;
    private TextView dateView;
    private TextView socialView;
    private TextView locationView;
    private TextView likeView;
    private ImageButton likeButton;
    private String userName;
    public Mood mood;
    private User user = new User();
    private ArrayList<Mood> moodArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        Intent intent = getIntent();
        String target = intent.getStringExtra("MOOD");
        Gson gS = new Gson();
        mood = gS.fromJson(target,Mood.class);
        nameView=(TextView)findViewById(R.id.nameVIEW1);
        nameView.setText(mood.getName());
        trigerView = (TextView)findViewById(R.id.resonView1);
        trigerView.setText(mood.getTrigger());
        motonView = (TextView)findViewById(R.id.motionView1);
        motonView.setText(mood.getMood());
        dateView = (TextView)findViewById(R.id.timeView1);
        dateView.setText(mood.getDate().toString());
        socialView = (TextView)findViewById(R.id.socialView1);
        socialView.setText(mood.getSocial());


        locationView = (TextView)findViewById(R.id.loactionView1);
        locationView.setText(mood.getLocation().getProvider());

        if (mood.getImage() != null) {
            ImageView preview = (ImageView) findViewById(R.id.imageView4);
            preview.setImageBitmap(mood.getImage());
        }
        /*
        locationView = (TextView)findViewById(R.id.loactionView1);
        locationView.setText(mood.getLocation().toString());
    */
        likeView = (TextView)findViewById(R.id.likeView1);
        likeView.setText(String.valueOf(mood.getLike()));

        likeButton = (ImageButton)findViewById(R.id.imageButton1);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeView.setText(String.valueOf(mood.getLike()+1));
                //mood.addLike();
                userName = mood.getName();
                ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
                getUserTask.execute(userName);
                try {
                    user = getUserTask.get();

                } catch (Exception e) {
                    Log.i("Error", "Failed to get the User out of the async object");
                }

                //Log.i("mood",mood.toString());
                moodArrayList = user.getMoodlist();
                for(int i=0;i<moodArrayList.size();i++){
                    //Log.i("moodtest",moodArrayList.get(i).toString());
                    if(moodArrayList.get(i).toString().equals(mood.toString())){
                        moodArrayList.remove(i);
                        mood.addLike();
                        moodArrayList.add(mood);
                    }
                    else{
                        Log.i("Error","not working");
                    }

                }


                ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                addUserTask.execute(user);
                //Toast.makeText(this, "New mood adds successfully", Toast.LENGTH_SHORT).show();
                likeButton.setClickable(false);
            }
        });
    }
}

