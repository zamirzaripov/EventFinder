package com.example.eventfinder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eventfinder.data.database.dao.EventDao
import com.example.eventfinder.data.model.Event

@Database(entities = [Event::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}
