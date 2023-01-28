package com.motlagh.flickerlist.presenter

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.domain.GetListUseCase
import com.motlagh.flickerlist.domain.model.FlickrModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(private val getListUseCase: GetListUseCase) :
    ViewModel() {

    private val _searchedText = MutableStateFlow("")//query
    val searchedText = _searchedText.asStateFlow()

    private val _images = MutableStateFlow(listOf<FlickrModel>())
    val images = _images.asStateFlow()

    fun searchText(text: String) {
        _searchedText.value = text
    }

    init {
        viewModelScope.launch {
            searchedText.debounce(1000).collect { text ->
                if (text.isEmpty()) return@collect
                getListUseCase(text).collect {
                    when (it) {
                        is ResultModel.Error -> TODO()
                        is ResultModel.Success -> {
                            _images.value = it.value
                        }
                    }
                }
            }
        }
    }


}