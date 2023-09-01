package com.mayka.talamyd.ui

import androidx.compose.runtime.Composable
import com.mayka.talamyd.TalaamydApp
import com.mayka.talamyd.TalamydRootComponent

@Composable
internal fun ContentView(
    component: TalamydRootComponent,
) {
    TalaamydApp(component)
}