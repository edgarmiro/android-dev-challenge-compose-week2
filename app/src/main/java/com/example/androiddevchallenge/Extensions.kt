package com.example.androiddevchallenge

fun String.toSeconds(): Int {
    val value = if (this.length >= 6) this else this.padStart(6, '0')
    val hour = value.substring(0, 2).toInt()
    val min = value.substring(2, 4).toInt()
    val sec = value.substring(4, 6).toInt()

    return (hour * 60 * 60) + (min * 60) + sec
}

fun String.toHHMMss(): String {
    val value = if (this.length >= 6) this else this.padStart(6, '0')
    val hour = value.substring(0, 2)
    val min = value.substring(2, 4)
    val sec = value.substring(4, 6)

    return "${hour}h ${min}m ${sec}s"
}

fun Int.toHHMMss(): String {
    val hour = this / 3600
    val min = (this % 3600) / 60
    val sec = this % 60

    return String.format("%02dh %02dm %02ds", hour, min, sec)
}
