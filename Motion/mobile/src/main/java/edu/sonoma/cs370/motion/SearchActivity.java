package edu.sonoma.cs370.motion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.parceler.Parcels;

import edu.sonoma.cs370.motion.Model.FoodSearchItemModel;
import edu.sonoma.cs370.motion.Model.FoodSearchResultModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {






    private EditText searchEditText;
    private Button searchButton;
    private ListView foodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_search);

        // Set the local instance of the controls to reference their layout counterparts.
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        searchButton = (Button)findViewById(R.id.searchButton);
        foodListView = (ListView)findViewById(R.id.foodListView);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodServiceClient.getFoodProvider()
                        .getFoodSearchResults(searchEditText.getText().toString(), AppDefines.APP_ID, AppDefines.API_KEY) // Call to the provider
                        .subscribeOn(Schedulers.newThread()) // Spinning up a new background thread to handle the call
                        .observeOn(AndroidSchedulers.mainThread()) // Setting a listener on the main thread to handle the callback
                        .subscribe(new Subscriber<FoodSearchResultModel>() { // Subscribing with the data type that we expect to be returned
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                int i = 0;
                            }

                            @Override
                            public void onNext(FoodSearchResultModel searchResultModel) {
                                // Once the result set comes back from the API call, it's handed off to an adapter for processing
                                foodListView.setAdapter(new FoodListAdapter(SearchActivity.this, searchResultModel.results));
                            }
                        });
            }
        });


        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // When the recipe item is clicked, the following code is executed
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent foodIntent = new Intent(SearchActivity.this, CalorieActivity.class);

                foodIntent.putExtra(AppDefines.FOOD_INTENT_KEY, Parcels.wrap((FoodSearchItemModel)parent.getItemAtPosition(position)));

                startActivity(foodIntent);
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
