package yifanwang.mymood1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNewEvent extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    ImageView IMG;
    Bitmap imageBitmap;

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

        IMG = (ImageView) findViewById(R.id.imageView);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager())!= null)
                {
                    startActivityForResult(i, REQUEST_CODE);
                }
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

    public void send(View view){
        userName = OurMoodApplication.username;
        Mood mood = new Mood(userName,motion);

        triggerString = (EditText)findViewById(R.id.triggerView);
        reason = triggerString.getText().toString();

        //Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
        mood.setTrigger(reason);
        mood.setSocial(social);

        ImageView picture = (ImageView) findViewById(R.id.imageView);
        //Log.d("Tetestestst", "is this drawable or na" + picture.getDrawable()  );
        if (picture.getDrawable() != null) {
            // (128 * 128) * 4 = 65536 bytes which is the maximum allowed
            // limits every bitmap image to 65536 bytes.
            Bitmap convertedImage = getResizedBitmap(imageBitmap, 128, 128);
            convertedImage.getByteCount();
            Log.d("Test", "This is convertedImage byte count!" +convertedImage.getByteCount() );
            mood.setImage(convertedImage);
            System.out.println(mood);
        }

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(userName);

        try {
            user = getUserTask.get();
            user.addMood(mood);
            ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
            addUserTask.execute(user);
            //Toast.makeText(this, "New mood adds successfully", Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }



        //set mood propoties
        finish();
    }

    public void findlocation(View view){
        Intent intent = new Intent(this,SeeMapActivity.class);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                // (128 * 128) * 4 = 65536 bytes which is the maximum allowed
                Bitmap convertedImage = getResizedBitmap(imageBitmap, 128, 128);
                convertedImage.getByteCount();
                Log.d("Test", "This is convertedImage byte count!" +convertedImage.getByteCount() );
                IMG.setImageBitmap(convertedImage);
            }
        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxWidth, int maxHeight) {

        int width = maxWidth;
        int height = maxHeight;

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
