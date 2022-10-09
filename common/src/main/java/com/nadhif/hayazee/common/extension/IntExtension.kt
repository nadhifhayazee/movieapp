package com.nadhif.hayazee.common.extension


fun Int.getRuntimeString(): String {
    if (this == 0) return "-"
    return if (this < 60) {
        "$this Minutes"
    } else {
        val hour: Int = this / 60
        val minutes: Int = this % 60
        if (minutes != 0) {
            "$hour Hour $minutes Minutes"
        } else {
            "$hour Hour"
        }
    }
}