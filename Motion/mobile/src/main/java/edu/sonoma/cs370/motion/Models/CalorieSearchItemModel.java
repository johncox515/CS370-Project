package edu.sonoma.cs370.motion.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by bryancarvalheira on 11/8/15.
 */
@Parcel
public class CalorieSearchItemModel {
    @SerializedName("recipe_id")
    public String recipeId;

    @SerializedName("title")
    public String recipeName;

    @SerializedName("f2f_url")
    public String recipeUrl;

    @SerializedName("image_url")
    public String thumbnail;
}
