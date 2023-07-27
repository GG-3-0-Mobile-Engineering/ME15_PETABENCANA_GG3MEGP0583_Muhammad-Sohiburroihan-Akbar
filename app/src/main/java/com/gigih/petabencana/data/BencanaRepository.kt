package com.gigih.petabencana.data

import com.gigih.petabencana.data.remote.ApiService

class BencanaRepository(
    private val apiService: ApiService,
//    private val bencanaDao: BencanaDao,
//    private val appExecutors: AppExecutors
) {
    suspend fun getDisaster(): UiState<BencanaResponse> {
        return try {
            val response = apiService.getDisaster()
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error repository getdisaster")
        }
    }

    suspend fun getDisasterByLocation(location: String): UiState<BencanaResponse> {
        return try {
            val response = apiService.getDisasterByLocation(location = location)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error repository getDisasterByLocation")
        }
    }

    suspend fun getDisasterByType(type: String): UiState<BencanaResponse> {
        return try {
            val response = apiService.getDisasterByLocation(type)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error repository getDisasterByLocation")
        }
    }

//        companion object {
//            @Volatile
//            private var instance: BencanaRepository? = null
//            fun getInstance(
//                apiService: ApiService,
//                newsDao: BencanaDao,
//                appExecutors: AppExecutors
//            ) : BencanaRepository =
//                instance ?: synchronized(this) {
//                    instance ?: BencanaRepository(apiService,newsDao,appExecutors)
//                }.also { instance = it }
//        }
}