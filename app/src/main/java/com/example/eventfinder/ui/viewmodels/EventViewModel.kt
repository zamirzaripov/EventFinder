package com.example.eventfinder.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventfinder.data.model.Event
import com.example.eventfinder.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    init {
        viewModelScope.launch {
            _events.value = repository.getCachedEvents()
            fetchEvents()
        }
    }

    private suspend fun fetchEvents() {
        try {
            val events = repository.getEvents()

            Log.d("AAAAAA", events.toString())

            _events.postValue(events)
        } catch (e: Exception) {
            // Handle the error here and show an error message to the user
        }
    }


}
