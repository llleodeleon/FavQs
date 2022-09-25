package com.leodeleon.favqs.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.leodeleon.favqs.data.model.Filter
import com.leodeleon.favqs.presentation.QuotesViewModel

@Composable
fun TagScreen(
    tag: String,
    navigationIcon: (@Composable () -> Unit)? = null
) {
    val viewModel: QuotesViewModel = hiltViewModel()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "#$tag")
        }, navigationIcon = navigationIcon)
    }, content = {
        LaunchedEffect(Unit) {
            viewModel.getQuotes(Filter(tag, Filter.Type.Tag))
        }
        QuotesList(
            quotes = viewModel.quotes.value.collectAsLazyPagingItems(),
            modifier = Modifier.padding(it),
            showTags = false
        )
    })
}