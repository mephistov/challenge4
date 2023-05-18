package com.nicolascastilla.challenge4.views.compose

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge4.R
import com.nicolascastilla.challenge4.viewmodels.ReminderViewModel
import com.nicolascastilla.domain.entities.AlarmSetup


@Composable
fun InitViewAddRemainder(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
){
    val viewModel = viewModel<ReminderViewModel>()
    val scaffolState = rememberScaffoldState()
    val coroutineScope= rememberCoroutineScope()
    val alarmSetup = AlarmSetup()


    Scaffold(
        scaffoldState = scaffolState,
        topBar = { TopBar(onMenuClicked = {  (context as? Activity)?.finish() },"ADD REMINDER") },
    ) {
        modifier.padding(it)
        BodyView(viewModel,context,alarmSetup)
    }
}

@Composable
fun BodyView(viewModel:ReminderViewModel, context:Context, alarmSetup:AlarmSetup){

    val textName by viewModel.textName.collectAsState()
    val textdescription by viewModel.textDescription.collectAsState()


    val dialogDate = DateDialog(onDialogResult = {result->

        alarmSetup.apply {
            dateValue = result.dateValue
            year = result.year
            month = result.month
            day = result.day
        }

    })
    val dialogTime = TimeDialog(onDialogResult = {result->

        alarmSetup.apply {
            timeValue = result.timeValue
            hour = result.hour
            min = result.min
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TextField(
            value = textName,
            onValueChange = {
                viewModel.onTextChanged(it)
            },
            label = { Text(context.getString( R.string.nameLabol)) },
            modifier = Modifier
                .background(Color.White)
        )
        TextField(
            value = textdescription,
            onValueChange = {
                viewModel.onDescriptionChanged(it)
            },
            label = { Text(context.getString( R.string.description)) },
            modifier = Modifier
                .background(Color.White)
        )
        Demo_DropDownMenu(viewModel)
        Button(
            onClick = {
                dialogDate.show()
            },
            modifier = Modifier
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text("Date")
        }
        Button(
            onClick = {
                dialogTime.show()
            },
            modifier = Modifier
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Green,
                contentColor = Color.White
            )
        ) {
            Text("Time")
        }
        Size(height = 20F, width = 2F)
        Button(
            onClick = {
                viewModel.addRemainder(alarmSetup)
                (context as? Activity)?.finish()
            },
            modifier = Modifier
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Add Reminder")
        }
    }
}

@Composable
fun Demo_DropDownMenu(viewModel: ReminderViewModel) {

    val typeText by viewModel.type.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row() {
            Text(
                text = typeText,
                modifier = Modifier.clickable(onClick = { expanded = true })
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                viewModel.onTypeChanged("Professional")
                expanded = false
            }) {
                Text(text = "Professional")
            }
            Divider()
            DropdownMenuItem(onClick = {
                viewModel.onTypeChanged("Student")
                expanded = false
            }) {
                Text(text = "Student")
            }
        }
    }
}

@Composable
fun TopBar(onMenuClicked: () -> Unit, title:String) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        navigationIcon = { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.clickable(onClick = onMenuClicked)) },
        backgroundColor = Color(0xFF1E90FF)
    )
}




//--------------------------------------------------

