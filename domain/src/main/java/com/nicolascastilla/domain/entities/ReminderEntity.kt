package com.nicolascastilla.domain.entities

data class  ReminderEntity(
    var id:Long = 0,
    var name: String = "",
    var description: String= "",
    var type: String= "",
    var date: Double = 0.0,
    var humaDate: String= "",
    var image:String = "dd/mm/yyyy",
    var status:String = "saved"
)
