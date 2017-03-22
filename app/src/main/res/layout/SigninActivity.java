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


public class SigninActivity extends AppCompatActivity {
    Button signinButton, signupButton;
    EditText et;
    UserList userList = new UserList();
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
    }
    private void initView() {
        signinButton = (Button) findViewById(R.id.signin_bt);
        signupButton = (Button) findViewById(R.id.signup_bt);
        et = (EditText) findViewById(R.id.editText);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et.getText().toString();
                User u = new User(username);
                if(checkUsernameLegal(username)) {
                    if (userList.checkUserExisted(u, context)) {
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.EXTRA_USERNAME_MSG, u.getUsername());
                        startActivity(intent);
                    } else {
                        makeTost("User Not Existed");
                    }
                }
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","2");
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
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

