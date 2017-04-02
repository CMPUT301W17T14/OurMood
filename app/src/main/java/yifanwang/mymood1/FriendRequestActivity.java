package yifanwang.mymood1;

import android.content.Context;
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
import java.util.concurrent.ExecutionException;

public class FriendRequestActivity extends AppCompatActivity {

    private ArrayList<String> followList;
    private ArrayList<String> followerList;
    private ArrayList<String> requestList;
    private ArrayList<String> anotherfollowList;
    private ListView followListView;
    private ListView followerListView;
    private ListView requestListView;

    private String currentUserName;
    private User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        currentUserName = OurMoodApplication.getUsername();
        String query = currentUserName;

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(query);

        try {
            user = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        followerList = user.getFollowerIDs();
        followList = user.getFolloweeIDs();
        requestList = user.getPending();
        //System.out.println(requestList);
        try{
            if(user != null) {

                final ArrayAdapter<User> adapter = new requestListAdapter(this, requestList);
                requestListView = (ListView) findViewById(R.id.requestList);
                requestListView.setAdapter(adapter);
            }
            else {
                makeToast("???");
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User");
        }
    }

    private void makeToast(String s) {
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }


    /**
     * This is a list adapter for the activity.
     * When a new followrequest has been added into the list, it
     * also includes two buttons which are accept and decline.
     */

    private class requestListAdapter extends ArrayAdapter<User> {
        public requestListAdapter(Context context, ArrayList requestList) {
            super(context, R.layout.list_notification_item, requestList);
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_notification_item, parent, false);
            final String singleNotification = requestList.get(position).toString();
            final TextView notificationName = (TextView) view.findViewById(R.id.notificationID);
            notificationName.setText(singleNotification);
            Button AcceptButton = (Button) view.findViewById(R.id.AcceptButton);
            Button DeclineButton = (Button) view.findViewById(R.id.DeclineButton);
            AcceptButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    followerList.add(singleNotification);
                    requestList.remove(position);
                    ArrayAdapter<User> adapter = new requestListAdapter(FriendRequestActivity.this,
                            requestList);
                    requestListView.setAdapter(adapter);

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

            DeclineButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    requestList.remove(position);
                    ArrayAdapter<User> adapter = new requestListAdapter(FriendRequestActivity.this,
                            requestList);
                    requestListView.setAdapter(adapter);

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
