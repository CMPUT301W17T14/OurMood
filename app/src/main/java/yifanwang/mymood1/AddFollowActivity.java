package yifanwang.mymood1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class AddFollowActivity extends AppCompatActivity {
    Button FollowButton;
    EditText name;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_follow);

        FollowButton= (Button) findViewById(R.id.FollowButton);
        name = (EditText) findViewById(R.id.editText3);
        
        FollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String followname = name.getText().toString();
                addFollow(followname);
            }
        });
    }
    private void addFollow(String followname) {
        String currentUserName = OurMoodApplication.getUsername();
        //Toast.makeText(this,currentUserName, Toast.LENGTH_SHORT).show();
        if (followname.equals(currentUserName)) {
            Toast.makeText(this, "You enter a wrong username", Toast.LENGTH_SHORT).show();
        }
        else {
            ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
            getUserTask.execute(followname);

            try {
                user = getUserTask.get();
                if(user != null){
                    ArrayList<String> pendingList = user.getPending();
                    ArrayList<String> followerList = user.getFollowerIDs();

                    if ( followerList.contains(currentUserName)) {
                        Toast.makeText(this, "You already followed this user", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pendingList.contains(currentUserName)) {
                            Toast.makeText(this, "You already in pending list", Toast.LENGTH_SHORT).show();
                        } else {
                            pendingList.add(currentUserName);
                            user.setPendings(pendingList);
                            ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                            addUserTask.execute(user);
                            Toast.makeText(this, "Follow Request sends successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User");
            }
        }

    }
}
