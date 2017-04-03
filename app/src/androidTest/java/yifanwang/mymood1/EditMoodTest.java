package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ruoyang on 4/3/17.
 */

public class EditMoodTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public EditMoodTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    public void test0(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SignupActivity.class);

        solo.enterText((EditText) solo.getView(R.id.username_et), "c");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "c");
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
    public void testEdit(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "c");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",ShowDetailActivity.class);
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong Activity", EditMoodActivity.class);

        solo.clickOnButton("SAVE");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


    }
}
