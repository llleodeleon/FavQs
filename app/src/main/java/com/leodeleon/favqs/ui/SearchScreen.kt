package com.leodeleon.favqs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.leodeleon.favqs.data.model.Filter
import com.leodeleon.favqs.presentation.QuotesViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    navigationIcon: (@Composable () -> Unit)? = null,
) {
    val viewModel: QuotesViewModel = hiltViewModel()
    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val localFocusManager = LocalFocusManager.current
    val showResults = query.text.length > 2

    Scaffold(topBar = {
        TopAppBar(title = {
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .background(Color.White.copy(alpha = 0.54f)),
                value = query,
                placeholder = {
                    Text(text = "Search quotes")
                },
                onValueChange = {
                    if (query.text != it.text) {
                        query = it
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                    },
                )
            )
            LaunchedEffect(focusRequester) {
                delay(100)
                focusRequester.requestFocus()
            }
        },
            navigationIcon = navigationIcon,
            actions = {
                IconButton(onClick = {
                    query = TextFieldValue("")
                }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            if (showResults) {
                LaunchedEffect(Unit) {
                    delay(2000)
                    viewModel.getQuotes(Filter(query.text))
                    localFocusManager.clearFocus(true)
                }

                val quotes = viewModel.quotes.value.collectAsLazyPagingItems()
                QuotesList(quotes = quotes, showTags = false, onRetry = {
                    quotes.refresh()
                })
            }
        }
    }
}