package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


public class SignupActivity extends AppCompatActivity {
    Button Sign_up;
    EditText username;
    //private ArrayList<User> userlist = new ArrayList<User>();
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    private void initView() {
        Sign_up = (Button) findViewById(R.id.startsurfring);
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                /**
                 * we check if the username has been used or not
                 * if it's used, we tell the user to create a new username.
                 * if it's not used,we check if the username is legal or not.
                 * then we initialize a user and then upload this user to server.
                 *
                 */
                if(checkUsernameLegal(usernameStr)){
                    ElasticsearchController.IsExist isExist= new ElasticsearchController.IsExist();
                    isExist.execute(usernameStr);
                    try{
                        if(isExist.get()){
                            makeTost("Username already existed");
                        }
                        else{
                            User u = new User(usernameStr);
                            ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
                            addUserTask.execute(u);

                            //Intent intent = new Intent(context, SignupsuccessActivity.class);
                            //Gson gS = new Gson();
                            //String target = gS.toJson(u);
                            //intent.putExtra("USER",target);
                            //startActivity(intent);

                            makeTost("Username has signed up success");
                            finish();

                        }
                    }catch (Exception e) {
                        Log.i("Error", "Failed to get the tweets out of the async object");
                    }
                }

            }
        });
        username = (EditText) findViewById(R.id.username_et);
    }
    private boolean checkUsernameLegal(String username) {
        if(username == null || username.equals("")){
            makeTost("Username cannot be empty");
            return false;
        }
        if(username.contains(" ")){
            makeTost("Username cannot contain space");
            return false;
        }
        return true;
    }
    private void makeTost(String msg) {
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

