package com.mayka.talamyd.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mayka.talamyd.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AccountIcon(
    onSignOut: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(Icons.Outlined.AccountCircle, contentDescription = "menu")
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {

        DropdownMenuItem(
            text = { Text(stringResource(SharedRes.strings.btn_label_signout)) },
            onClick = {
                onSignOut()
                showMenu = false
            }
        )
    }
}