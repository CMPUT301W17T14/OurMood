package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ruoyang on 4/3/17.
 */

public class SignUpTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public SignUpTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    public void testSignUpFailed1() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username_et), "");
        solo.clickOnButton("Sign Up");
        assertTrue(solo.waitForText("Username cannot be empty"));

        solo.enterText((EditText) solo.getView(R.id.username_et), "A B");
        solo.clickOnButton("Sign Up");
        assertTrue(solo.waitForText("Username cannot contain space"));

    }
    public void testSignUpFailed2() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_et), "test");

        solo.clickOnButton("Sign Up");
        assertTrue(solo.waitForText("Username already existed"));
    }
    public void testSignUpSucceed(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username_et), "popo");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
    }



}
