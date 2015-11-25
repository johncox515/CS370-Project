package edu.sonoma.cs370.motion;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class FoodSearchItemModel {
        @SerializedName("id")
        public String foodId;

       @SerializedName("foodName")
       public String foodName;

        @SerializedName("smallImageUrls")
        public ArrayList<String> thumbnail;

}
