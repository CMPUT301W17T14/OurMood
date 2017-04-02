package yifanwang.mymood1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class EditDeleteMoodActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView motonView;
    private TextView trigerView;
    private TextView dateView;
    private TextView socialView;
    private TextView locationView;
    private Mood mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_mood);
        Intent intent = getIntent();
        String target = intent.getStringExtra("MOOD");
        Gson gS = new Gson();
        mood = gS.fromJson(target,Mood.class);

        nameView=(TextView)findViewById(R.id.textView17);
        nameView.setText(mood.getName());
        trigerView = (TextView)findViewById(R.id.textView19);
        trigerView.setText(mood.getTrigger());
        motonView = (TextView)findViewById(R.id.textView18);
        motonView.setText(mood.getMood());
        dateView = (TextView)findViewById(R.id.textView20);
        dateView.setText(mood.getDate().toString());
        socialView = (TextView)findViewById(R.id.textView21);
        socialView.setText(mood.getSocial());
        /*
        locationView = (TextView)findViewById(R.id.textView22);
        locationView.setText(mood.getLocation().toString());
        */

    }

    public void delete(View view){
        new MoodList().deleteMood(mood,this,false);
        Intent intent = new Intent(getApplicationContext(),MyHistoryActivity.class);
        startActivity(intent);
        finish();
    }
    public void edit(View view){
        Intent intent = new Intent(getApplicationContext(),EditMoodActivity.class);
        Gson gS = new Gson();
        String target = gS.toJson(mood);
        intent.putExtra("MOOD",target);
        startActivity(intent);
    }

}
