package com.mayka.talamyd

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.mayka.talamyd.auth.ui.AuthenticationView
import com.mayka.talamyd.common.ui.LoadingView
import com.mayka.talamyd.main.ui.MainView

@Composable
fun TalaamydApp(
    component: TalamydRootComponent,
) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            TalamydRootComponent.Child.Loading -> LoadingView()
            is TalamydRootComponent.Child.Auth -> AuthenticationView(child.component)
            is TalamydRootComponent.Child.Main -> MainView(child.component)
        }
    }
}