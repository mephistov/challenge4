package com.nicolascastilla.challenge4.views.activities

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.nicolascastilla.challenge4.ui.theme.Challenge4Theme
import com.nicolascastilla.challenge4.views.compose.InitViewMainCompose
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var coroutineScope: CoroutineScope
    lateinit var scaffolState:ScaffoldState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Challenge4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    InitViewMainCompose()

                }
            }
        }
        requestPermission()
    }

    fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(baseContext,"Thanks, you can continue using the app :)",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(baseContext,"Sorry, the app only works with permission :(",Toast.LENGTH_LONG).show()
                }
            }
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onResume() {
        super.onResume()




    }
}