package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by zheng on 2017-03-13.
 */

public class SigninActivityTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public SigninActivityTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }



    public void testSignUpSuccess() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username_et), "asdf");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupsuccessActivity.class);
        solo.clickOnButton("Startsurfing!");
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
    }
    public void testSignUpFailed() {
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
    public void testSignInSucceed() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void testSignInFailed() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Username cannot be empty"));

        solo.enterText((EditText) solo.getView(R.id.editText), "A B");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForText("Username cannot contain space"));
    }

    public void testConvertToMap() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImage(2);
        solo.assertCurrentActivity("Wrong Activity", SeeMapActivity.class);
    }

    public void testFilter() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImage(1);
        solo.assertCurrentActivity("wrong activity", Fliter.class);
    }

    /*
    public void testShowDetail() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowDetailActivity.class);
    }
    */
    public void testMood(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Mood Event");
        solo.waitForFragmentByTag("FragmentMood");
    }
    public void testnearby() {
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Nearby");
        solo.waitForFragmentByTag("NearbyFragment");
    }
    public void testSearchFriend(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Follow");
        solo.waitForFragmentByTag("FragmentFollow");
    }
    public void testProfile(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");
    }
    public void testhistory(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");
        solo.clickOnButton("My Mood Events History");
        solo.waitForText("My Mood History");
    }
    public void testRequest(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");
        solo.clickOnButton("Follower Request");
        solo.waitForText("ACCEPT");
    }

    public void testEdit(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");
        solo.clickOnImageButton(1);
        solo.assertCurrentActivity("wrong activity",AddNewEvent.class);
        solo.clickOnButton("SEND");
        solo.assertCurrentActivity("wrong activity",MainActivity.class);
    }
    /*
    public void testLocation(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");
        solo.clickOnImageButton(1);
        solo.assertCurrentActivity("wrong activity",AddNewEvent.class);
        solo.clickOnImageButton(2);
        solo.assertCurrentActivity("wrong activity",SeeMapActivity.class);
    }
    */
    public void testFilter2(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "asdf");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");
        solo.clickOnButton("My Mood Events History");
        solo.waitForText("My Mood History");
        solo.clickOnButton("Filter");
        solo.waitForText("View Last Week");

    }



}