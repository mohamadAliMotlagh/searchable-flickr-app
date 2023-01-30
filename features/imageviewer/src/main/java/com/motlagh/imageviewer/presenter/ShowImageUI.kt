package com.motlagh.imageviewer.presenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter

@Composable
fun ShowImageUI(viewModel: ImageViewerViewModel = hiltViewModel()) {
    val imageAddress = viewModel.imageAddress.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Image in big size")
                },
                navigationIcon = {
                    IconButton(onClick = viewModel::navigateUp) {
                        Icon(Icons.Filled.Close, "closeIcon")
                    }
                },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                elevation = 10.dp
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                ImageLoader(imageAddress = imageAddress)
            }
        }
    )
}

@Composable
private fun ImageLoader(imageAddress: String) {
    Image(
        painter = rememberAsyncImagePainter(
            model = imageAddress,
            imageLoader = ImageLoader.Builder(LocalContext.current).build()
        ),
        contentDescription = null,
        Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}