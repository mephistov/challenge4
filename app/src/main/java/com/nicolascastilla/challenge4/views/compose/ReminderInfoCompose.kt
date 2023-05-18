package com.nicolascastilla.challenge4.views.compose

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge4.viewmodels.InfoReminderViewModel
import com.nicolascastilla.domain.entities.ReminderEntity
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun RemainderInfoCompose(idReman: Long?) {
    val viewModel = viewModel<InfoReminderViewModel>()
    viewModel.getRemainderById(idReman!!)
    val myData by viewModel.myData.observeAsState(null)
    if(myData != null){
        MainInfo(myData!!)
    }else{
        Text("Error garrafal intente de nuevo id:${idReman}")
    }

}

@Composable
fun RemainderInfoComposeError(){
    Text("ALgun error paso")
}
@Composable
fun MainInfo(remainde: ReminderEntity){

    val scaffolState = rememberScaffoldState()
    val context = LocalContext.current
    val modifier: Modifier = Modifier
    Scaffold(
        scaffoldState = scaffolState,
        topBar = { TopBar(onMenuClicked = {  (context as? Activity)?.finish() },"Remander ID: ${remainde.name}") },
    ) {
        modifier.padding(it)
        BodyInfo(remainde)

    }

}

@Composable
fun BodyInfo(remainde: ReminderEntity){
    Column() {
        Text("Name: ${remainde.name}")
        Text("Description: ${remainde.description}")
        Text("Type: ${remainde.type}")
        Text("Fecha: ${remainde.humaDate}")
        Text("Status: ${remainde.status}")
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewInfo() {
    MainInfo(ReminderEntity(name = "nameTest"))
}