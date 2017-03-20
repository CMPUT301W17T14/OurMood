package yifanwang.mymood1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AddNewEvent extends AppCompatActivity {

    OurMoodApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        app = (OurMoodApplication) getApplication();
    }

    public void send(View view){
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);
        Boolean offline = false;
        String userName = app.getUsername();
        String current_mood = "";

        Mood mood = new Mood(userName, current_mood);

        //set mood propoties


        MoodList md = new MoodList();
        md.addMood(mood, this, offline);

        finish();
    }

    public void findlocation(View view){
        Intent intent = new Intent(this,SeeMapActivity.class);
        startActivity(intent);

    }
}
