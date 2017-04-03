package yifanwang.mymood1;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by junzhuo on 4/2/17.
 */

public class OfflineMoodController {

    private static final String FILEPREFIX = "offline_";
    private static String username;
    Context context;
    ArrayList<OfflineMoodModule> OfflineMoodList;

    public OfflineMoodController(String username, Context context){
        this.context = context;
        CreateOrLoadList(username);

    }

    public void addOfflineAction(String action, Mood mood) {
        OfflineMoodModule of = new OfflineMoodModule();
        of.action = action;
        of.mood = mood;

        OfflineMoodList.add(of);
        SaveFile();
    }

    public int getOfflineListSize() {
        return OfflineMoodList.size();
    }

    public ArrayList<OfflineMoodModule> getOfflineList() {
        return OfflineMoodList;
    }

    public Boolean SyncOfflineAction() {
        Log.i("Offline", "Offline Count: " + getOfflineListSize());
        if (getOfflineListSize() == 0) {
            return true;
        }

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(username);
        User user;
        try {
             user = getUserTask.get();
        }catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
            return false;
        }

        for(OfflineMoodModule mm: OfflineMoodList) {
            doSync(user, mm);
        }

        ElasticsearchController.AddUserTask addUserTask = new ElasticsearchController.AddUserTask();
        addUserTask.execute(user);

        File cachedfile = new File(context.getExternalCacheDir(), FILEPREFIX + username);
        cachedfile.delete();

        Log.i("Offline", "Offline Data synced.");
        Toast.makeText(context,
                "Offline Data synced.",
                Toast.LENGTH_SHORT).show();
        return true;
    }

    private void doSync(User user, OfflineMoodModule m) {
        if (m.action.equals("ADD")) {
            user.addMood(m.mood);
        }else if(m.action.equals("DELETE")) {
            user.deleteMood(m.mood);
        }else if(m.action.equals("EDIT")) {
            //pass
        }

    }

    private void CreateOrLoadList(String username){
        //find saved list.
        File cachedfile = new File(context.getExternalCacheDir(), FILEPREFIX + username);
        if (!cachedfile.exists()) {
            OfflineMoodList = new ArrayList<OfflineMoodModule>();
        }else {
            ReadFile(cachedfile);
        }

        this.username = username;
    }

    private void ReadFile(File fp) {
        try {
            FileInputStream fis = new FileInputStream(fp);

            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            OfflineMoodList = gson.fromJson(in, new TypeToken<ArrayList<OfflineMoodModule>>() {}.getType());
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SaveFile() {
        try {

            File cachedfile = new File(context.getExternalCacheDir(), FILEPREFIX + username);
            FileOutputStream outputStream = new FileOutputStream(cachedfile);
            //outputStream = context.openFileOutput(OFFLINE_NAME, Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream));
            Gson gson = new Gson();

            gson.toJson(OfflineMoodList, out);
            out.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("Offline", "Offline Data Saved, Count: " + getOfflineListSize());
    }
}
