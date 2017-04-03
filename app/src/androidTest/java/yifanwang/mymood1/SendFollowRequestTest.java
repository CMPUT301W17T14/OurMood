package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ruoyang on 4/3/17.
 */

public class SendFollowRequestTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public SendFollowRequestTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    public void testSendSucceed(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        solo.clickOnButton("Follow a new person");
        solo.assertCurrentActivity("wrong activity", AddFollowActivity.class);

        solo.enterText((EditText) solo.getView(R.id.editText3), "test1");
        solo.clickOnButton("Follow this person");
        solo.waitForText("Follow Request sends successfully");
    }

    public void testSendFailed(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        solo.clickOnButton("Follow a new person");
        solo.assertCurrentActivity("wrong activity", AddFollowActivity.class);

        solo.enterText((EditText) solo.getView(R.id.editText3), "test");
        solo.clickOnButton("Follow this person");
        solo.waitForText("You enter a wrong username");

        solo.enterText((EditText) solo.getView(R.id.editText3), "r999999999999999");
        solo.clickOnButton("Follow this person");
        solo.waitForText("The user does not exist");
    }

    public void testRequestAccpet(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        solo.clickOnButton("Follower Request");
        solo.assertCurrentActivity("wrong activity", FriendRequestActivity.class);

        solo.clickOnButton("Accept");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }

}

