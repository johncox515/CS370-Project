package edu.sonoma.cs370.motion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.Parcels;

import edu.sonoma.cs370.motion.Model.FoodDataModel;
import edu.sonoma.cs370.motion.Model.FoodSearchItemModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CalorieActivity extends AppCompatActivity {

    private ListView NutritionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);


        NutritionList = (ListView) findViewById(R.id.NutritionList);


        final FoodSearchItemModel foodItem =
                (FoodSearchItemModel) Parcels.unwrap(this.getIntent().getParcelableExtra(AppDefines.FOOD_INTENT_KEY));

        setTitle(foodItem.item_name);


        //getting Food data using the ID...

        FoodServiceClient.getFoodProvider()
                .getFoodById(foodItem.item_id, AppDefines.APP_ID, AppDefines.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FoodDataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int i = 0;
                    }

                    @Override
                    public void onNext(FoodDataModel foodDataModel) {
                        // Handle the results
                        if (foodDataModel != null) {

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                    CalorieActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    foodDataModel.calories);

                            NutritionList.setAdapter(arrayAdapter);


                        }
                        else {}
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food, menu);
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


