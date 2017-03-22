package yifanwang.mymood1;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by junzhuo on 3/19/17.
 */

public class MoodList {
    private static String FILE_NAME = "moodlist.json";
    private static String OFFLINE_NAME = "moodlist_offline.json";

    ArrayList<Mood> MoodList = new ArrayList<Mood>();
    ArrayList<Mood> MoodList_offline = new ArrayList<Mood>();

    private Boolean mood_inited = false;
    private Boolean mood_offline_inited = false;

    public void addMood(Mood mood, Context context, Boolean offline) {
        try {
            loadMoodList(context, offline);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (offline) {
            MoodList_offline.add(mood);
        }else {
            MoodList.add(mood);
        }

        try {
            this.saveMoodList(context, offline);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mood getMood(String uuid, Context context, Boolean offline) {
        try {
            loadMoodList(context, offline);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Mood m:MoodList) {
            if (m.getUUID().equals(uuid)) {
                return m;
            }
        }

        if (offline) {
            for (Mood m:MoodList_offline) {
                if (m.getUUID().equals(uuid)) {
                    return m;
                }
            }
        }

        return null;
    }

    public Mood updateMood(Mood mood, Context context, Boolean offline) {
        Boolean found = false;
        try {
            loadMoodList(context, offline);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //del old mood;
        for (Mood m:MoodList) {
            if (m.getUUID().equals(mood.getUUID())) {
                MoodList.remove(m);
                found = true;
                break;
            }
        }

        if (offline && !found) {
            for (Mood m:MoodList_offline) {
                if (m.getUUID().equals(mood.getUUID())) {
                    MoodList_offline.remove(m);
                    found = true;
                    break;
                }
            }
        }

        if (found) {
            this.addMood(mood, context, offline);
        }
        return null;
    }

    public ArrayList<Mood> getMoodLists(Context context, Boolean offline) {
        try {
            loadMoodList(context, offline);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Mood> ret = new ArrayList<Mood>(MoodList);
        if (offline) {
            ret.addAll(MoodList_offline);
        }

        return ret;
    }

    private void loadOfflineMoodList(Context context) throws  IOException {
        if (this.mood_offline_inited) {
            return;
        }
        FileInputStream fis = context.openFileInput(OFFLINE_NAME);

        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        Gson gson = new Gson();

        MoodList_offline = gson.fromJson(in, new TypeToken<ArrayList<Mood>>() {}.getType());

        fis.close();
    }

    public void loadMoodList(Context context, Boolean offline) throws  IOException {
        if (offline) {
            this.loadOfflineMoodList(context);
        }

        if (this.mood_inited) {
            return;
        }
        FileInputStream fis = context.openFileInput(FILE_NAME);

        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        Gson gson = new Gson();

        MoodList = gson.fromJson(in, new TypeToken<ArrayList<Mood>>() {}.getType());

        fis.close();
    }

    private void saveOfflineMoodList(Context context, Boolean offline) throws IOException {
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(OFFLINE_NAME, Context.MODE_PRIVATE);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream));
        Gson gson = new Gson();

        gson.toJson(MoodList_offline, out);

        out.flush();
        outputStream.close();
    }

    private void saveMoodList(Context context, Boolean offline) throws IOException {
        if (offline) {
            this.saveOfflineMoodList(context, offline);
        }
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream));
        Gson gson = new Gson();

        gson.toJson(MoodList, out);

        out.flush();
        outputStream.close();
    }
}
