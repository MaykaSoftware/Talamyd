package com.mayka.talamyd.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mayka.talamyd.main.component.SignOutComponent

@Composable
fun SignOutDialog(dialogComponent: SignOutComponent) {
    Dialog(onDismissRequest = {
        dialogComponent.onDismissClicked()
    }) {
        Card {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = dialogComponent.title,
                    textAlign = TextAlign.Center,
                )

                Text(text = dialogComponent.message)

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Button(onClick = {
                        dialogComponent.onDismissClicked()
                    }) {
                        Text(text = "Dismiss")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        dialogComponent.onSignOutClicked()
                    }) {
                        Text(text = "Log out")
                    }
                }
            }
        }
    }
}