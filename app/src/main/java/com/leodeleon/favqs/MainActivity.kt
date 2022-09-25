package com.leodeleon.favqs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.leodeleon.favqs.ui.AppNavHost
import com.leodeleon.favqs.ui.theme.FavqsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavqsTheme {
                AppNavHost(modifier = Modifier.fillMaxSize())
            }
        }
    }
}