package yifanwang.mymood1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ruoyang on 2/22/17.
 */

/**
 * this model contains username,user mood
 * user trigger,user image ,user location,
 * current date and how people like this
 * mood
 */
public class Mood {
    private String name;
    private String mood;
    private String trigger;
    private String social;
    private String imagedata;
    private Boolean withimage = false;
    private Date date;
    private Location location;
    private int like;

    public Mood(String name, String mood){

        this.name = name;
        this.mood = mood;
        this.date = new Date();

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Mood{" +
                "name='" + name + '\'' +
                ", mood='" + mood + '\'' +
                ", trigger='" + trigger + '\'' +
                ", social='" + social + '\'' +
                //", image=" + withimage.toString() +
                ", date=" + date +
                ", location=" + location +
                '}';
    }


    public int getLike() {
        return like;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getMood() {
        return mood;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getSocial() {
        return social;
    }


    public Bitmap getImage() {
        Bitmap decodedByte;
        if (withimage) {
            byte[] decodedString = Base64.decode(imagedata, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                    decodedString.length);
        }else{
            //rturn default.
            decodedByte = null;
        }
        return decodedByte;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public void setLike(int like){
        this.like = like;
    }


    public void setImage(Bitmap image) {
        //Bitmap bm = BitmapFactory.decodeFile("/path/to/image.jpg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        imagedata = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
        this.withimage = true;
    }



    public void setLocation(Location location) {
        this.location = location;
    }


}