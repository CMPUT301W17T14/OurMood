package yifanwang.mymood1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by junzhuo on 3/19/17.
 */

public class MoodlistAdpater extends ArrayAdapter<Mood> {
    public MoodlistAdpater (Context context, ArrayList<Mood> moodlist) {
        super(context, 0, moodlist);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Mood mood = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_moodlist, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.name_mood);
        textView.setText(mood.getName() + ": " + mood.getMood());

        DateFormat df = new SimpleDateFormat("HH:mm");

        TextView timeview = (TextView) convertView.findViewById(R.id.time);
        timeview.setText(df.format(mood.getDate()));

        df = new SimpleDateFormat("MM/dd/yyyy");
        TextView dateview = (TextView) convertView.findViewById(R.id.date);
        dateview.setText(df.format(mood.getDate()));


//        Button delete = (Button) convertView.findViewById(R.id.del);
//        Button edit = (Button) convertView.findViewById(R.id.edit);
//
//        delete.setTag(position);
//        edit.setTag(position);
        textView.setTag(position);

//        textView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                int position = (Integer) view.getTag();
//                Person person = getItem(position);
//
//                Log.d("VIEW: ", String.valueOf(position) + ", " + person.getName());
//                Intent intent = new Intent(view.getContext(), addPerson.class);
//                intent.putExtra(MainActivity.EXTRA_MESSAGE, "view:" + person.getName());
//                ((Activity) view.getContext()).startActivityForResult(intent, 1);
//            }
//        });

        return convertView;
    }
}
