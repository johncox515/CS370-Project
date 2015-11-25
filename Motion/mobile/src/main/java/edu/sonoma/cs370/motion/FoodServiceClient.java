package edu.sonoma.cs370.motion;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

    public class FoodServiceClient {

        private static IFoodProvider foodProvider;


        public static IFoodProvider getFoodProvider() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppDefines.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();


            foodProvider = retrofit.create(IFoodProvider.class);

            return foodProvider;

        }
    }

