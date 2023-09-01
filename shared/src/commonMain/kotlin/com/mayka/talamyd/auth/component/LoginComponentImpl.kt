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
import com.mayka.talamyd.auth.domain.SignInUseCase
import com.mayka.talamyd.common.util.MyResult
import com.mayka.talamyd.utils.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Parcelize
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isAuthenticating: Boolean = false,
    val authErrorMessage: String? = null,
    val authenticationSucceed: Boolean = false
) : Parcelable

interface LoginComponent {

    val uiState: Value<LoginUiState>

    fun login()
    fun register()
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)
}

class LoginComponentImpl(
    componentContext: ComponentContext,
    private val onLoginSuccess: () -> Unit,
    private val onRegister: () -> Unit
) : LoginComponent, KoinComponent, ComponentContext by componentContext {

    private val handler =
        instanceKeeper.getOrCreate(LOGIN_KEY_STATE) {
            Handler(
                initialState = stateKeeper.consume(LOGIN_KEY_STATE)
                    ?: LoginUiState()
            )
        }

    private val useCase: SignInUseCase by inject()
    private val coroutineScope = coroutineScope()

    override val uiState: Value<LoginUiState> = handler.state

    override fun login() {
        coroutineScope.launch {
            when (val authResultData =
                useCase.invoke(handler.state.value.email, handler.state.value.password)) {
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
                    onLoginSuccess()
                }
            }
        }
    }

    override fun register() {
        onRegister()
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

    private companion object {
        private const val LOGIN_KEY_STATE = "LOGIN STATE"
    }

    private class Handler(
        initialState: LoginUiState,
    ) : InstanceKeeper.Instance, DisposableScope by DisposableScope() {
        val state: MutableValue<LoginUiState> = MutableValue(initialState)

        init {
            state.update {
                it.copy(
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