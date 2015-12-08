package edu.sonoma.cs370.motion;

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
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "edu.sonoma.cs370.motion.MESSAGE";
    private TextView date;
    private TextView time;
    private TextView miles;
    private TextView totalCalories;

    MotionDbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = (TextView) findViewById(R.id.dateView);
        time = (TextView) findViewById(R.id.timeView);
        miles = (TextView) findViewById(R.id.milesView);
        totalCalories = (TextView) findViewById(R.id.totalCalories);

        mydb = new MotionDbHelper(this);

        date.setText(String.valueOf(mydb.getDate()).replace(",", " ").replace("[", " ").replace("]", "\n").trim());
        time.setText(String.valueOf(mydb.getTime()).replace(",", ":").replace("[", "").replace("]", "\n").trim());
        miles.setText(String.valueOf(mydb.getMiles()).replace(",", "\n").replace("[", " ").replace("]", "\n").trim());
        totalCalories.setText(String.valueOf(mydb.gettotalCalories()).replace(",", "\n").replace("[", " ").replace("]", "\n").trim());
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
       Intent intent = new Intent(this,Settings.class);
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
