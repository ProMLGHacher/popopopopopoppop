package com.example.uktgfmyapplication;

import retrofit.http.GET;
import retrofit.http.Query;

public interface RouteApiInterface {
    @GET("/maps/api/directions/json")

    RouteResponse getRoute(
            @Query(value = "origin", encodeValue = false) String position,
            @Query(value = "destination", encodeValue = false) String destination,
            @Query("key") String key);
}
