package com.motlagh.flickerlist.presenter.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

    val textSearch = viewModel.queryText.collectAsState().value
    val list = viewModel.images.collectAsState().value
    val recentSearchList = viewModel.recentSearchItems.collectAsState().value

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
                recentSearchList,
                padding = contentPadding,
                onItemClicked = viewModel::navigateToShowImage,
                onRecentSearchClicked = viewModel::search
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
    recentSearch: List<String>,
    padding: PaddingValues,
    onItemClicked: (String) -> Unit,
    onRecentSearchClicked: (String) -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(padding),
        columns = GridCells.Fixed(2),
        content = {
            item(span = { GridItemSpan(this.maxLineSpan) }) {
                RecentSearchUI(recentSearch, onRecentSearchClicked)
            }

            itemsIndexed(list) { _, item ->
                ListItem(item.thumbnailAddress, item.title) {
                    onItemClicked.invoke(item.mainImageAddress)
                }
            }
        }
    )
}

@Composable
fun RecentSearchUI(recentSearch: List<String>, onRecentSearchClicked: (String) -> Unit) {
    LazyHorizontalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .heightIn(0.dp, 90.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        rows = GridCells.Fixed(3),
        content = {


            itemsIndexed(recentSearch) { _, item ->

                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .border(1.dp, Color.Red, shape = RoundedCornerShape(50))
                        .padding(2.dp)
                        .clickable {
                            onRecentSearchClicked.invoke(item)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(horizontal = 5.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.caption
                    )

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