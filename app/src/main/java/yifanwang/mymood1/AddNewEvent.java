package yifanwang.mymood1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNewEvent extends AppCompatActivity {
    private String motion;
    private String reason;
    private User user;
    private String userName;
    private String social;
    OurMoodApplication app;
    ImageButton addImageButton;
    RadioButton button0;
    RadioButton button1;
    RadioButton button2;
    RadioButton button3;
    RadioButton button4;
    RadioButton button5;
    RadioButton button6;
    RadioButton button7;
    RadioButton button8;
    RadioButton button9;
    RadioButton button10;
    RadioButton button11;
    EditText Reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        addImageButton = (ImageButton)findViewById(R.id.addImage_bt);
        button0 = (RadioButton)findViewById(R.id.radioButton30);
        button1 = (RadioButton)findViewById(R.id.radioButton31);
        button2 = (RadioButton)findViewById(R.id.radioButton32);
        button3 = (RadioButton)findViewById(R.id.radioButton33);
        button4 = (RadioButton)findViewById(R.id.radioButton34);
        button5 = (RadioButton)findViewById(R.id.radioButton35);
        button6 = (RadioButton)findViewById(R.id.radioButton36);
        button7 = (RadioButton)findViewById(R.id.radioButton37);

        button8 = (RadioButton)findViewById(R.id.alone);
        button9 = (RadioButton)findViewById(R.id.withother);
        button10 = (RadioButton)findViewById(R.id.moreone);
        button11 = (RadioButton)findViewById(R.id.crowd);
        //app = (OurMoodApplication) getApplication();

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNewEvent.this, CameroActivity.class);
                startActivity(i);
            }
        });
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Anger";
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Comfusion";
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Disgust";
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Fear";
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Happy";
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Sad";
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Shame";
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "Surprise";
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "Alone";
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "With one other people";
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "With more than one people";
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "crowd";
            }
        });

        Reason = (EditText)findViewById(R.id.reason);
        reason = Reason.getText().toString();
    }

    public void send(View view){
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);
        userName = OurMoodApplication.username;
        Log.d("username",userName);
        Mood mood = new Mood(userName,motion);
        mood.setExplanation(reason);
        mood.setSocial(social);
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(userName);

        try {
            user = getUserTask.get();
            user.addMood(mood);
            ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
            addUserTask.execute(user);
            Toast.makeText(this, "New mood adds successfully", Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        ElasticsearchController.GetUserTask getUserTask1 = new ElasticsearchController.GetUserTask();
        getUserTask1.execute(userName);

        //set mood propoties
        finish();
    }

    public void findlocation(View view){
        Intent intent = new Intent(this,SeeMapActivity.class);
        startActivity(intent);

    }
}
