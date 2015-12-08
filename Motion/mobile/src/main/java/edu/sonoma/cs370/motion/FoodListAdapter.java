package edu.sonoma.cs370.motion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.sonoma.cs370.motion.Model.FoodSearchItemModel;

public class FoodListAdapter extends ArrayAdapter<FoodSearchItemModel> {
    public FoodListAdapter(Context context, ArrayList<FoodSearchItemModel> foods) {
        super(context, 0, foods);
    }

    public FoodListAdapter(Context context, int resource, List<FoodSearchItemModel> items) {
        super(context, resource, items);
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.food_item, null);
            }

            FoodSearchItemModel food = getItem(position);

            if (food != null) {

                TextView nameText = (TextView) v.findViewById(R.id.foodItemNameText);


                if (nameText != null) {
                    nameText.setText(food.foodData.item_name + "\n" + food.foodData.brandName);
                }


            }

            return v;
        }
    }

