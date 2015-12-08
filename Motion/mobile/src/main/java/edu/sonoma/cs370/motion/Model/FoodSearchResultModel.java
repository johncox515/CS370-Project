package edu.sonoma.cs370.motion.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import edu.sonoma.cs370.motion.Model.FoodSearchItemModel;

public class FoodSearchResultModel {
        @SerializedName("hits")
        public ArrayList<FoodSearchItemModel> results;

    }
