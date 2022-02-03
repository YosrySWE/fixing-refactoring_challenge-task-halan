package com.example.halanchallenge.features.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor() : ViewModel() {

    private var _state = MutableStateFlow(DetailsViewState.Idle)
    val state: StateFlow<DetailsViewState> get() = _state


}