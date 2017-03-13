package yifanwang.mymood1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private LinearLayout llChat, llFriends, llContacts, llSettings;
    private ImageView ivChat, ivFriends, ivContacts, ivSettings, ivCurrent;
    private TextView tvChat, tvFriends, tvContacts, tvSettings, tvCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        llChat = (LinearLayout) findViewById(R.id.llChat);
        llFriends = (LinearLayout) findViewById(R.id.llFriends);
        llContacts = (LinearLayout) findViewById(R.id.llContacts);
        llSettings = (LinearLayout) findViewById(R.id.llSettings);

        llChat.setOnClickListener(this);
        llFriends.setOnClickListener(this);
        llContacts.setOnClickListener(this);
        llSettings.setOnClickListener(this);

        ivChat = (ImageView) findViewById(R.id.ivChat);
        ivFriends = (ImageView) findViewById(R.id.ivFriends);
        ivContacts = (ImageView) findViewById(R.id.ivContacts);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);

        tvChat = (TextView) findViewById(R.id.tvChat);
        tvFriends = (TextView) findViewById(R.id.tvFriends);
        tvContacts = (TextView) findViewById(R.id.tvContacts);
        tvSettings = (TextView) findViewById(R.id.tvSettings);

        ivChat.setSelected(true);
        tvChat.setSelected(true);
        ivCurrent = ivChat;
        tvCurrent = tvChat;

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
        Fragment friendsFragment = new ProfileFragment();
        Fragment contactsFragment = new SearchfriendFragment();
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
            case R.id.llChat:
                viewPager.setCurrentItem(0);
            case 0:
                ivChat.setSelected(true);
                ivCurrent = ivChat;
                tvChat.setSelected(true);
                tvCurrent = tvChat;
                break;
            case R.id.llFriends:
                viewPager.setCurrentItem(1);
            case 1:
                ivFriends.setSelected(true);
                ivCurrent = ivFriends;
                tvFriends.setSelected(true);
                tvCurrent = tvFriends;
                break;
            case R.id.llContacts:
                viewPager.setCurrentItem(2);
            case 2:
                ivContacts.setSelected(true);
                ivCurrent = ivContacts;
                tvContacts.setSelected(true);
                tvCurrent = tvContacts;
                break;
            case R.id.llSettings:
                viewPager.setCurrentItem(3);
            case 3:
                ivSettings.setSelected(true);
                ivCurrent = ivSettings;
                tvSettings.setSelected(true);
                tvCurrent = tvSettings;
                break;
            default:
                break;
        }
    }

}