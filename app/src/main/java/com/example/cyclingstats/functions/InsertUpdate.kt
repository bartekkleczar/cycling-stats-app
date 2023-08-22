package com.example.cyclingstats.functions

fun insertUpdate(actual: String, update: String): String {
    return if(update == actual || update == "") {
        actual
    }else {
        update
    }
}