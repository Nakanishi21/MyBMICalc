package com.example.mybmicalc.model.body

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class MyBody(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val height: Int,
    val weight: Int,
    val date: Long,
    val created: Long,
    val updated: Long
): Parcelable
