package com.example.mybmicalc.model.body

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyBody::class], version = 1)
abstract class MyBMICalcDatabase : RoomDatabase() {
    abstract fun myBodyDao(): MyBodyDao
}