package com.mayka.talamyd.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PriceCheck
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mayka.talamyd.SharedRes
import com.mayka.talamyd.home.ui.Home
import com.mayka.talamyd.main.component.TalamydTabComponent
import com.mayka.talamyd.price.ui.PriceScreen
import com.mayka.talamyd.settings.ui.SettingsScreen
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun TabRoute(
    component: TalamydTabComponent
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Row {
        Scaffold(
            modifier = Modifier.windowInsetsPadding(WindowInsets.ime),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(),
            ) {
                Children(
                    component = component,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier
                        .weight(1f)
                        .then(Modifier.consumeWindowInsets(NavigationBarDefaults.windowInsets)),
                )

                BottomBar(component = component)
            }
        }
    }

    val dialogSlot by component.dialogSlot.subscribeAsState()
    dialogSlot.child?.instance?.also {
        SignOutDialog(dialogComponent = it)
    }
}

@Composable
private fun Children(
    component: TalamydTabComponent,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {

    val topBarActions: @Composable RowScope.() -> Unit = {
        AccountIcon(
            onSignOut = component::onSignOutClicked,
        )
    }

    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is TalamydTabComponent.Child.HomeChild -> Home(
                topBarActions = topBarActions,
                component = child.component
            )

            is TalamydTabComponent.Child.PricesChild -> PriceScreen(
                topBarActions = topBarActions
            )

            is TalamydTabComponent.Child.SettingsChild -> SettingsScreen(
                topBarActions = topBarActions
            )
        }
    }
}

@Composable
private fun BottomBar(component: TalamydTabComponent) {
    Column {
        Divider()
        NavigationBar(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            tonalElevation = 0.dp,
        ) {
            NavigationButtons(component = component) { isSelected, selectedIcon, unselectedIcon, textId, onClick ->
                NavigationBarItem(
                    selected = isSelected,
                    onClick = onClick,
                    icon = {
                        Icon(
                            imageVector = if (isSelected) selectedIcon else unselectedIcon,
                            contentDescription = textId,
                        )
                    },
                    label = { Text(textId) },
                )
            }
        }
    }
}

@Composable
private fun <T> T.NavigationButtons(
    component: TalamydTabComponent,
    content: @Composable T.(
        isSelected: Boolean,
        selectedIcon: ImageVector,
        unselectedIcon: ImageVector,
        textId: String,
        onClick: () -> Unit,
    ) -> Unit,
) {
    val stack by component.childStack.subscribeAsState()
    val activeChild = stack.active.instance

    content(
        isSelected = activeChild is TalamydTabComponent.Child.HomeChild,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        textId = stringResource(SharedRes.strings.label_tab_home),
        onClick = component::onHomeTabClicked,
    )

    content(
        isSelected = activeChild is TalamydTabComponent.Child.PricesChild,
        selectedIcon = Icons.Filled.PriceCheck,
        unselectedIcon = Icons.Outlined.PriceCheck,
        textId = stringResource(SharedRes.strings.label_tab_price),
        onClick = component::onPricesTabClicked,
    )

    content(
        isSelected = activeChild is TalamydTabComponent.Child.SettingsChild,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        textId = stringResource(SharedRes.strings.label_tab_settings),
        onClick = component::onSettingsTabClicked,
    )
}