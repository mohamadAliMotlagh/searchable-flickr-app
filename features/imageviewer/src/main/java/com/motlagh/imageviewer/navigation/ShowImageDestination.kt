package com.motlagh.imageviewer.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.motlagh.uikit.navigator.NavigationDestination
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object ShowImageDestination : NavigationDestination {

    const val SHOW_IMAGE_PARAM = "image_address"
    private const val SHOW_IMAGE_ROUTE = "show_image_route"
    private const val SHOW_IMAGE_NAV_ROUTE = "$SHOW_IMAGE_ROUTE/{$SHOW_IMAGE_PARAM}"

    fun createShowImageRoute(imageAddress: String): String {
        val encodedUrl = URLEncoder.encode(imageAddress, StandardCharsets.UTF_8.toString())
        return "$SHOW_IMAGE_ROUTE/$encodedUrl"
    }

    override fun route(): String = SHOW_IMAGE_NAV_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(SHOW_IMAGE_PARAM) { type = NavType.StringType }
        )
}