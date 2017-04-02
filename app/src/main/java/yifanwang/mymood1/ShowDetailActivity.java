package yifanwang.mymood1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ShowDetailActivity extends AppCompatActivity {
    Bitmap imageBitmap = null;
    private TextView nameView;
    private TextView motonView;
    private TextView trigerView;
    private TextView dateView;
    private TextView socialView;
    private ImageView imageView;
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


        String message = intent.getStringExtra("MOOD");
        position = Integer.parseInt(message);
        userName = OurMoodApplication.getUsername();
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(userName);
        try {
            user = getUserTask.get();

        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        moodArrayList = user.getMoodlist();
        mood = moodArrayList.get(position);
        System.out.println(mood);
        /*
        Gson gS = new Gson();
        mood = gS.fromJson(target,Mood.class);
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

        imageView = (ImageView)findViewById(R.id.ShowImageView);
        //if (mood.getImage() != null) {
        //imageBitmap = mood.getImage();
        if (mood.getImage() != null) {
            //imageBitmap = mood.getImage();
            //Bitmap convertedImage = getResizedBitmap(imageBitmap, 100, 100);
            imageView.setImageBitmap(mood.getImage());
        }
        /*
        locationView = (TextView)findViewById(R.id.loactionView);
        locationView.setText(mood.getLocation().toString());
    */


        delete = (Button)findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodArrayList.remove(position);
                ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                addUserTask.execute(user);
                //Toast.makeText(this, "New mood adds successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        edit = (Button)findViewById(R.id.EditButton);

    }
    public void editMood(View view){
        Intent intent = new Intent(this, EditMoodActivity.class);
        intent.putExtra("MOOD",String.valueOf(position));
        startActivity(intent);
        finish();
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxWidth, int maxHeight) {

        int width = maxWidth;
        int height = maxHeight;

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
