package com.example.eventfinder.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventfinder.data.model.Event
import com.example.eventfinder.data.repository.EventRepository
import com.example.eventfinder.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val _events = MutableLiveData<Resource<List<Event>>>()
    val events: LiveData<Resource<List<Event>>> get() = _events

    private val _cachedEvents = MutableLiveData<List<Event>>()
    val cachedEvents: LiveData<List<Event>> get() = _cachedEvents

    init {
        viewModelScope.launch {
            _events.value = Resource.Loading

            try {
                val events = repository.getEvents()
                _events.value = events

                updateCachedEvents()
            } catch (e: Exception) {
                _events.value = Resource.Error("Ошибка загрузки данных")
            }
        }
    }

    private suspend fun updateCachedEvents() {

        val cachedEvents = repository.getCachedEvents()

        _cachedEvents.value = cachedEvents
    }
}
