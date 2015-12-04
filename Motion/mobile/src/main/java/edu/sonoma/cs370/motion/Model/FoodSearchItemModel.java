package edu.sonoma.cs370.motion.Model;

import com.google.gson.annotations.SerializedName;
import edu.sonoma.cs370.motion.Model.FoodDataModel;
import org.parceler.Parcel;

import java.util.ArrayList;

public class FoodSearchItemModel {
        @SerializedName("fields")
        public FoodDataModel foodData;
}
