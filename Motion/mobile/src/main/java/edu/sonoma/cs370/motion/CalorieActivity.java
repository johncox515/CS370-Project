package edu.sonoma.cs370.motion;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.parceler.Parcels;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CalorieActivity extends AppCompatActivity {
    // In the Activity that backs the layout (activity_search), create private instances of controls
    // to act as references to those declared in the layout.  These controls have null values until
    // they are set to point at the actual controls in the layout instance.
    private EditText searchEditText;
    private Button searchButton;
    private ListView calorieListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_calorie);

        // Set the local instance of the controls to reference their layout counterparts.
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        searchButton = (Button)findViewById(R.id.searchButton);
        calorieListView = (ListView)findViewById(R.id.calorieListView);

        // A listener needs to be created to handle the button click. The body of this listener will
        // contain the invocation of the RecipeServiceClient
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalorieServiceClient.getCalorieProvider()
                        .getCalorieByFood(searchEditText.getText().toString(), AppDefines.API_KEY) // Call to the provider
                        .subscribeOn(Schedulers.newThread()) // Spinning up a new background thread to handle the call
                        .observeOn(AndroidSchedulers.mainThread()) // Setting a listener on the main thread to handle the callback
                        .subscribe(new Subscriber<CalorieSearchResultModel>() { // Subscribing with the data type that we expect to be returned

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                int i = 0;
                            }

                            @Override
                            public void onNext(CalorieSearchResultModel searchResultModel) {
                                // Once the result set comes back from the API call, it's handed off to an adapter for processing
                                CalorieListView.setAdapter(new CalorieListAdapter(SearchActivity.this, searchResultModel.calories));
                            }
                        });
            }
        });

        // An on-click listener for the items in the CalorieListView
        CalorieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // When the recipe item is clicked, the following code is executed
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create an intent to contain the relevant recipe item data and to specify the activity to load
                Intent calorieIntent = new Intent(SearchActivity.this, RecipeActivity.class);
                // Wrap up the CalorieSearchItemModel using Parcels (another Gradle dependency)
                calorieIntent.putExtra(AppDefines.CALORIE_INTENT_KEY, Parcels.wrap((CalorieSearchItemModel) parent.getItemAtPosition(position)));
                // Start the intended activity using the intent
                startActivity(calorieIntent);
            }
        });
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

}
