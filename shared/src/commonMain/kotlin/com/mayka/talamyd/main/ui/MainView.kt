package com.mayka.talamyd.main.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.mayka.talamyd.main.component.TalamydMainComponent
import com.mayka.talamyd.profile.ui.ProfileScreen

@Composable
fun MainView(component: TalamydMainComponent) {
    Children(
        stack = component.stack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is TalamydMainComponent.Child.Settings -> ProfileScreen()
            is TalamydMainComponent.Child.Tab -> TabRoute(child.component)
        }
    }
}