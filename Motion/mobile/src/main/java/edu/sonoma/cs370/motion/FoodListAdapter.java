package edu.sonoma.cs370.motion;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

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
                ImageView foodImage = (ImageView) v.findViewById(R.id.foodItemImage);


                if (nameText != null) {
                    nameText.setText(food.foodName);
                }


                if (foodImage != null){
                    Picasso.with(getContext()).load(food.thumbnail.get(0)).resize(200, 200).into(foodImage);
                }
            }

            return v;
        }
    }

