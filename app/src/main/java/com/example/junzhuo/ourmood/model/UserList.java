package com.example.junzhuo.ourmood.model;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        ObjectInputStream oi = new ObjectInputStream(fis);
        try {
            users = (ArrayList<User>) oi.readObject();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Log.d("List", users.toString());
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveUserList(Context context) throws IOException {
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(users);
        outputStream.close();
    }

}
