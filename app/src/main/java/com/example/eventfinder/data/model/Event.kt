package com.example.eventfinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val endDate: String,
    val icon: String,
    val name: String,
    val startDate: String,
    val url: String,
)