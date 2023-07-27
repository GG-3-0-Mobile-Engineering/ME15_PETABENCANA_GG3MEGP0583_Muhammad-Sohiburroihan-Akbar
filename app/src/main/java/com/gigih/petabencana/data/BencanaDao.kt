package com.gigih.petabencana.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BencanaDao {
    @Query("SELECT * FROM bencana")
    fun getDisaster(): LiveData<List<BencanaTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bencana: List<BencanaTable>)

    @Query("DELETE FROM bencana")
    fun deleteAll()
}