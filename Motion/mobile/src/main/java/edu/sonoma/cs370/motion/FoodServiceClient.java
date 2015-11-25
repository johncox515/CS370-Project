package edu.sonoma.cs370.motion;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

    public class FoodServiceClient {

        private static FoodProvider foodProvider;


        public static FoodProvider getFoodProvider() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppDefines.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();


            foodProvider = retrofit.create(FoodProvider.class);

            return foodProvider;

        }
    }

