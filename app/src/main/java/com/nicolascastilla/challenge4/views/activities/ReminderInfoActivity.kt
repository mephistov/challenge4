package com.nicolascastilla.challenge4.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.nicolascastilla.challenge4.views.activities.ui.theme.Challenge4Theme
import com.nicolascastilla.challenge4.views.compose.RemainderInfoCompose
import com.nicolascastilla.challenge4.views.compose.RemainderInfoComposeError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.extras
        val idReman = data?.getLong("REMAINDER_ID")
        setContent {
            Challenge4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if(idReman != null)
                        RemainderInfoCompose(idReman)
                    else
                        RemainderInfoComposeError()

                }
            }
        }
    }
}

