package com.example.mybmicalc

import android.content.Context
import androidx.room.Room
import com.example.mybmicalc.model.body.MyBMICalcDatabase
import com.example.mybmicalc.model.body.MyBodyDao
import com.example.mybmicalc.repository.MyBodyRepository
import com.example.mybmicalc.repository.MyBodyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyBMICalcObject {
    @Singleton
    @Provides
    fun provideMyBMICalcDatabase(@ApplicationContext context: Context): MyBMICalcDatabase {
        return Room.databaseBuilder(
            context,
            MyBMICalcDatabase::class.java,
            "mybmicalc.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMyBodyDao(db: MyBMICalcDatabase): MyBodyDao {
        return db.myBodyDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MyBodyRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMyBodyRepository(impl: MyBodyRepositoryImpl): MyBodyRepository
}