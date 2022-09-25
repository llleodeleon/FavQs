package com.leodeleon.favqs.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leodeleon.favqs.data.IPrefsRepo
import com.leodeleon.favqs.data.IQuotesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: IQuotesRepo,
    private val prefsRepo: IPrefsRepo
) : ViewModel() {

    val isLoggedIn = runBlocking {
        prefsRepo.appPrefsFlow.first().token.isNotEmpty()
    }

    private val _state = mutableStateOf<LoginUiState?>(null)
    val state : State<LoginUiState?> = _state

    fun login(user: String, password: String) {
        viewModelScope.launch {
            _state.value = (LoginUiState.Loading)
            repo.login(user, password).fold(
                onSuccess = { _state.value = (LoginUiState.Success) },
                onFailure = { _state.value = (LoginUiState.Error) }
            )
        }
    }
}

@Immutable
sealed interface LoginUiState {
    object Success : LoginUiState
    object Error : LoginUiState
    object Loading : LoginUiState
}