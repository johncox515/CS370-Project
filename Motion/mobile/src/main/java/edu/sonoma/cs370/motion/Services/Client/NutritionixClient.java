package edu.sonoma.cs370.motion.Services.Client;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import edu.sonoma.cs370.motion.Models.CalorieModel;
import edu.sonoma.cs370.motion.Models.CalorieSearchResultModel;
/**
 * Created by bryancarvalheira on 11/8/15.
 */
public interface NutritionixClient {
    // Defining the api endpoint calls.  The @GET annotation supplies a route that will be added to the
    // AppDefines.BASE_API_URL in the CalorieServiceClient.  The @Query annotation in the method signature provides
    // a way to pass query parameters on the api call.
    @GET("/api/search")
    Observable<CalorieSearchResultModel> getRecipesByIngredient(@Query("q") String ingredient, @Query("key") String key);

    @GET("/api/get")
    Observable<CalorieModel> getRecipeById(@Query("rId") String recipeId, @Query("key") String key);
}
