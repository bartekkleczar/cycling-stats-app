package com.example.cyclingstats.functions

fun formatFloat(float: String?): Float {
    if(float == null) return 0.0f
    var out = ""
    var outFloat = 0.0f
    for(i in float){
        if(i == ',') {
            out += "."
            continue
        }
        out += i
    }
    try {
        outFloat = out.toFloat()
    }catch(e: NumberFormatException){
        return 0.0f
    }
    return outFloat
}