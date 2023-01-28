package com.motlagh.flickerlist.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.motlagh.flickerlist.domain.model.FlickrModel


@Composable
fun FlickrUI(viewModel: FlickrViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            PatientItemTextFieldUI(viewModel)
        },
        content = { contentPadding ->
            ImageListUI(viewModel.images.collectAsState().value, contentPadding)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Action") },
                onClick = {  }
            )
            ExtendedFloatingActionButton(
                text = { Text("Action2") },
                onClick = {  }
            )
        }
    )
}


@Composable
fun PatientItemTextFieldUI(
    viewModel: FlickrViewModel
) {
    val textSearch = viewModel.searchedText.collectAsState()
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Cyan,
                textColor = Color.Black,
            ),
            value = textSearch.value,
            singleLine = true,
            label = {
                Text(text = "search here", color = Color.Gray)
            },
            onValueChange = viewModel::searchText,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(horizontal = 12.dp)
        )
    }
}

@Composable
fun ImageListUI(list: List<FlickrModel>, contentPadding: PaddingValues) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(contentPadding),
        columns = GridCells.Fixed(2),
        content = {
            itemsIndexed(list) { _, item ->
                ListItem(item.thumbnailAddress, item.title)
            }
        }
    )
}

@Composable
fun ListItem(imageAddress: String, title: String) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(0.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageAddress,
                imageLoader = ImageLoader.Builder(LocalContext.current).crossfade(true)
                    .build()
            ),
            contentDescription = null,
            Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.6f)),
            style = MaterialTheme.typography.caption,
            maxLines = 2,
            color = Color.White
        )
    }
}