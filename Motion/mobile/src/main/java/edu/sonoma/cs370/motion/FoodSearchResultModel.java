package edu.sonoma.cs370.motion;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodSearchResultModel {

        @SerializedName("matches")
        public ArrayList<FoodSearchItemModel> results;

        @SerializedName("totalMatchCount")
        public int resultCount;
    }
