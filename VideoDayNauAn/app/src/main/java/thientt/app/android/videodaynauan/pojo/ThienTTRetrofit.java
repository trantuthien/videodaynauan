package thientt.app.android.videodaynauan.pojo;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ThienTTRetrofit {

//    @FormUrlEncoded
//    @POST("/service/event")
//    Call<String> createGamePlay(@Field("gameplay") String gameplay);

    @GET("/api-nauan.php/clip")
    Call<String> getLinkServer(@Query("transform") int transform);

    @GET("/api-nauan.php/category")
    Call<String> getCategoryServer(@Query("transform") int transform);

//    @FormUrlEncoded
//    @POST("/service/like")
//    Call<String> likeEvent(@Field("id") int id, @Field("like") int like);


}

