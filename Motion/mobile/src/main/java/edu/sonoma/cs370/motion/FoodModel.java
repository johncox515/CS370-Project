package edu.sonoma.cs370.motion;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodModel {

        @SerializedName("foodLines")
        public ArrayList<String> food;

        @SerializedName("images")
        public ArrayList<ImageContainerModel> images;

        @SerializedName("name")
        public String foodName;

        @SerializedName("id")
        public String foodId;

        @SerializedName("numberOfCalories")
        public String calories;

    }