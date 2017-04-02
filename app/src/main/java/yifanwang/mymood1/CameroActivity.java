package yifanwang.mymood1;

import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
//import yifanwang.mymood1.R;

public class CameroActivity extends AppCompatActivity {

    Uri imageFileUri;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camero);
        ImageButton ib = (ImageButton) findViewById(R.id.takeImage_ib);
        ib.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    takeAPhoto();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private File createImageFile2() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCameraTest";
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
        String imagePathAndFileName = path + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(imagePathAndFileName);
        return imageFile;
    }
    public void takeAPhoto() throws IOException {
        imageFileUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", createImageFile());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == 1){
            TextView tv = (TextView)findViewById(R.id.textView1);
            if (resultCode == RESULT_OK){
                Log.v("123", "reutn;");
                tv.setText("Photo completed!");
                ImageButton ib = (ImageButton)findViewById(R.id.takeImage_ib);
                ib.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
            }
            else
            if (resultCode == RESULT_CANCELED)
                tv.setText("Photo was canceled!");
            else
                tv.setText("What happened?!!");
        }
    }
}
