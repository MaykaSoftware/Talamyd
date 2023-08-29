package com.mayka.talamyd.settings.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mayka.talamyd.SharedRes
import com.mayka.talamyd.main.ui.TabContainerScaffold
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    topBarActions: @Composable RowScope.() -> Unit,
) {
    TabContainerScaffold(
        title = stringResource(SharedRes.strings.label_tab_settings),
        topBarActions = topBarActions,
    ) {
        Text(text = "SettingsScreen")
    }
}