package com.gigih.petabencana.data.remote

import com.gigih.petabencana.data.BencanaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports?")
    suspend fun getDisaster(): BencanaResponse
//    @GET("reports?timeperiod=604800")

    @GET("reports")
    suspend fun getDisasterByLocation(
        @Query("admin") location: String): BencanaResponse

    @GET("reports")
    suspend fun getDisasterByType(
        @Query("admin") type: String): BencanaResponse

}