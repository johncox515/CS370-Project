package edu.sonoma.cs370.motion.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

import edu.sonoma.cs370.motion.*;

/**
 * Created by bundleofivan on 11/24/15.
 */
@Parcel
public class FoodDataModel {

//    @SerializedName("nf_calories")
//    public ArrayList<String> calories;

    @SerializedName("item_id")
    public String item_id;

    @SerializedName("item_name")
    public String item_name;

    @SerializedName("nf_calories")
    public String calories;


}
