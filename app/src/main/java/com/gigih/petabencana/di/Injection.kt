package com.gigih.petabencana.di

import android.content.Context
import com.gigih.petabencana.data.BencanaDatabase
import com.gigih.petabencana.data.BencanaRepository
import com.gigih.petabencana.data.remote.ApiConfig
import com.gigih.petabencana.utils.AppExecutors

object Injection {
    fun providerRepository(context: Context) : BencanaRepository {
        val apiService = ApiConfig.getApiService()
        val database = BencanaDatabase.getInstance(context)
        val dao = database.bencanaDao()
        val appExecutors = AppExecutors()
        return BencanaRepository.getInstance(apiService, dao, appExecutors)
    }
}

//object Injection {
//    fun provideRepository(context: Context): BencanaRepository {
//        val apiService = ApiConfig.getApiService()
//        return BencanaRepository(apiService)
//    }
//}