package yifanwang.mymood1;

import android.content.Context;
import android.os.Environment;

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
    }

    public int getOfflineListSize() {
        return OfflineMoodList.size();
    }

    public ArrayList<OfflineMoodModule> getOfflineList() {
        return OfflineMoodList;
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
        SaveFile();
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
    }
}
