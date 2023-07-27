package com.gigih.petabencana.data.remote

import com.gigih.petabencana.data.BencanaResponse
import retrofit2.http.GET

interface ApiService {
    @GET("reports?timeperiod=604800")
    suspend fun getDisaster(): BencanaResponse
}