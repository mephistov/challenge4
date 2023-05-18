package com.nicolascastilla.challenge4.views.compose


import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.nicolascastilla.domain.entities.AlarmSetup
import java.util.*

@Composable
fun DateDialog(onDialogResult: (AlarmSetup) -> Unit): DatePickerDialog {

    val context = LocalContext.current
    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()

    calendar.get(Calendar.YEAR).also { year = it }
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            var fixedDay = "$dayOfMonth"
            var fixedMonth = "${month + 1}"
            if((month + 1) < 10) {
                fixedMonth = "0${month + 1}"
            }
            if ((dayOfMonth) <10) {
                fixedDay = "0$dayOfMonth"
            }
            val alarmSetupDate = AlarmSetup().apply {
                dateValue = "$fixedDay/${fixedMonth}/$year"
                this.year = year
                this.month = (month + 1)
                this.day = dayOfMonth

            }
            onDialogResult(alarmSetupDate)
        }, year, month, day
    )
   // datePickerDialog.show()
    return datePickerDialog

   }