package com.compose.base.features.shared

import com.compose.base.common.base.BaseViewModel
import com.compose.base.common.helper.LocalDataManager
import com.compose.base.common.helper.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    val localDataManager: LocalDataManager,
    val resProvider: ResourceProvider,
) : BaseViewModel(localDataManager, resProvider)
