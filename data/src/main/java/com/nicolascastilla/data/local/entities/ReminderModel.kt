package com.nicolascastilla.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remainder_table")
data class ReminderModel(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    val name: String,
    val description: String,
    val type: String,
    val date: Double,
    val image:String,
    val huma_date:String,
    val status:String
)
