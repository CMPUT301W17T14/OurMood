package yifanwang.mymood1;

/**
 * Created by yifanwang on 2017-03-30.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;


public class MoodeventAdapter extends ArrayAdapter<Mood> {

    private ArrayList<Mood> items;
    private int layoutResourceId;
    private MoodExample example;
    private Context context;
    private int emotion;


    public MoodeventAdapter(Context context, int layoutResourceId, ArrayList<Mood> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        example = new MoodExample();
        example.mood = items.get(position);

        example.emotion = (TextView) row.findViewById(R.id.mood_emotion);
        example.date = (TextView) row.findViewById(R.id.mood_date);
        example.emoji = (ImageView) row.findViewById(R.id.mood_emoji);
        example.username = (TextView) row.findViewById(R.id.mood_username);
        example.like = (TextView) row.findViewById(R.id.mood_like);





        backgroundcolor(example,row);
        settext(example);
        return row;
    }

    private void backgroundcolor(MoodExample example, View row) {
        if (example.mood.getMood().equals("angry")) {row.setBackgroundColor(Color.parseColor("#CDC673"));}
        if (example.mood.getMood().equals("confused")) {row.setBackgroundColor(Color.parseColor("#FFD700"));}
        if (example.mood.getMood().equals("disgust")) {row.setBackgroundColor(Color.parseColor("#FA8072"));}
        if (example.mood.getMood().equals("fear")) {row.setBackgroundColor(Color.parseColor("#FF00FF"));}
        if (example.mood.getMood().equals("happy")) {row.setBackgroundColor(Color.parseColor("#C0FF3E"));}
        if (example.mood.getMood().equals("sad")) {row.setBackgroundColor(Color.parseColor("#8968CD"));}
        if (example.mood.getMood().equals("shame")) {row.setBackgroundColor(Color.parseColor("#66CDAA"));}
        if (example.mood.getMood().equals("surprise")) {row.setBackgroundColor(Color.parseColor("#ABABAB"));}
    }


    private void settext(MoodExample example) {
        String emotionString = example.mood.getMood();


        if (emotionString.equals("angry")){emotion = 1;}
        if (emotionString.equals("confused")){emotion = 2;}
        if (emotionString.equals("disgust")){emotion = 3;}
        if (emotionString.equals("fear")){emotion = 4;}
        if (emotionString.equals("happy")){emotion = 5;}
        if (emotionString.equals("sad")){emotion = 6;}
        if (emotionString.equals("shame")){emotion = 7;}
        if (emotionString.equals("surprise")){emotion = 8;}

        example.emotion.setText(emotionString);
        example.date.setText(example.mood.getDate().toString());
        example.username.setText(example.mood.getName());
        example.like.setText(Integer.toString(example.mood.getLike()));
        emotionToEmoji(example.emoji,emotion);
    }


    private void emotionToEmoji (ImageView emoji, int emotion){
        switch (emotion) {
            case 1:
                emoji.setImageResource(R.drawable.angry);
                break;
            case 2:
                emoji.setImageResource(R.drawable.confused);
                break;
            case 3:
                emoji.setImageResource(R.drawable.disgust);
                break;
            case 4:
                emoji.setImageResource(R.drawable.afraid);
                break;
            case 5:
                emoji.setImageResource(R.drawable.timg);
                break;
            case 6:
                emoji.setImageResource(R.drawable.sad);
                break;
            case 7:
                emoji.setImageResource(R.drawable.shame);
                break;
            case 8:
                emoji.setImageResource(R.drawable.surprise);
                break;
            default:
                break;
        }
    }


}