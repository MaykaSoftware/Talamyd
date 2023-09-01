package com.mayka.talamyd.home.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mayka.talamyd.home.component.CoursesComponent

@Composable
fun Home (
    component: CoursesComponent,
    topBarActions: @Composable RowScope.() -> Unit,
){
    val uiState by component.uiState.subscribeAsState()

    HomeScreen(
        uiState = uiState,
        topBarActions = topBarActions
    )
}