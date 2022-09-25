package com.leodeleon.favqs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leodeleon.favqs.data.model.Quote

@Composable
fun QuoteItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    showTags: Boolean,
    onClickTag: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {
        quote.body?.let { body ->
            Text(
                text = body,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.fillMaxWidth()
            )
        }
        quote.author?.let { author ->
            Text(
                text = author,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.57f)
            )
        }

        if (showTags && !quote.tags.isNullOrEmpty()) {
            LazyRow(Modifier.padding(top = 16.dp)) {
                items(quote.tags) { tag ->
                    Chip(onClick = { onClickTag(tag) }, modifier = Modifier.padding(end = 8.dp)) {
                        Text(text = "#$tag", color = MaterialTheme.colors.primary, style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuoteItemPreview() {
    MaterialTheme {
        val quote = Quote(
            id = 0,
            body = "This is the quote body. It can be a long quote or a small one sometimes it doesn't matter",
            author = "By Someone",
            tags = listOf("tag-one", "tag-two", "tag-three")
        )
        QuoteItem(quote = quote, showTags = true, onClickTag = {})
    }
}