package com.example.cyclingstats.functions

fun formatMinutes(time: String): Int {
    var out = 0
    var minutes = ""
    var hours = ""
    var onMinutes = false
    for(i in time){
        if(i == '-'){
            onMinutes = true
            out += hours.toInt() * 60
            continue
        }
        if(!onMinutes){
            hours += i
            continue
        }
        if(onMinutes){
            minutes += i
            continue
        }
    }
    if(minutes == "") minutes = "0"
    return out + minutes.toInt()
}