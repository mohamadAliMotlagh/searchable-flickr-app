package com.motlagh.flickerlist.presenter.ui

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
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.flickerlist.presenter.FlickrViewModel


@Composable
fun FlickrUI(viewModel: FlickrViewModel = hiltViewModel()) {

    val textSearch = viewModel.searchedText.collectAsState().value
    val list = viewModel.images.collectAsState().value
    Scaffold(
        topBar = {
            SearchTextFieldUI(
                currentText = textSearch,
                queryText = viewModel::searchText
            )
        },
        content = { contentPadding ->
            ImageListUI(
                list = list,
                padding = contentPadding,
                onItemClicked = viewModel::navigateToShowImage
            )
        }
    )
}


@Composable
fun SearchTextFieldUI(
    currentText: String,
    queryText: (String) -> Unit
) {
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
            value = currentText,
            singleLine = true,
            label = {
                Text(text = "search here", color = Color.Gray)
            },
            onValueChange = queryText,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(horizontal = 12.dp)
        )
    }
}

@Composable
fun ImageListUI(
    list: List<FlickrModel>,
    padding: PaddingValues,
    onItemClicked: (String) -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(padding),
        columns = GridCells.Fixed(2),
        content = {
            itemsIndexed(list) { _, item ->
                ListItem(item.thumbnailAddress, item.title) {
                    onItemClicked.invoke(item.mainImageAddress)
                }
            }
        }
    )
}

@Composable
fun ListItem(imageAddress: String, title: String, clickable: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(0.dp))
            .clickable {
                clickable.invoke()
            },
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