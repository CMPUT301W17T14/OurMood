package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {
    Button Sign_up;
    EditText username;
    UserList userList = new UserList();
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    private void initView() {
        Sign_up = (Button) findViewById(R.id.startsurfring);
        ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
        //addUserTask.execute()
        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                User u = new User(username.getText().toString());
                if(checkUsernameLegal(usernameStr)){
                    if(!userList.checkUserExisted(u, context)) {
                        userList.addUser(u,context);
                        //makeTost("Signup successfully");
                        Intent i = new Intent(context, SignupsuccessActivity.class);
                        i.setAction(Intent.ACTION_SEND);
                        i.putExtra(Intent.EXTRA_TEXT, usernameStr);
                        i.setType("text/plain");
                        startActivity(i);
                    }else{
                        makeTost("Username already existed");
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

