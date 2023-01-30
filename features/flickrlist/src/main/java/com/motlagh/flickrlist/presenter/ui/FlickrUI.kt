package com.motlagh.flickrlist.presenter.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.motlagh.flickrlist.domain.model.FlickrModel
import com.motlagh.flickrlist.presenter.FlickrViewModel
import com.motlagh.uikit.ViewData
import com.motlagh.uikit.ViewError
import com.motlagh.uikit.ViewLoading
import com.motlagh.uikit.ViewState
import com.motlagh.uikit.common.ErrorUI
import com.motlagh.uikit.common.LoadingUI


@Composable
fun FlickrUI(viewModel: FlickrViewModel = hiltViewModel()) {

    val textSearch = viewModel.queryText.collectAsState().value
    val imagesViewState = viewModel.images.collectAsState().value
    val recentSearchList = viewModel.recentSearchItems.collectAsState().value

    Scaffold(
        topBar = {
            SearchTextFieldUI(
                currentText = textSearch,
                queryText = viewModel::requestSearchWithQueryText
            )
        },
        content = { contentPadding ->

            ImageListUI(
                imagesViewState = imagesViewState,
                recentSearchList,
                padding = contentPadding,
                onItemClicked = viewModel::navigateToShowImage,
                onRecentSearchClicked = viewModel::requestSearchWithQueryText,
                onclickRetryWhenError = viewModel::retrySearchWithQuery
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
    imagesViewState: ViewState<List<FlickrModel>>,
    recentSearch: List<String>,
    padding: PaddingValues,
    onItemClicked: (String) -> Unit,
    onRecentSearchClicked: (String) -> Unit,
    onclickRetryWhenError: () -> Unit
) {

    Box() {
        val list: List<FlickrModel> =
            if (imagesViewState is ViewData) imagesViewState.data else listOf()
        LazyVerticalGrid(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(padding),
            columns = GridCells.Fixed(2),
            content = {
                item(span = { GridItemSpan(this.maxLineSpan) }) {
                    RecentSearchUI(recentSearch, onRecentSearchClicked)
                }

                items(items = list, key = {
                    it.imageID
                }) { item ->
                    ListItem(item.thumbnailAddress, item.title) {
                        onItemClicked.invoke(item.mainImageAddress)
                    }
                }
            }
        )

        when (imagesViewState) {
            is ViewError -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(65.dp))
                    ErrorUI(imagesViewState.message,onclickRetryWhenError)
                }
            }
            ViewLoading -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(65.dp))
                    LoadingUI()
                }
            }
            else -> {}
        }

    }
}

@Composable
fun RecentSearchUI(recentSearch: List<String>, onRecentSearchClicked: (String) -> Unit) {
    LazyHorizontalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .heightIn(0.dp, 60.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        rows = GridCells.Fixed(2),
        content = {


            items(items = recentSearch, key = { it }) { item ->
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
                imageLoader = ImageLoader.Builder(LocalContext.current)
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