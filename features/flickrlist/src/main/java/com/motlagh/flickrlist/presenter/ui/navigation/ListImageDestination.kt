package com.motlagh.flickrlist.presenter.ui.navigation

import com.motlagh.uikit.navigator.NavigationDestination

object ListImageDestination : NavigationDestination {
    private const val LIST_IMAGE_DESTINATION = "listImage"
    override fun route(): String = LIST_IMAGE_DESTINATION
}