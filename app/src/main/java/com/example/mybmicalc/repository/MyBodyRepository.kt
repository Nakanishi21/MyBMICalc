package com.example.mybmicalc.repository

import com.example.mybmicalc.model.body.MyBody
import kotlinx.coroutines.flow.Flow

interface MyBodyRepository {
    fun getAllDate(): Flow<List<MyBody>>
    suspend fun create(height: Int, weight: Int, date: Long)
    suspend fun update(myBody: MyBody, height: Int, weight: Int, date: Long) : MyBody
    suspend fun delete(myBody: MyBody)
}