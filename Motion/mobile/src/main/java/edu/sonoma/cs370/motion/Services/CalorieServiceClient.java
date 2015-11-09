package edu.sonoma.cs370.motion.Services;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by bryancarvalheira on 11/8/15.
 */
public class CalorieServiceClient {
    // A static instance if the NutritionixClient is created to use as a singleton
    private static NutritionixClient calorieProvider;

    // A public method is created to expose the singleton and grant access to it's public methods
    public static NutritionixClient getCalorieProvider() {
        // A new Retrofit object is created
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDefines.BASE_API_URL) // a base api URL is provided
                .addConverterFactory(GsonConverterFactory.create()) // a deserializer is specified
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // we associate rdxAndroid to handle the callback
                .build();

        // a concrete implementation of the NutritionixClient interfaces is dynamically created by the Retrofit
        // object
        calorieProvider = retrofit.create(NutritionixClient.class);

        return calorieProvider;
    }




}
