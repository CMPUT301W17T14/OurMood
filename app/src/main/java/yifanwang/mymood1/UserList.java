package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
/**
 * Created by junzhuo on 3/12/17.
 */
public class UserList {
    private static String FILE_NAME = "USERLIST.json";
    ArrayList<User> users = new ArrayList<User>();
    //Gson gson = new Gson();
    public void addUser(User u, Context context) {
        users.add(u);
        try {
            this.saveUserList(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkUserExisted(User u, Context context) {
        try {
            loadUserList(context);
        } catch (FileNotFoundException e) {
            Log.d("File", "No existed");
            return false;
        } catch (IOException e){
            e.printStackTrace();
        }
        Log.d("User", users.toString());
        return users.indexOf(u) != -1;
    }

    public void loadUserList(Context context) throws  IOException {
        FileInputStream fis = context.openFileInput(FILE_NAME);
        //ObjectInputStream oi = new ObjectInputStream(fis);

        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        Gson gson = new Gson();

        users = gson.fromJson(in, new TypeToken<ArrayList<User>>() {}.getType());

        fis.close();
    }
//    private void saveUserList(Context context) throws IOException {
//        FileOutputStream outputStream;
//        outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//        ObjectOutputStream out = new ObjectOutputStream(outputStream);
//        out.writeObject(users);
//        outputStream.close();
//    }
    private void saveUserList(Context context) throws IOException {
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream));
        Gson gson = new Gson();

        gson.toJson(users, out);

        out.flush();
        outputStream.close();
    }
}

