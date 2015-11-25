package edu.sonoma.cs370.motion;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface IFoodProvider {
    // Defining the api endpoint calls.  The @GET annotation supplies a route that will be added to the
    // AppDefines.BASE_API_URL in the RecipeServiceClient.  The @Query annotation in the method signature provides
    // a way to pass query parameters on the api call.  Passing in a value of "steak" would yield a
    // url of: http://www.food2fork.com/api/search?q=steak
    @GET("/v1/api/foods")
    Observable<FoodSearchResultModel> getFoodSearchResults(@Query("q") String searchTerm,
                                                             @Query("_app_id") String appId,
                                                             @Query("_app_key") String key);

    @GET("/v1_1/brand/{id}")
    Observable<FoodModel> getFoodById(@Path("food-id") String foodId,
                                        @Query("_app_id") String appId,
                                        @Query("_app_key") String key);

}