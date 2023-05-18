package com.nicolascastilla.challenge4.views.compose

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.nicolascastilla.domain.entities.AlarmSetup
import java.util.*

@Composable
fun TimeDialog(onDialogResult: (AlarmSetup) -> Unit): TimePickerDialog {
    val context = LocalContext.current
    val c = Calendar.getInstance()
    val mHour = c[Calendar.HOUR_OF_DAY]
    val mMinute = c[Calendar.MINUTE]

    val pickerDialog = TimePickerDialog(
    context,
    {_, hour : Int, minute: Int ->
        val alarmSetupDate = AlarmSetup().apply {
            timeValue = "$hour:$minute"
            this.hour = hour
            this.min = minute

        }
        onDialogResult(alarmSetupDate)
    }, mHour, mMinute, false
    )

    return pickerDialog
}