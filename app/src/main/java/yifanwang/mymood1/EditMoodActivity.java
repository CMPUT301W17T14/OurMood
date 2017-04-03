package yifanwang.mymood1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EditMoodActivity extends AppCompatActivity {
    private String motion;
    private String trigger;
    private User user;
    private String userName;
    private String social;
    private Location location;
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
    EditText triggerString;

    Uri imageFileUri;
    Boolean photoTaken = false;
    Bitmap photo_bitmap;


    private int position;
    private ArrayList<Mood> moodArrayList;
    private Mood mood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);

        Intent intent = getIntent();
        String target = intent.getStringExtra("MOOD");

        Gson gS = new Gson();
        mood = gS.fromJson(target,Mood.class);
        Log.i("mood:",mood.toString());

        addImageButton = (ImageButton)findViewById(R.id.addImage_bt);
        button0 = (RadioButton)findViewById(R.id.anger);
        button1 = (RadioButton)findViewById(R.id.confusion);
        button2 = (RadioButton)findViewById(R.id.disgust);
        button3 = (RadioButton)findViewById(R.id.fear);
        button4 = (RadioButton)findViewById(R.id.happy);
        button5 = (RadioButton)findViewById(R.id.sad);
        button6 = (RadioButton)findViewById(R.id.shame);
        button7 = (RadioButton)findViewById(R.id.suprise);

        button8 = (RadioButton)findViewById(R.id.alone1);
        button9 = (RadioButton)findViewById(R.id.withother1);
        button10 = (RadioButton)findViewById(R.id.moreone1);
        button11 = (RadioButton)findViewById(R.id.crowd1);
        triggerString = (EditText)findViewById(R.id.triggerView1);
        //app = (OurMoodApplication) getApplication();

        if(mood.getMood().equals("angry")){
            Log.i("motion:",mood.getMood());
            button0.setChecked(true);
        }
        if(mood.getMood().equals("confused")){
            button1.setChecked(true);
        }
        if(mood.getMood().equals("disgust")){
            button2.setChecked(true);
        }
        if(mood.getMood().equals("fear")){
            button3.setChecked(true);
        }
        if(mood.getMood().equals("happy")){
            button4.setChecked(true);
        }
        if(mood.getMood().equals("sad")){
            button5.setChecked(true);
        }
        if(mood.getMood().equals("shame")){
            button6.setChecked(true);
        }
        if(mood.getMood().equals("surprise")){
            button7.setChecked(true);
        }

        if(mood.getMood().equals("alone")){
            button8.setChecked(true);
        }
        if(mood.getSocial()!=null) {
            if (mood.getSocial().equals("with one other people")) {
                button9.setChecked(true);
            }
            if (mood.getSocial().equals("with more than one people")) {
                button10.setChecked(true);
            }
            if (mood.getSocial().equals("crowd")) {
                button11.setChecked(true);
            }
        }

        if(mood.getTrigger()!=null){
            triggerString.setText(mood.getTrigger());
        }

        if (mood.getImage() != null) {
            ImageView preview = (ImageView) findViewById(R.id.imageViewEdit);
            preview.setImageBitmap(mood.getImage());
            //photoTaken = true;
        }


        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(AddNewEvent.this, CameroActivity.class);
                //startActivity(i);\
                takephote();
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "angry";
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "confused";
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "disgust";
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "fear";
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "happy";
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "sad";
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "shame";
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motion = "surprise";
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "alone";
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "with one other people";
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "with more than one people";
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social = "crowd";
            }
        });



    }

    public void savemood(View view){

        userName = OurMoodApplication.getUsername();
        Boolean isOnline = OnlineChecker.isOnline(this);
        if (isOnline) {
            ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
            getUserTask.execute(userName);
            try {
                user = getUserTask.get();

            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }

            //Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
            moodArrayList = user.getMoodlist();
            for (int i = 0; i < moodArrayList.size(); i++) {
                //Log.i("moodtest",moodArrayList.get(i).toString());
                if (moodArrayList.get(i).toString().equals(mood.toString())) {
                    moodArrayList.remove(i);
                    if (motion != null) {
                        mood.setMood(motion);
                    }
                    trigger = triggerString.getText().toString();
                    if (trigger != null) {
                        mood.setTrigger(trigger);
                    }
                    if (social != null) {
                        mood.setSocial(social);
                    }
                    if (photoTaken) {
                        mood.setImage(photo_bitmap);
                    }
                    moodArrayList.add(mood);
                    ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                    addUserTask.execute(user);
                } else {
                    Log.i("Error", "not working");
                }

            }
        }else {
            //not online save local copy;
            OfflineMoodController OfflineController = new OfflineMoodController(userName, this);
            OfflineController.addOfflineAction("DELETE", mood);
            if (motion != null) {
                mood.setMood(motion);
            }
            trigger = triggerString.getText().toString();
            if (trigger != null) {
                mood.setTrigger(trigger);
            }
            if (social != null) {
                mood.setSocial(social);
            }
            if (photoTaken) {
                mood.setImage(photo_bitmap);
            }
            OfflineMoodController OfflineController1 = new OfflineMoodController(userName, this);
            Toast.makeText(this, "Offline Activity Saved!", Toast.LENGTH_LONG).show();
            OfflineController1.addOfflineAction("ADD", mood);

        }
        finish();
    }

    public void findlocation(View view){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), 1);
        } catch (Exception e) {
            Log.i("Error", "PlacePicker failed");
        }
    }

    private void takephote() {
        File folder = new File(getExternalCacheDir(), "my_picture.jpg");
        try {
            if (folder.exists()){
                folder.delete();
            }
            folder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageFileUri = FileProvider.getUriForFile(EditMoodActivity.this,
                    this.getApplicationContext().getPackageName()+ ".provider", folder);
        }
        else {

            imageFileUri = Uri.fromFile(folder);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, 98);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 98:
                if (resultCode == RESULT_OK){

                    try {
                        photo_bitmap = BitmapFactory.
                                decodeStream(getContentResolver().openInputStream(imageFileUri));

                        //set image preview
                        ImageView preview = (ImageView)findViewById(R.id.imageViewEdit);
                        preview.setImageBitmap(photo_bitmap);
                        photoTaken = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                    Location location;
                    location = new Location(place.getName().toString());
                    location.setLatitude(place.getLatLng().latitude);
                    location.setLongitude(place.getLatLng().longitude);

                    mood.setLocation(location);
                }
                break;
        }
    }
}
