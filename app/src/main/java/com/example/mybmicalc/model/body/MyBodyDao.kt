package com.example.mybmicalc.model.body

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MyBodyDao {

    @Query("select * from MyBody order by date desc")
    fun getAllDate(): Flow<List<MyBody>>

    @Insert
    suspend fun create(myBody: MyBody)

    @Update
    suspend fun update(myBody: MyBody)

    @Delete
    suspend fun delete(myBody: MyBody)
}