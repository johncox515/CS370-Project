package edu.sonoma.cs370.motion;

import edu.sonoma.cs370.motion.Model.FoodSearchResultModel;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface FoodProvider {

    @GET("/v1_1/search/{phrase}")
    Observable<FoodSearchResultModel> getFoodSearchResults(@Path("phrase") String searchTerm,
                                                             @Query("appId") String appId,
                                                             @Query("appKey") String key);


}