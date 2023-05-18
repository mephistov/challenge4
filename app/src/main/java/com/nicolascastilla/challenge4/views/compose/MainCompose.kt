package com.nicolascastilla.challenge4.views.compose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge4.R
import com.nicolascastilla.challenge4.viewmodels.ReminderViewModel
import com.nicolascastilla.challenge4.views.activities.AddReminderView
import com.nicolascastilla.challenge4.views.activities.ReminderInfoActivity
import com.nicolascastilla.domain.entities.ReminderEntity

val items = arrayListOf<ReminderEntity>(
    ReminderEntity(
        id = 0,
        name = "test1",
        description = "description1",
        date = 0.0,
        image = "",
        type = "type1",
        humaDate = "dd/mm/yyyy HH:mm"
    ),ReminderEntity(
        id = 0,
        name = "test2",
        description = "description2",
        date = 0.0,
        image = "",
        type = "type2",
        humaDate = "dd/mm/yyyy HH:mm"
    ))

@Composable
fun InitViewMainCompose(){

    val viewModel = viewModel<ReminderViewModel>()
   // val coroutineScope= rememberCoroutineScope()
    val myItems by viewModel.myItems.collectAsState(emptyList())

    StartInitMainView(myItems)

}

@Composable
fun StartInitMainView(items: List<ReminderEntity>){
    var modifier: Modifier = Modifier
    val scaffolState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffolState,
    ) {
        modifier.padding(it)
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (listElem, button2) = createRefs()
            val context = LocalContext.current
            LazyColumn(

                modifier = Modifier.constrainAs(listElem) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)

                }
                    .fillMaxSize()


            ){
                items(items) { item ->
                    RowElemnt(item,context)
                }
            }
            Button(
                onClick = {
                    val intent = Intent(context, AddReminderView::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.constrainAs(button2) {
                    bottom.linkTo(parent.bottom, margin = 9.dp)
                    end.linkTo(parent.end, margin = 8.dp)

                }
            ) {
                Text(text = "+")
            }
        }

    }
}

@Composable
fun RowElemnt(remanderData: ReminderEntity, context: Context){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                val intent = Intent(context, ReminderInfoActivity::class.java).apply {
                    putExtra("REMAINDER_ID",remanderData.id)
                }
                context.startActivity(intent)
            })
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.notificacion_2),
            contentDescription = "Notificación",
            modifier = Modifier.size(50.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name: ${remanderData.name}",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = remanderData.humaDate,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = "Description: ${remanderData.description}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Type: ${remanderData.type}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Status: ${remanderData.status}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StartInitMainView(items)
}