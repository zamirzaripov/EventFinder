package com.example.eventfinder.data.repository

import com.example.eventfinder.data.api.EventApiService
import com.example.eventfinder.data.database.dao.EventDao
import com.example.eventfinder.data.model.Event
import com.example.eventfinder.util.Resource
import java.io.IOException
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: EventApiService,
    private val eventDao: EventDao
) {
    suspend fun getEvents(): Resource<List<Event>> {

        return try {
            val response = apiService.getEvents()

            if (response.isSuccessful) {
                val events = response.body()?.data ?: emptyList()
                eventDao.deleteAllEvents()
                eventDao.insertEvents(events)
                Resource.Success(events)
            } else {
                Resource.Error("Ошибка загрузки данных")
            }

        } catch (e: IOException) {
            Resource.Error("Пожалуйста, проверьте соеденение с интернетом")
        } catch (e: Exception) {
            Resource.Error("Что-то пошло не так")
        }
    }

    suspend fun getCachedEvents(): List<Event> {
        return eventDao.getEvents()
    }
}
