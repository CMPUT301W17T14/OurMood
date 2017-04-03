package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ruoyang on 4/3/17.
 */

public class SignInTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public SignInTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    //initialize a user
    public void testBegin(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username_et), "test1");
        solo.clickOnButton("Sign Up");
    }

    public void testSignInSucceed() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    //test error checking
    public void testSignInFailed1() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Username cannot be empty"));

        solo.enterText((EditText) solo.getView(R.id.editText), "A B");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Username cannot contain space"));



    }
    public void testSignInFailed2() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "");
        solo.clickOnButton("Sign In");

        solo.enterText((EditText) solo.getView(R.id.editText), "test2");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("User has not been signed up"));
    }


}
