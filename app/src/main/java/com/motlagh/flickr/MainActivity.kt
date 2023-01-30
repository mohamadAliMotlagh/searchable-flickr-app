package com.motlagh.flickr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.motlagh.flickrlist.presenter.ui.FlickrUI
import com.motlagh.flickrlist.presenter.ui.graphs.addFlickrComposableDestinations
import com.motlagh.flickrlist.presenter.ui.navigation.ListImageDestination
import com.motlagh.uikit.navigator.Navigator
import com.motlagh.uikit.navigator.NavigatorEvent
import com.motlagh.uikit.theme.FlickerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = ListImageDestination.route()
                    ) {
                        addFlickrComposableDestinations()
                    }

                    LaunchedEffect(navController) {
                        appNavigator.destinations.collect {
                            when (val event = it) {
                                is NavigatorEvent.NavigateUp -> {
                                    navController.navigateUp()
                                }
                                is NavigatorEvent.Directions -> navController.navigate(
                                    event.destination,
                                    event.builder
                                )
                                NavigatorEvent.PopBackStack -> {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlickerTheme {
        FlickrUI()
    }
}