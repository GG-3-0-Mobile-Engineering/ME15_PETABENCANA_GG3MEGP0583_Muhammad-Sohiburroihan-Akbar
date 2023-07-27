package com.gigih.petabencana.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.gigih.petabencana.data.remote.ApiService
import com.gigih.petabencana.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            UiState.Error(e.message ?: "Unknown error")
        }
    }

//    private val result = MediatorLiveData<Result<List<BencanaTable>>>()
//
//    fun getHeadlineNews(): LiveData<Result<List<BencanaTable>>> = liveData {
//        result.value = Result.Loading
//        val client = apiService.getDisaster()
//        client.enqueue(object : Callback<BencanaResponse> {
//            override fun onResponse(call: Call<BencanaResponse>, response: Response<BencanaResponse>) {
//                if (response.isSuccessful) {
//                    val disaster = response.body()?.result?.objects?.output?.geometries
//                    Log.d("gigih", disaster.toString())
//                    val bencanaList = ArrayList<BencanaTable>()
//                    appExecutors.diskIO.execute {
//                        disaster?.forEach { bencana ->
//                            val dis = BencanaTable(
//                                bencana?.properties?.pkey.toString(),
//                                bencana?.properties?.title,
//                                bencana?.properties?.imageUrl,
//                                bencana?.properties?.tags?.instanceRegionCode,
//                                bencana?.properties?.disasterType,
//                                bencana?.coordinates?.get(0),
//                                bencana?.coordinates?.get(1)
//                            )
//                            bencanaList.add(dis)
//                        }
//                        bencanaDao.deleteAll()
//                        bencanaDao.insertAll(bencanaList)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BencanaResponse>, t: Throwable) {
//                result.value = Result.Error(t.message.toString())
//            }
//        })
//        val localData = bencanaDao.getDisaster()
//        result.addSource(localData) { newData: List<BencanaTable> ->
//            result.value = Result.Success(newData)
//            Log.d("repository", result.value.toString())
//        }
//    }

//    fun getData() = bencanaDao.getDisaster()

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