package yifanwang.mymood1;

/**
 * Created by ruoyang on 4/3/17.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

public class IntentTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public IntentTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    //initialize two user
    public void test0(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username_et), "r0");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);

        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_et), "r1");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
    }
    //r1 send a new mood
    public void test1(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        View view = solo.getView(R.id.addnew);
        solo.clickOnView(view);
        solo.assertCurrentActivity("wrong activity", AddNewEvent.class);

        solo.clickOnRadioButton(1);
        solo.clickOnButton("SEND");
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
    }
    //r0 follow r1
    public void test2(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r0");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        solo.clickOnButton("Follow a new person");
        solo.assertCurrentActivity("wrong activity", AddFollowActivity.class);

        solo.enterText((EditText) solo.getView(R.id.editText3), "r1");
        solo.clickOnButton("Follow this person");
        solo.waitForText("Follow Request sends successfully");

    }
    //r1 accept r0
    public void test3(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        solo.clickOnButton("Follower Request");
        solo.assertCurrentActivity("wrong activity", FriendRequestActivity.class);

        solo.clickOnButton("Accept");


    }
    //r0 can check the mood of r1
    public void test4(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r0");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("All Mood");
        solo.waitForFragmentByTag("FragmentProfile");
    }


    //r1 show his mood detail
    public void test5(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowDetailActivity.class);
    }
    //r1 edit his mood
    public void test6(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowDetailActivity.class);
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong Activity", EditMoodActivity.class);

        solo.clickOnButton("SAVE");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    //r1 delete his mood
    public void test7(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "r1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowDetailActivity.class);

        solo.clickOnButton("DELETE");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }




}
