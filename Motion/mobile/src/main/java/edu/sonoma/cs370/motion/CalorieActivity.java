package edu.sonoma.cs370.motion;

import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CalorieActivity extends AppCompatActivity {

    private ImageView foodImage;
    private ListView foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);


        foodImage = (ImageView) findViewById(R.id.foodImage);
        foodList = (ListView) findViewById(R.id.foodList);


        FoodSearchItemModel foodItem =
                (FoodSearchItemModel) Parcels.unwrap(this.getIntent().getParcelableExtra(AppDefines.FOOD_INTENT_KEY));

        setTitle(foodItem.foodName);

        FoodServiceClient.getFoodProvider()
                .getFoodById(foodItem.foodId, AppDefines.APP_ID, AppDefines.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FoodModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) { int i = 0; }

                    @Override
                    public void onNext(FoodModel foodModel) {
                        // Handle the results
                        if (foodModel != null) {
                            Picasso.with(getBaseContext()).load(foodModel.images.get(0).imageUrl).resize(900, 600).into(foodImage);
                            // Create an inline adapter instead of using an inheriting class
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                    CalorieActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    foodModel.food); //missing variable

                            foodList.setAdapter(arrayAdapter);

                        } else {
                            // handle null
                        }
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



