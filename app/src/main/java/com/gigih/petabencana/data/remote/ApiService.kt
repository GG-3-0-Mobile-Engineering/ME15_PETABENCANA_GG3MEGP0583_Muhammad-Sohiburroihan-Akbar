package com.gigih.petabencana.data.remote

import com.gigih.petabencana.data.BencanaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports?")
    fun getDisaster(): retrofit2.Call<BencanaResponse>
//    @GET("reports?timeperiod=604800")

    @GET("reports")
    suspend fun getDisasterByLocation(
        @Query("admin") location: String): BencanaResponse

    @GET("reports")
    suspend fun getDisasterByType(
        @Query("admin") type: String): BencanaResponse

}