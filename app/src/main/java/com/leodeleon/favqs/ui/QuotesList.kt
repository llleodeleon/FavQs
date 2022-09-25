package com.leodeleon.favqs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.leodeleon.favqs.data.model.Quote

@Composable
fun QuotesList(
    quotes: LazyPagingItems<Quote>,
    modifier: Modifier = Modifier,
    showTags: Boolean,
    onRetry: () -> Unit = {},
    onClickTag: (String) -> Unit = {},
) {

    when (quotes.loadState.refresh) {
        is LoadState.Error -> {
            Box(Modifier.fillMaxSize()) {
                Column(Modifier.align(Alignment.Center)) {
                    Text(text = "Error")
                    Button(onClick = {
                        onRetry()
                    }, ){
                        Text(text = "Retry")
                    }
                }
            }
        }
        LoadState.Loading -> Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        else -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(quotes) { quote ->
                    quote?.let {
                        QuoteItem(quote = it, showTags = showTags, onClickTag = onClickTag)
                    }
                }

                with(quotes) {
                    when (loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item { Text(text = "Error loading quotes") }
                        }
                        else -> {} // Do nothing
                    }
                }
            }
        } // Do nothing
    }

}