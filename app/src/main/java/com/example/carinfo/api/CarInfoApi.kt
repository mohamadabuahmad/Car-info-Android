package com.example.carinfo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CarInfoApi {
    @GET("datastore_search")
    fun getCarInfo(
        @Query("resource_id") resourceId: String,
        @Query("q") query: String
    ): Call<CarInfoResponse>
}
