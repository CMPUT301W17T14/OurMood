package yifanwang.mymood1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Filter2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter2);
    }
    public void confirmfliter(View view){
        Intent intent = new Intent(this,MyHistoryActivity.class);
        startActivity(intent);

    }
}
