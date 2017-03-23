package yifanwang.mymood1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AddNewEvent extends AppCompatActivity {

    OurMoodApplication app;
    ImageButton addImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        addImageButton = (ImageButton)findViewById(R.id.addImage_bt);
        app = (OurMoodApplication) getApplication();
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNewEvent.this, CameroActivity.class);
                startActivity(i);
            }
        });
    }

    public void send(View view){
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);
        Boolean offline = false;
        String userName = OurMoodApplication.username;
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
