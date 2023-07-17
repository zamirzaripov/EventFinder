package com.example.eventfinder.di

import android.content.Context
import androidx.room.Room
import com.example.eventfinder.data.api.EventApiService
import com.example.eventfinder.data.database.AppDatabase
import com.example.eventfinder.data.database.dao.EventDao
import com.example.eventfinder.data.repository.EventRepository
import com.example.eventfinder.ui.viewmodels.EventViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://guidebook.com/service/v2/"

    @Provides
    @Singleton
    fun provideEventApiService(): EventApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "events"
        ).build()
    }

    @Provides
    fun provideEventDao(database: AppDatabase): EventDao {
        return database.eventDao()
    }

    @Provides
    @Singleton
    fun provideEventRepository(apiService: EventApiService, eventDao: EventDao): EventRepository {
        return EventRepository(apiService, eventDao)
    }

    @Provides
    fun provideEventViewModelFactory(repository: EventRepository): EventViewModelFactory {
        return EventViewModelFactory(repository)
    }
}
