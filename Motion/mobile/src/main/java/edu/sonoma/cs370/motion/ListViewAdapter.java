package edu.sonoma.cs370.motion;

import static edu.sonoma.cs370.motion.Constants.Date;
import static edu.sonoma.cs370.motion.Constants.Time;
import static edu.sonoma.cs370.motion.Constants.Miles;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.column_listview, null);

            txtFirst=(TextView) convertView.findViewById(R.id.date);
            txtSecond=(TextView) convertView.findViewById(R.id.time);
            txtThird=(TextView) convertView.findViewById(R.id.miles);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(Date));
        txtSecond.setText(map.get(Time));
        txtThird.setText(map.get(Miles));

        return convertView;
    }

}