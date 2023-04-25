package com.example.mybmicalc.repository

import com.example.mybmicalc.model.body.MyBody
import com.example.mybmicalc.model.body.MyBodyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyBodyRepositoryImpl @Inject constructor(
    private val dao: MyBodyDao
): MyBodyRepository {
    override fun getAllDate(): Flow<List<MyBody>> {
        return dao.getAllDate()
    }

    override suspend fun create(height: Int, weight: Int, date: Long) {
        val now = System.currentTimeMillis()
        val myBody = MyBody(
            height = height,
            weight = weight,
            date = date,
            created = now,
            updated = now
        )
        withContext(Dispatchers.IO) {
            dao.create(myBody)
        }
    }

    override suspend fun update(myBody: MyBody, height: Int, weight: Int, date: Long): MyBody {
        val now = System.currentTimeMillis()
        val updateMyBody = MyBody(
            _id = myBody._id,
            height = height,
            weight = weight,
            date = date,
            created = myBody.created,
            updated = now
        )
        withContext(Dispatchers.IO) {
            dao.update(updateMyBody)
        }
        return updateMyBody
    }

    override suspend fun delete(myBody: MyBody) {
        dao.delete(myBody)
    }
}