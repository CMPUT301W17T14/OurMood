package yifanwang.mymood1;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by ruoyang on 4/3/17.
 */

public class ShowAllTest extends ActivityInstrumentationTestCase2<SigninActivity> {
    private Solo solo;

    public ShowAllTest() {
        super(yifanwang.mymood1.SigninActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    public void testShowAll(){
        solo.assertCurrentActivity("wrong activity", SigninActivity.class);
        solo.enterText((EditText) solo.getView(R.id.editText), "test1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText("All Mood");
        solo.waitForFragmentByTag("FragmentMood");

    }
}
