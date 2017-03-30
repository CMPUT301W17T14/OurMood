package yifanwang.mymood1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
public class AddNewEvent extends AppCompatActivity {
    private String motion;
    private User user;
    private String userName;
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

    }

    public void send(View view){
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);
        userName = OurMoodApplication.username;
        Mood mood = new Mood(userName,motion);
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(userName);

        try {
            user = getUserTask.get();
        }catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        user.addMoodlist(mood);

        ElasticsearchController.UpdateUserTask updateUserTask = new ElasticsearchController.UpdateUserTask();
        updateUserTask.execute(user);
        //set mood propoties
        finish();
    }

    public void findlocation(View view){
        Intent intent = new Intent(this,SeeMapActivity.class);
        startActivity(intent);

    }
}
