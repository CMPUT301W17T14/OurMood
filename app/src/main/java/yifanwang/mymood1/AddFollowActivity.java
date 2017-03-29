package yifanwang.mymood1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class AddFollowActivity extends AppCompatActivity {
    Button FollowButton;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_follow);

        FollowButton= (Button) findViewById(R.id.FollowButton);
        name = (EditText) findViewById(R.id.editText3);
        
        FollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String followername = name.getText().toString();
                addFollow(followername);
            }
        });
    }
    private void addFollow(String followername) {
        // if name = currentUserName
        String currentUserName = OurMoodApplication.username;
        if (followername.equals(currentUserName)) {
            Toast.makeText(this, "You enter wrong username", Toast.LENGTH_SHORT).show();
        }

        else {
            ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
            getUserTask.execute(followername);

            try {
                User user = getUserTask.get();
                if(user != null){
                    ArrayList<String> pendingList = user.getPendingPermission();
                    ArrayList<String> followerList = user.getFollowerIDs();

                    if ( followerList.contains(currentUserName)) {
                        Toast.makeText(this, "You already followed this user", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pendingList.contains(currentUserName)) {
                            Toast.makeText(this, "You already in pending list", Toast.LENGTH_SHORT).show();
                        } else {
                            pendingList.add(currentUserName);
                            user.setPendingPermissions(pendingList);
                            ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                            addUserTask.execute(user);
                        }
                    }

                } else {
                    Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //Log.i("Error", "Failed to get the User out of the async object");
            }
        }

    }
}
