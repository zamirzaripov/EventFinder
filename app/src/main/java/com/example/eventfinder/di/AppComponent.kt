package com.example.eventfinder.di

import com.example.eventfinder.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
