package com.rodrigorods.events.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rodrigorods.events.FullScreenLoading
import com.rodrigorods.events.FullScreenRetryMessage
import com.rodrigorods.events.model.Event
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventDetailScreen(
    eventId: String,
    navController: NavController,
    viewModel: EventDetailViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    SideEffect { viewModel.getEventDetail(eventId) }

    when (val uiState = viewModel.uiState.collectAsState().value) {
        is UIState.FullPageLoading -> FullScreenLoading()
        is UIState.DisplayingEventDetail -> EventDetailCard(content = uiState.event)
        else -> FullScreenRetryMessage(modifier = modifier)
    }
}

@Composable
fun EventDetailCard(content: Event) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            AsyncImage(
                model = content.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(260.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = content.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            Text(
                text = content.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}