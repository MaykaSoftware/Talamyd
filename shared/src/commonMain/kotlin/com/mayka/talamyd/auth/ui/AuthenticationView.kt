package com.mayka.talamyd.auth.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.mayka.talamyd.auth.component.TalamydAuthComponent
import com.mayka.talamyd.auth.ui.login.Login
import com.mayka.talamyd.auth.ui.signup.SignUp

@Composable
fun AuthenticationView(component: TalamydAuthComponent) {
    Children(
        stack = component.stack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is TalamydAuthComponent.Child.Guest -> {}
            is TalamydAuthComponent.Child.Login -> Login(component = child.component)
            is TalamydAuthComponent.Child.Signup -> SignUp(component = child.component)
        }
    }
}