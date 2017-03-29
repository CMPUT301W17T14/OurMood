package yifanwang.mymood1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class FriendRequestActivity extends AppCompatActivity {

    private ArrayList<String> followList;
    private ArrayList<String> followerList;
    private ArrayList<String> notificationList;
    private ArrayList<String> anotherfollowList;
    private ListView followListView;
    private ListView followerListView;
    private ListView notificationListView;

    private String currentUserName;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");

        String query = currentUserName;
        ElasticsearchController.GetUserTask getUserTask =
                new ElasticsearchController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

 //       followerList = user.getFollowerIDs();
      //  followList = user.getFolloweeIDs();
        try{
            notificationList = user.getPendingPermission();
            final ArrayAdapter<User> adapter = new NotificationListAdapter(this, notificationList);
            notificationListView = (ListView) findViewById(R.id.notificationList);
            notificationListView.setAdapter(adapter);
        }catch (Exception e) {
            Log.i("Error", "null");
        }
    }



    /**
     * This is the NotificationList adapter for the activity.
     * It adapts the list view whenever the view has been changed,
     * i.e. a new notification has been added into the list. And It
     * also includes two buttons which are acceptBtn and declineBtn,
     */

    private class NotificationListAdapter extends ArrayAdapter<User> {
        public NotificationListAdapter(Context context, ArrayList notificationList) {
            super(context, R.layout.list_notification_item, notificationList);
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_notification_item, parent, false);

            final String singleNotification = notificationList.get(position).toString();
            final TextView notificationName = (TextView) view.findViewById(R.id.notificationID);
            notificationName.setText(singleNotification);
            Button acceptBtn = (Button) view.findViewById(R.id.acceptBtn);
            Button declineBtn = (Button) view.findViewById(R.id.declineBtn);
            acceptBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    followerList.add(singleNotification);
                    notificationList.remove(position);
                    ArrayAdapter<User> adapter = new NotificationListAdapter(FriendRequestActivity.this,
                            notificationList);
                    notificationListView.setAdapter(adapter);

                    ElasticsearchController.AddUserTask addUserTask =
                            new ElasticsearchController.AddUserTask();
                    addUserTask.execute(user);

                    User anotherUser = new User(singleNotification);

                    ElasticsearchController.GetUserTask getUserTask =
                            new ElasticsearchController.GetUserTask();
                    getUserTask.execute(singleNotification);
                    try{
                        anotherUser = getUserTask.get();
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the User out of the async object");
                    }

                    anotherfollowList = anotherUser.getFolloweeIDs();
                    anotherfollowList.add(currentUserName);


                    ElasticsearchController.AddUserTask addUserTask2 =
                            new ElasticsearchController.AddUserTask();
                    addUserTask2.execute(anotherUser);


                    Toast.makeText(getApplicationContext(), singleNotification +
                            " has been accepted", Toast.LENGTH_SHORT).show();
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    notificationList.remove(position);
                    ArrayAdapter<User> adapter = new NotificationListAdapter(FriendRequestActivity.this,
                            notificationList);
                    notificationListView.setAdapter(adapter);

                    ElasticsearchController.AddUserTask addUserTask =
                            new ElasticsearchController.AddUserTask();
                    addUserTask.execute(user);

                    Toast.makeText(getApplicationContext(), singleNotification+
                            " has been declined", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }

    protected void onStart(){
        super.onStart();
    }
}
