package com.motlagh.uikit


sealed class ViewState<out T>
data class ViewError(var message: String = "") : ViewState<Nothing>()
object Idle : ViewState<Nothing>()
data class ViewData<T>(val data: T) : ViewState<T>()
object ViewLoading : ViewState<Nothing>()