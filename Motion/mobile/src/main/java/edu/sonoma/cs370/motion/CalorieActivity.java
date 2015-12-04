package edu.sonoma.cs370.motion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ListView;

import org.parceler.Parcels;

import edu.sonoma.cs370.motion.Model.FoodSearchItemModel;


public class CalorieActivity extends AppCompatActivity {

    MotionDbHelper mydb;
    private ListView foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);
        mydb = new MotionDbHelper(this);
        foodList = (ListView) findViewById(R.id.foodList);
        FoodSearchItemModel foodItem = (FoodSearchItemModel) Parcels.unwrap(this.getIntent().getParcelableExtra(AppDefines.FOOD_INTENT_KEY));
        setTitle(foodItem.foodData.item_name);
    }

}


