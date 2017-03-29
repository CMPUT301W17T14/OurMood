package yifanwang.mymood1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Date;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView nameView;
    private TextView motonView;
    private TextView trigerView;
    private TextView dateView;
    private TextView socialView;
    private TextView locationView;
    private TextView likeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Intent intent = getIntent();
        String target = intent.getStringExtra("MOOD");
        Gson gS = new Gson();
        Mood mood = gS.fromJson(target,Mood.class);
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
        /*
        locationView = (TextView)findViewById(R.id.loactionView);
        locationView.setText(mood.getLocation().toString());

        likeView = (TextView)findViewById(R.id.likeView);
        likeView.setText(mood.getLike());
        */




    }
}
