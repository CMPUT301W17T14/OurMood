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

import java.util.ArrayList;


public class SigninActivity extends AppCompatActivity {
    Button signinButton, signupButton;
    EditText et;
    ArrayList<User> userlist = new ArrayList<User>();
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
        /**
         * if the sign in button is pressed,
         * we check if the server contains this user.
         * if the user is in the list, we jump to main.
         * if the user is not in the list, we tell the
         * user to sign up a new user.
         */

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et.getText().toString();
                if(checkUsernameLegal(username)) {
                    ElasticsearchController.IsExist isExist= new ElasticsearchController.IsExist();
                    isExist.execute(username);
                    try{
                        if(isExist.get()){
                            //makeTost("sign in success");
                            User u = new User(username);
                            Intent intent = new Intent(context, MainActivity.class);
                            Gson gS = new Gson();
                            String target = gS.toJson(u);
                            intent.putExtra("USER",target);
                            startActivity(intent);
                        }
                        else{
                            makeTost("User has not been signed up");
                        }
                    }catch (Exception e) {
                        Log.i("Error", "Failed to get the tweets out of the async object");
                    }

                }

            }
        });
        /**
         * if the sign up button is pressed,
         * we jump to sign up activity.
         */
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

