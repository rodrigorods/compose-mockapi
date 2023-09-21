package com.rodrigorods.events

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.rodrigorods.events.model.Event
import com.rodrigorods.events.navigation.NavGraph
import com.rodrigorods.events.navigation.Screens
import com.rodrigorods.events.theme.ComposeEventListTheme
import org.koin.androidx.compose.koinViewModel

class EventListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeEventListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    when(val uiState = viewModel.uiState.collectAsState().value) {
        is UIState.FullPageLoading -> FullScreenLoading()
        is UIState.DisplayingEventList ->
            CharacterList(
                data = uiState.eventList,
                onEventClick = { eventId ->
                    navController.navigate(
                        route = "${Screens.Detail.route}/$eventId"
                    )
                }
            )
        else -> FullScreenRetryMessage(modifier = modifier)
    }
}

@Composable
fun FullScreenRetryMessage(modifier: Modifier) {
    Text(
        text = "Erro de carregamento! Tente Novamente!",
        modifier = modifier
    )
}

@Composable
fun FullScreenLoading() {
    Surface(
        modifier = Modifier.wrapContentSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp)
        )
    }
}

@Composable
fun CharacterList(data: List<Event>, onEventClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(data.size) { index ->
                EventListRow(data[index], onEventClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListRow(content: Event, onEventClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        onClick = {
            Log.e("TESTE", "CLICOU :${content.id}")
            onEventClick(content.id)
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(color = Color.Gray.copy(alpha = 0.15f))
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (thumbnail, name, description) = createRefs()

            AsyncImage(
                model = content.image,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentScale = ContentScale.Crop,
            )
            Text(
                text = content.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .constrainAs(name) {
                        top.linkTo(thumbnail.top)
                        start.linkTo(thumbnail.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
            )
            Text(
                text = content.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .constrainAs(description) {
                        start.linkTo(thumbnail.end)
                        end.linkTo(parent.end)
                        top.linkTo(name.bottom)
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventListPreview() {
    ComposeEventListTheme {
        Surface(
            modifier = Modifier.wrapContentSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp)
            )
        }
    }
}