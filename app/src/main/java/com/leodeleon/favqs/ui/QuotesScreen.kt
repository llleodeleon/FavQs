package com.leodeleon.favqs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.leodeleon.favqs.data.model.Quote
import com.leodeleon.favqs.presentation.QuotesViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun QuotesScreen(
    onClickSearch: () -> Unit,
    onClickTag: (String) -> Unit,
) {
    val viewModel: QuotesViewModel = hiltViewModel()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "FavQs")
        }, actions = {
            IconButton(onClick = onClickSearch) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        })
    }) {
        Box(modifier = Modifier.padding(it)) {
            LaunchedEffect(Unit) {
                viewModel.getQuotes()
            }
            val quotes = viewModel.quotes.value.collectAsLazyPagingItems()
            QuotesList(
                quotes = quotes,
                showTags = true,
                onClickTag = onClickTag,
                onRetry = {
                    quotes.refresh()
                }
            )
        }
    }
}
