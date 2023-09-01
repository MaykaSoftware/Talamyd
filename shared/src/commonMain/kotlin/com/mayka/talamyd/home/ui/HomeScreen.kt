package com.mayka.talamyd.home.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mayka.talamyd.SharedRes
import com.mayka.talamyd.home.component.CoursesUiState
import com.mayka.talamyd.home.data.CourseData
import com.mayka.talamyd.main.ui.TabContainerScaffold
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: CoursesUiState,
    topBarActions: @Composable RowScope.() -> Unit,
) {

    TabContainerScaffold(
        title = stringResource(SharedRes.strings.label_tab_home),
        topBarActions = topBarActions,
    ) {
        LazyColumn {
            items(uiState.courses){ course ->
                CourseRow(modifier, course)
            }
        }
    }

}

@Composable
fun CourseRow(modifier: Modifier = Modifier, course: CourseData){
    Card {
        Text(text = course.title)
        Spacer(modifier.padding(4.dp, 8.dp))
        Text(text = course.description)
    }
}