package com.mayka.talamyd.auth.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mayka.talamyd.auth.component.LoginComponent

@Composable
fun Login(
    component: LoginComponent
) {
    val uiState by component.uiState.subscribeAsState()

    LoginScreen(
        uiState = uiState,
        onEmailChange = component::onEmailChanged,
        onPasswordChange = component::onPasswordChanged,
        onLoginClick = component::login,
        onNavigateToSignup = component::register
    )
}