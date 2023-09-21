package com.rodrigorods.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.rodrigorods.events.model.Event
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
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: EventListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    when(val uiState = viewModel.uiState.collectAsState().value) {
        is UIState.FullPageLoading -> FullScreenLoading()
        is UIState.DisplayingEventList -> CharacterList(data = uiState.eventList)
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
fun CharacterList(data: List<Event>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(data.size) { index ->
                EventListRow(data[index])
            }
        }
    }
}

@Composable
fun EventListRow(content: Event) {
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        val (thumbnail, name, description, divider) = createRefs()

        AsyncImage(
            model = content.image,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(end = 6.dp)
                .constrainAs(thumbnail) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopStart
        )
        Text(
            text = content.title,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(thumbnail.top)
                    start.linkTo(thumbnail.end)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
        )
        Text(
            text = content.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(description) {
                    start.linkTo(thumbnail.end)
                    end.linkTo(parent.end)
                    top.linkTo(name.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        Divider(
            modifier = Modifier
                .constrainAs(divider) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            color = Color.Gray,
            thickness = 4.dp
        )
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