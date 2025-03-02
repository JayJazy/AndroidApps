package com.example.presentation.main.setting

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.login.ClearTokenUseCase
import com.example.domain.usecase.main.setting.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val clearTokenUseCase: ClearTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel(), ContainerHost<SettingState, SettingSideEffect> {


    override val container: Container<SettingState, SettingSideEffect> =
        container(
            initialState = SettingState(),
            buildSettings = {
                this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                    intent {
                        postSideEffect(SettingSideEffect.Toast(throwable.message.orEmpty()))
                    }
                }
            }
        )

    init {
        viewModelScope.launch {
            load()
        }
    }

    private fun load() = intent {
        val user = getUserInfoUseCase().getOrThrow()
        reduce {
            state.copy(
                profileImageUrl = user.profileImageUrl,
                userName = user.userName
            )
        }
    }

    fun onLogoutClick() = intent {
        clearTokenUseCase().getOrThrow()
        postSideEffect(SettingSideEffect.NavigateToLoginActivity)
    }

    fun onUsernameChange(username: String) = intent {
        reduce {
            state.copy(userName = username)
        }
    }

}


@Immutable
data class SettingState(
    val profileImageUrl : String? = null,
    val userName: String = ""
)


sealed interface SettingSideEffect
{
    class Toast(val message: String) : SettingSideEffect
    data object NavigateToLoginActivity : SettingSideEffect
}
