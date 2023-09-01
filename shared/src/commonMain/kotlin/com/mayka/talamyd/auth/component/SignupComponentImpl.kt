package com.mayka.talamyd.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.essenty.statekeeper.consume
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.mayka.talamyd.auth.domain.SignUpUseCase
import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.utils.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Parcelize
data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isAuthenticating: Boolean = false,
    val authErrorMessage: String? = null
) : Parcelable

interface SignupComponent {
    val uiState: Value<SignUpUiState>

    fun onSignupClicked()
    fun onUsernameChanged(username: String)
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)
    fun onCloseClicked()
}

class SignupComponentImpl(
    componentContext: ComponentContext,
    private val onSignupSuccess: () -> Unit,
    private val onCloseClick: () -> Unit
) : SignupComponent, KoinComponent, ComponentContext by componentContext {

    private val handler =
        instanceKeeper.getOrCreate(SIGNUP_KEY_STATE) {
            Handler(
                initialState = stateKeeper.consume(SIGNUP_KEY_STATE) ?: SignUpUiState()
            )
        }

    private val coroutineScope = coroutineScope()
    private val signupUseCase: SignUpUseCase by inject()

    override val uiState: Value<SignUpUiState> = handler.state

    override fun onSignupClicked() {
        coroutineScope.launch {
            when (val authResultData = signupUseCase.invoke(
                uiState.value.email,
                uiState.value.username,
                uiState.value.password
            )) {
                is MyResult.Error -> {
                    handler.state.update {
                        it.copy(isAuthenticating = false, authErrorMessage = authResultData.message)
                    }
                    //TODO show dialog box
                    println("Error: ${authResultData.message}")
                }

                is MyResult.Success -> {
                    handler.state.update {
                        it.copy(isAuthenticating = false)
                    }
                    onSignupSuccess()
                }
            }
        }
    }

    override fun onUsernameChanged(username: String) {
        handler.state.update {
            it.copy(username = username)
        }
    }

    override fun onEmailChanged(email: String) {
        handler.state.update {
            it.copy(email = email)
        }
    }

    override fun onPasswordChanged(password: String) {
        handler.state.update {
            it.copy(password = password)
        }
    }

    override fun onCloseClicked() {
        onCloseClick()
    }

    private companion object {
        private const val SIGNUP_KEY_STATE = "SIGNUP STATE"
    }

    private class Handler(
        initialState: SignUpUiState,
    ) : InstanceKeeper.Instance, DisposableScope by DisposableScope() {
        val state: MutableValue<SignUpUiState> = MutableValue(initialState)

        init {
            state.update {
                it.copy(
                    username = it.username,
                    email = it.email,
                    password = it.password
                )
            }
        }

        override fun onDestroy() {
            dispose()
        }
    }
}