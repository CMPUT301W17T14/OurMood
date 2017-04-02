package yifanwang.mymood1;

import android.location.Location;
import android.media.Image;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ruoyang on 2/22/17.
 */
public class Mood {
    private String uuid;
    private String name;
    private String mood;
    private String trigger;
    private String social;
    private Image image;
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
    public void addLike(){
        this.like+=1;
    }
    @Override
    public String toString() {
        return "Mood{" +
                "name='" + name + '\'' +
                ", mood='" + mood + '\'' +
                ", trigger='" + trigger + '\'' +
                ", social='" + social + '\'' +
                ", image=" + image +
                ", date=" + date +
                ", location=" + location +
                '}';
    }

    public String getUUID() {return uuid;}
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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


    public Image getImage() {
        return image;
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


    public void setImage(Image image) {
        this.image = image;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}