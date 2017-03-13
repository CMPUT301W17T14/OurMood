package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by junzhuo on 3/11/17.
 */

public class SignupsuccessActivity extends AppCompatActivity {
    Button startSurfing;
    TextView et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupsuccess);
        initView();
        Intent intent = getIntent();
        et.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
    }

    private void initView() {
        startSurfing = (Button) findViewById(R.id.startsurfring);
        startSurfing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupsuccessActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        et = (TextView) findViewById(R.id.username_display_et);
    }
}