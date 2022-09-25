package com.leodeleon.favqs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leodeleon.favqs.R
import com.leodeleon.favqs.presentation.QuotesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onNext: () -> Unit) {
    val viewModel: QuotesViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.randomQuote.observeAsState()
    SideEffect {
        scope.launch {
            delay(4000)
            onNext()
        }
    }
    state.value?.let { quote ->
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.weight(1f)) {
                QuoteItem(quote = quote, showTags = false, modifier = Modifier.align(Alignment.Center))
            }

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 48.dp)
            )
        }
    }
}