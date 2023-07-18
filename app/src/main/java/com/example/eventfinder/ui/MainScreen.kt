package com.example.eventfinder.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eventfinder.data.model.Event
import com.example.eventfinder.ui.theme.MainRed
import com.example.eventfinder.ui.viewmodels.EventViewModel
import com.example.eventfinder.util.Resource
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainScreen(viewModel: EventViewModel, navController: NavController) {
    val eventsState by viewModel.events.observeAsState()
    var isToastNotShown by remember { mutableStateOf(true) }

    when (eventsState) {
        is Resource.Success -> {
            val events = (eventsState as Resource.Success<List<Event>>).data
            EventList(events, navController)
        }

        is Resource.Error -> {
            val cachedEvents = viewModel.cachedEvents.observeAsState().value
            if (!cachedEvents.isNullOrEmpty()) {
                EventList(cachedEvents, navController)
            } else {
                if (isToastNotShown) {
                    Toast.makeText(
                        LocalContext.current,
                        (eventsState as Resource.Error).message,
                        Toast.LENGTH_LONG
                    ).show()
                    isToastNotShown = false
                }
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    color = MainRed
                )
            }
        }
    }
}


@Composable
fun EventList(events: List<Event>, navController: NavController) {
    LazyColumn {
        items(events.size) {
            Box(
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {

                    val encodedUrl = URLEncoder.encode(
                        "https://guidebook.com${events[it].url}",
                        StandardCharsets.UTF_8.toString()
                    )
                    val title = events[it].name

                    navController.navigate("webViewScreen/${encodedUrl}/${title}")
                }
            ) {
                EventListItem(events[it])
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun EventListItem(event: Event) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(160.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(event.icon)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = event.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = event.endDate,
                fontSize = 14.sp
            )
        }
    }
}