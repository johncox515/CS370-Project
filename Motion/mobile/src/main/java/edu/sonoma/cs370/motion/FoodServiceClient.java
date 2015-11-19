package edu.sonoma.cs370.motion;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

    public class FoodServiceClient {
        // A static instance iof the IRecipeProvider is created to use as a singleton
        private static IFoodProvider foodProvider;

        // A public method is created to expose the singleton and grant access to it's public methods
        public static IFoodProvider getFoodProvider() {
            // A new Retrofit object is created
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppDefines.BASE_API_URL) // a base api URL is provided
                    .addConverterFactory(GsonConverterFactory.create()) // a deserializer is specified
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // we associate rdxAndroid to handle the callback
                    .build();

            // a concrete implementation of the IRecipeProvider interfaces is dynamically created by the Retrofit
            // object
            foodProvider = retrofit.create(IFoodProvider.class);

            return foodProvider;

        }
    }

