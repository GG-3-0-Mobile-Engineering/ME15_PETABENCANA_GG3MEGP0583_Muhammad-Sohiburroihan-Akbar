package com.gigih.petabencana.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BencanaTable::class],
    version = 1,
    exportSchema = false
)
abstract class BencanaDatabase : RoomDatabase() {
    abstract fun bencanaDao(): BencanaDao

    companion object {
        @Volatile
        private var instance: BencanaDatabase? = null
        fun getInstance(context: Context): BencanaDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BencanaDatabase::class.java, "bencana.db"
                ).build()
            }
    }
}