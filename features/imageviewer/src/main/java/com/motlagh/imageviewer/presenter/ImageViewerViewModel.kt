package com.motlagh.imageviewer.presenter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.motlagh.imageviewer.navigation.ShowImageDestination
import com.motlagh.uikit.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator
) : ViewModel(), Navigator by navigator {

    private val _imageAddress = MutableStateFlow("")
    val imageAddress = _imageAddress.asStateFlow()

    init {
        savedStateHandle.get<String>(ShowImageDestination.SHOW_IMAGE_PARAM)?.let {
            _imageAddress.value = it
        }
    }
}