package yifanwang.mymood1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    EditText triggerString;

    Uri imageFileUri;
    Boolean photoTaken = false;
    Bitmap photo_bitmap;

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

        //camera
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(AddNewEvent.this, CameroActivity.class);
                //startActivity(i);\
                takephote();
            }
        });

        //clean motion
        motion = "";

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

        social = "";

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

    public void send(View view){

        //check motion

        if (motion.equals("") ) {
            Toast.makeText(this, "You need select motion!", Toast.LENGTH_SHORT).show();
            return;
        }



        triggerString = (EditText)findViewById(R.id.triggerView);
        reason = triggerString.getText().toString();

        if (reason.equals("")){
            Toast.makeText(this, "You need entry reason!", Toast.LENGTH_SHORT).show();
            return;
        }

        userName = OurMoodApplication.username;
        Mood mood = new Mood(userName, motion);

        //Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
        mood.setTrigger(reason);
        mood.setSocial(social);

        //phote
        if (photoTaken) {
            mood.setImage(photo_bitmap);
        }
        Boolean isOnline = OnlineChecker.isOnline(this);
        if (isOnline) {
            ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
            getUserTask.execute(userName);

            try {
                user = getUserTask.get();
                user.addMood(mood);
                ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                addUserTask.execute(user);
                //Toast.makeText(this, "New mood adds successfully", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }

            //set mood propoties


        }else{
            //not online save local copy;
            OfflineMoodController OfflineController = new OfflineMoodController(userName, this);
            OfflineController.addOfflineAction("ADD", mood);
            Toast.makeText(this, "Offline Activity Saved!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void findlocation(View view){
        Intent intent = new Intent(this,SeeMapActivity.class);
        startActivity(intent);

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
            imageFileUri = FileProvider.getUriForFile(AddNewEvent.this,
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
                        ImageView preview = (ImageView)findViewById(R.id.imageView);
                        preview.setImageBitmap(photo_bitmap);
                        photoTaken = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}