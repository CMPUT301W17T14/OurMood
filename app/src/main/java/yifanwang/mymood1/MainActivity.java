package yifanwang.mymood1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
//extends Application
public class MainActivity extends Activity implements OnClickListener {

    public final static String EXTRA_USERNAME_MSG = "com.app.username";
    private String username;
    private User user;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private LinearLayout llMoodEvent, llNearby, llSearchF, llProfile;
    private ImageView ivMoodEvent, ivNearby, ivSearchF, ivProfile, ivCurrent;
    private TextView tvMoodEvent, tvNearby, tvSearchF, tvProfile, tvCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String target = intent.getStringExtra("USER");
        Gson gS = new Gson();
        user=gS.fromJson(target,User.class);
        username=user.getUsername();

        OurMoodApplication app = (OurMoodApplication) getApplication();
        OurMoodApplication.username = username;
        app.setFilter(new Filter_data());

        initView();

        initData();

        //sync offline data
        if (OnlineChecker.isOnline(this)) {
            OfflineMoodController of = new OfflineMoodController(username, this);
            of.SyncOfflineAction();
        }

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        llMoodEvent = (LinearLayout) findViewById(R.id.llMoodEvent);
        llNearby = (LinearLayout) findViewById(R.id.llNearby);
        llSearchF = (LinearLayout) findViewById(R.id.llSearchF);
        llProfile = (LinearLayout) findViewById(R.id.llProfile);

        llMoodEvent.setOnClickListener(this);
        llNearby.setOnClickListener(this);
        llSearchF.setOnClickListener(this);
        llProfile.setOnClickListener(this);

        ivMoodEvent = (ImageView) findViewById(R.id.ivMoodEvent);
        ivNearby = (ImageView) findViewById(R.id.ivNearby);
        ivSearchF = (ImageView) findViewById(R.id.ivSearchF);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);

        tvMoodEvent = (TextView) findViewById(R.id.tvMoodEvent);
        tvNearby = (TextView) findViewById(R.id.tvNearby);
        tvSearchF = (TextView) findViewById(R.id.tvSearchF);
        tvProfile = (TextView) findViewById(R.id.tvProfile);

        ivMoodEvent.setSelected(true);
        tvMoodEvent.setSelected(true);
        ivCurrent = ivMoodEvent;
        tvCurrent = tvMoodEvent;

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    private void initData() {
        Fragment chatFragment = new MoodeventFragment();
        Fragment friendsFragment = new NearbyFragment();
        Fragment contactsFragment = new AllMoodeventFragment();
        Fragment settingsFragment = new ProfileFragment();

        fragments.add(chatFragment);
        fragments.add(friendsFragment);
        fragments.add(contactsFragment);
        fragments.add(settingsFragment);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getFragmentManager(),fragments);
//      MyFragmentStatePagerAdapter adapter = new MyFragmentStatePagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    private void changeTab(int id) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {
            case R.id.llMoodEvent:
                viewPager.setCurrentItem(0);
            case 0:
                ivMoodEvent.setSelected(true);
                ivCurrent = ivMoodEvent;
                tvMoodEvent.setSelected(true);
                tvCurrent = tvMoodEvent;
                break;
            case R.id.llNearby:
                viewPager.setCurrentItem(1);
            case 1:
                ivNearby.setSelected(true);
                ivCurrent = ivNearby;
                tvNearby.setSelected(true);
                tvCurrent = tvNearby;
                break;
            case R.id.llSearchF:
                viewPager.setCurrentItem(2);
            case 2:
                ivSearchF.setSelected(true);
                ivCurrent = ivSearchF;
                tvSearchF.setSelected(true);
                tvCurrent = tvSearchF;
                break;
            case R.id.llProfile:
                viewPager.setCurrentItem(3);
            case 3:
                ivProfile.setSelected(true);
                ivCurrent = ivProfile;
                tvProfile.setSelected(true);
                tvCurrent = tvProfile;
                break;
            default:
                break;
        }
    }

}