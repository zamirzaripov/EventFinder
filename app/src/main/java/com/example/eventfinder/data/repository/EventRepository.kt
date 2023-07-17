package com.example.eventfinder.data.repository

import android.util.Log
import com.example.eventfinder.data.api.EventApiService
import com.example.eventfinder.data.database.dao.EventDao
import com.example.eventfinder.data.model.Event

class EventRepository(private val apiService: EventApiService, private val eventDao: EventDao) {
    suspend fun getEvents(): List<Event> {

        return try {
            val response = apiService.getEvents()

            if (response.isSuccessful) {
                val events = response.body()?.data ?: emptyList()
                eventDao.insertEvents(events)
                events
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("FFFFF", "getEvents: ${e.message}")
            emptyList()
        }
    }

    suspend fun getCachedEvents(): List<Event> {
        return eventDao.getEvents()
    }
}
