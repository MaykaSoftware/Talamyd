package com.mayka.talamyd.auth.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mayka.talamyd.auth.component.SignupComponent

@Composable
fun SignUp(
    component: SignupComponent
) {
    val uiState by component.uiState.subscribeAsState()

    SignUpScreen(
        uiState = uiState,
        onUsernameChange = component::onUsernameChanged,
        onEmailChange = component::onEmailChanged,
        onPasswordChange = component::onPasswordChanged,
        onSignUpClick = component::onSignupClicked,
        onCloseClicked = component::onCloseClicked
    )
}