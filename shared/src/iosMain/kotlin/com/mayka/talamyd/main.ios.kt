package com.mayka.talamyd

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mayka.talamyd.common.theming.TalamydAppTheme
import com.mayka.talamyd.di.initKoin
import com.mayka.talamyd.ui.ContentView
import com.mayka.talamyd.utils.LocalSafeArea
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIViewController

@Suppress("unused", "FunctionName")
fun MainViewController(
    lifecycle: LifecycleRegistry,
    topSafeArea: Float,
    bottomSafeArea: Float
): UIViewController {
    initKoin()

    val root =
        TalamydRootComponentImpl(
            componentContext = DefaultComponentContext(lifecycle = lifecycle)
        )

    return ComposeUIViewController {
        val darkTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
        val density = LocalDensity.current

        val topSafeAreaDp = with(density) { topSafeArea.toDp() }
        val bottomSafeAreaDp = with(density) { bottomSafeArea.toDp() }
        val safeArea = PaddingValues(top = topSafeAreaDp + 10.dp, bottom = bottomSafeAreaDp)

        // Bind safe area as the value for LocalSafeArea
        CompositionLocalProvider(LocalSafeArea provides safeArea) {
            TalamydAppTheme(darkTheme) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    ContentView(component = root)
                }
            }
        }
    }
}