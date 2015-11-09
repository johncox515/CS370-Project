package edu.sonoma.cs370.motion.Models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by bryancarvalheira on 11/8/15.
 */
public class CalorieSearchResultModel {
    @SerializedName("calories")
    public ArrayList<CalorieSearchItemModel> calories;
}
