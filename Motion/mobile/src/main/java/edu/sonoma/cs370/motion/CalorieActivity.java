package edu.sonoma.cs370.motion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import edu.sonoma.cs370.motion.Model.FoodDataModel;
import edu.sonoma.cs370.motion.Model.FoodSearchItemModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CalorieActivity extends AppCompatActivity {

    private ListView NutritionList;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);
        Log.d("tag", "inside CalorieActivity onCreate");
        Log.d("Food intent key", AppDefines.FOOD_INTENT_KEY);

        //NutritionList = (ListView) findViewById(R.id.NutritionList);
        test = (TextView) findViewById(R.id.test);

        final FoodSearchItemModel foodItem =
                Parcels.unwrap(this.getIntent().getParcelableExtra(AppDefines.FOOD_INTENT_KEY));

        Log.d("unwrapped string", String.valueOf(foodItem.foodData.item_name));
        Log.d("calories", String.valueOf(foodItem.foodData.calories));

        //test.setText(foodItem.foodData.item_name);
        setTitle(foodItem.foodData.item_name);


        //getting Food data using the ID...

        FoodServiceClient.getFoodProvider()
                .getFoodById(foodItem.foodData.item_id, AppDefines.APP_ID, AppDefines.API_KEY)
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

//                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                                    CalorieActivity.this,
//                                    android.R.layout.simple_list_item_1,
//                                    foodDataModel.calories);

                            //NutritionList.setAdapter(arrayAdapter);

                            Log.d("calories", String.valueOf(foodDataModel.calories));
                            test.setText(String.valueOf(foodDataModel.calories));
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


