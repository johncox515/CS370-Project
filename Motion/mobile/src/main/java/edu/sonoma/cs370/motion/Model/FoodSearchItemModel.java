package edu.sonoma.cs370.motion.Model;

import com.google.gson.annotations.SerializedName;
import edu.sonoma.cs370.motion.Model.FoodDataModel;
import edu.sonoma.cs370.motion.*;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class FoodSearchItemModel {
        @SerializedName("fields")
        public FoodDataModel foodData;

        @SerializedName("item_name")
        public String item_name;

        @SerializedName("item_id")
        public String item_id;
}
