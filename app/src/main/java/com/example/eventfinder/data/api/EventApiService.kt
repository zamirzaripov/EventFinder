package com.example.eventfinder.data.api

import com.example.eventfinder.data.model.AllEvents
import retrofit2.Response
import retrofit2.http.GET

interface EventApiService {
    @GET("upcomingGuides")
    suspend fun getEvents(): Response<AllEvents>
}