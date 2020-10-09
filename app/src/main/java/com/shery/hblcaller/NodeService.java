package com.shery.hblcaller;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NodeService {
    @POST("/user")
    @FormUrlEncoded
    Call<Post> getResponseFromServer(@Field("baseurl") String baseurl,
                                     @Field("port") String port,
                                     @Field("username") String username);
}
