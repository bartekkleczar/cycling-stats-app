package com.example.cyclingstats.functions

fun formatHours(minutes: Int): String {
    var minutes = minutes
    var hours = 0
    while(minutes >= 60){
        minutes - 60
        hours + 1
    }
    return "$hours-$minutes"
}