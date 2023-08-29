package com.mayka.talamyd

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mayka.talamyd.common.theming.TalamydAppTheme
import com.mayka.talamyd.ui.ContentView

@Composable
fun ContentController(component: TalamydRootComponent) {
    TalamydAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            ContentView(
                component = component,
            )
        }
    }
}
