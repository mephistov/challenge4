package com.nicolascastilla.domain.entities

data class AlarmSetup(
    var dateValue:String = "",
    var timeValue:String= "",
    var year:Int= 0,
    var month:Int = 0,
    var day:Int = 0,
    var hour: Int = 0,
    var min:Int = 0,
    var sec: Int = 0,
    var timestamp: Double = 0.0
)
