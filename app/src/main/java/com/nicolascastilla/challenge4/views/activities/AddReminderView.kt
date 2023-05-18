package com.nicolascastilla.challenge4.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import com.nicolascastilla.challenge4.views.compose.InitViewAddRemainder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class AddReminderView : ComponentActivity() {

    lateinit var coroutineScope: CoroutineScope
    lateinit var scaffolState:ScaffoldState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitViewAddRemainder(context = this)
        }
    }
}