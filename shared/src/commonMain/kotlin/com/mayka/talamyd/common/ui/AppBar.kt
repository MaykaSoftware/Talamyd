package com.mayka.talamyd.common.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mayka.talamyd.common.theming.SmallElevation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier,
) {

    Surface(
        modifier = modifier,
        tonalElevation = SmallElevation
    ) {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = "",
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            navigationIcon =
            {
//                IconButton(onClick = { /*TODO*/ }) {
//                    Icon(
//                        painter = painterResource("compose-multiplatform.xml"),
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
            }

        )
    }
}
