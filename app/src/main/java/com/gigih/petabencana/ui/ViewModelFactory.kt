package com.gigih.petabencana.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gigih.petabencana.data.BencanaRepository
import com.gigih.petabencana.di.Injection
import com.gigih.petabencana.ui.home.DIsasterViewModel

//class ViewModelFactory private constructor(private val bencanaRepository: BencanaRepository) :
//    ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DIsasterViewModel::class.java)) {
//            return DIsasterViewModel(bencanaRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: ViewModelFactory? = null
//        fun getInstance(context: Context): ViewModelFactory =
//            instance ?: synchronized(this) {
//                instance?: ViewModelFactory(Injection.providerRepository(context))
//            }.also { instance = it }
//    }
//}

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DIsasterViewModel::class.java)) {
            return DIsasterViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}