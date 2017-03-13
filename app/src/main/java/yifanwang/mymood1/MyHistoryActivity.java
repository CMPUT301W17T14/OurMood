package yifanwang.mymood1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
    }
    public void Filter(View view){
        Intent intent = new Intent(this,Filter2.class);
        startActivity(intent);

    }
    public void SeeInMap1(View view) {
        Intent intent = new Intent(this, SeeMapActivity.class);
        startActivity(intent);
    }
}
