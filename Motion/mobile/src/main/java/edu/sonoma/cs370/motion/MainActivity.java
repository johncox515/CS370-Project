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
import android.widget.ListView;

import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "edu.sonoma.cs370.motion.MESSAGE";
//    private TextView date;
//    private TextView time;
//    private TextView miles;
    private ListView totalStats;
    private TextView totalCalories;

    //private ArrayList<HashMap<String, String>> list;

    MotionDbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        date = (TextView) findViewById(R.id.dateView);
//        time = (TextView) findViewById(R.id.timeView);
//        miles = (TextView) findViewById(R.id.milesView);
        //totalStats = (ListView) findViewById(R.id.totalStatsView);
        totalCalories = (TextView) findViewById(R.id.totalCalories);
        ListView listView = (ListView)findViewById(R.id.totalStatsView);
        /*list = new ArrayList<HashMap<String, String>>();

        HashMap<String,String> temp=new HashMap<String, String>();
        temp.put(Date, "Ankit Karia");
        temp.put(Time, "Male");
        temp.put(Miles, "22");
        list.add(temp);

        HashMap<String,String> temp2=new HashMap<String, String>();
        temp2.put(Date, "Rajat Ghai");
        temp2.put(Time, "Male");
        temp2.put(Miles, "25");
        list.add(temp2);

        HashMap<String,String> temp3=new HashMap<String, String>();
        temp3.put(Date, "Karina Kaif");
        temp3.put(Time, "Female");
        temp3.put(Miles, "31");
        list.add(temp3);

        HashMap<String,String> temp4=new HashMap<String, String>();
        temp4.put(Date, "Karina Kaif");
        temp4.put(Time, "Female");
        temp4.put(Miles, "31");
        list.add(temp4);

        HashMap<String,String> temp5=new HashMap<String, String>();
        temp5.put(Date, "Karina Kaif");
        temp5.put(Time, "Female");
        temp5.put(Miles, "31");
        list.add(temp5);*/
        mydb = new MotionDbHelper(this);

        mydb.getTotalStats();
        ListViewAdapter adapter=new ListViewAdapter(this, MotionDbHelper.list);
        listView.setAdapter(adapter);

        //ArrayList<String> values = mydb.getTotalStats();
        //ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        //totalStats.setAdapter(adapter);
        //totalCalories.setText(String.valueOf(mydb.getCaloriesCount()).replace(",", "\n").replace("[", " ").replace("]", "\n").trim());
//        date.setText(String.valueOf(mydb.getDate()).replace(",", " ").replace("[", " ").replace("]", "\n").trim());
//        time.setText(String.valueOf(mydb.getTime()).replace(",", ":").replace("[", "").replace("]", "\n").trim());
//        miles.setText(String.valueOf(mydb.getMiles()).replace(",", "\n").replace("[", " ").replace("]", "\n").trim());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void goToSettingsActivity(View v)
    {
       Intent intent = new Intent(this,Profile.class);
       //EditText editText = (EditText) findViewById(R.id.edit_message);
       // String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void goToRunning(View v)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void goToCalorie(View v)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}
