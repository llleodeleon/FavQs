package com.leodeleon.favqs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.leodeleon.favqs.R
import com.leodeleon.favqs.presentation.LoginUiState
import com.leodeleon.favqs.presentation.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNext: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    var user by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    LaunchedEffect(scaffoldState.snackbarHostState) {
        when (state.value) {
            LoginUiState.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Try again",
                    )
            }
            LoginUiState.Success -> {
                    onNext()
            }
            else -> {} //Do Nothing
        }
    }

    Scaffold(scaffoldState = scaffoldState) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(vertical = 48.dp)
            )
            TextField(value = user,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                label = {
                    Text(text = "Username or email")
                },
                onValueChange = {
                    user = it
                })
            TextField(value = password,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    password = it
                }, keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(44.dp),
                contentPadding = PaddingValues(top = 0.dp),
                enabled =
                user.text.isNotEmpty() &&
                        password.text.isNotEmpty() &&
                        state.value != LoginUiState.Loading,
                onClick = {
                    viewModel.login(user = user.text, password = password.text)
                    keyboardController?.hide()
                }) {

                if (state.value == LoginUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Login")
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}