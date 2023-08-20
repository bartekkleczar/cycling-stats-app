package com.example.cyclingstats.functions

fun timeFormat(time: String): String {
    var out = ""
    if (time.length >= 2) if(time[1] == '-') out = "0$out"
    for(i in time){
        if(i == '-'){
            out += "h:"
            continue
        }
        out += i
    }
    return out + "m"
}