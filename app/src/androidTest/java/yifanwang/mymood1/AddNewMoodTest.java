package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ruoyang on 4/3/17.
 */

public class AddNewMoodTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public AddNewMoodTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAddFailed(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Profile");
        solo.waitForFragmentByTag("FragmentProfile");

        View view = solo.getView(R.id.addnew);
        solo.clickOnView(view);
        solo.assertCurrentActivity("wrong activity", AddNewEvent.class);

        solo.clickOnButton("SEND");
        assertTrue(solo.waitForText("Please enter a motion"));
    }
    public void testAddSucceed(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test");
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
}

