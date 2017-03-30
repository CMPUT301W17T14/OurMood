package yifanwang.mymood1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class EditMoodActivity extends AppCompatActivity {
    private Mood mood;
    private  EditText motion;
    private EditText reason;
    private TextView date;
    private EditText situation;
    private TextView location;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);
        Intent intent = getIntent();
        String target = intent.getStringExtra("MOOD");
        Gson gS = new Gson();
        mood = gS.fromJson(target,Mood.class);
        name = (TextView)findViewById(R.id.textView30);
        name.setText(mood.getName());

        motion = (EditText)findViewById(R.id.editText2);
        motion.setText(mood.getMood());

        reason = (EditText)findViewById(R.id.editText4);
        reason.setText(mood.getExplanation());

        date = (TextView) findViewById(R.id.textView31);
        date.setText(mood.getDate().toString());

        situation = (EditText)findViewById(R.id.editText6);
        situation.setText(mood.getSocial());
        /*
        location = (TextView) findViewById(R.id.textView32);
        location.setText(mood.getLocation().toString());
        */
    }
    public void editRecord(View view){
        String newMotion = motion.getText().toString();
        String newReason = reason.getText().toString();
        String newSituation = situation.getText().toString();
        mood.setMood(newMotion);
        mood.setExplanation(newReason);
        mood.setSocial(newSituation);
        new MoodList().updateMood(mood,this,false);
        
        Intent intent = new Intent(getApplicationContext(),MyHistoryActivity.class);
        startActivity(intent);
        finish();
    }
}
