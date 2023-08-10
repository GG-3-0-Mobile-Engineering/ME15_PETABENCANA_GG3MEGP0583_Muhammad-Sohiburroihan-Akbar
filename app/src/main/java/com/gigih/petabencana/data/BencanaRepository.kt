package com.gigih.petabencana.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.gigih.petabencana.data.remote.ApiService
import com.gigih.petabencana.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BencanaRepository(
    private val apiService: ApiService,
    private val bencanaDao: BencanaDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<UiState<List<BencanaTable>>>()
    fun getDisaster(): LiveData<UiState<List<BencanaTable>>> {
        result.value = UiState.Loading
        val client = apiService.getDisaster()
        client.enqueue(object : Callback<BencanaResponse> {
            override fun onResponse(call: Call<BencanaResponse>, response: Response<BencanaResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result?.objects?.output?.geometries
                    val dataList = ArrayList<BencanaTable>()
                    appExecutors.diskIO.execute {
                        data?.forEach { bencana ->
                            val news = BencanaTable(
                                bencana?.properties!!.pkey,
                                bencana.properties.title,
                                bencana.properties.imageUrl,
                                bencana.properties.tags?.instanceRegionCode,
                                bencana.properties.disasterType,
                                bencana.coordinates?.get(0),
                                bencana.coordinates?.get(1),
                            )
                            dataList.add(news)
                        }
                        bencanaDao.deleteAll()
                        bencanaDao.insertAll(dataList)
                    }
                }
            }

            override fun onFailure(call: Call<BencanaResponse>, t: Throwable) {
                result.value = UiState.Error(t.message.toString())
            }
        })
        val localData = bencanaDao.getDisaster()
        result.addSource(localData) { newData: List<BencanaTable> ->
            result.value = UiState.Success(newData)
        }
        return result
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

        companion object {
            @Volatile
            private var instance: BencanaRepository? = null
            fun getInstance(
                apiService: ApiService,
                newsDao: BencanaDao,
                appExecutors: AppExecutors
            ) : BencanaRepository =
                instance ?: synchronized(this) {
                    instance ?: BencanaRepository(apiService,newsDao,appExecutors)
                }.also { instance = it }
        }
}