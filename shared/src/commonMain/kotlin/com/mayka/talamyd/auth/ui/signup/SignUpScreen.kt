package com.mayka.talamyd.auth.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mayka.talamyd.SharedRes
import com.mayka.talamyd.auth.component.SignUpUiState
import com.mayka.talamyd.common.theming.ButtonHeight
import com.mayka.talamyd.common.theming.ExtraLargeSpacing
import com.mayka.talamyd.common.theming.LargeSpacing
import com.mayka.talamyd.common.theming.MediumSpacing
import com.mayka.talamyd.common.ui.CustomTextField
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onCloseClicked: () -> Unit,
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onCloseClicked() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                    .padding(
                        top = ExtraLargeSpacing + LargeSpacing,
                        start = LargeSpacing + MediumSpacing,
                        end = LargeSpacing + MediumSpacing,
                        bottom = LargeSpacing
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(LargeSpacing)
            ) {

                CustomTextField(
                    value = uiState.username,
                    onValueChange = onUsernameChange,
                    hint = stringResource(SharedRes.strings.hint_username)
                )

                CustomTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    hint = stringResource(SharedRes.strings.hint_email),
                    keyboardType = KeyboardType.Email
                )

                CustomTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(SharedRes.strings.hint_password),
                    keyboardType = KeyboardType.Password,
                    isPasswordTextField = true
                )

                Button(
                    onClick = {
                        onSignUpClick()
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(ButtonHeight),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = stringResource(SharedRes.strings.btn_label_signup))
                }

            }
        }

        if (uiState.isAuthenticating) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(
        key1 = uiState.authErrorMessage,
        block = {
            if (uiState.authErrorMessage != null) {

            }
        }
    )
}