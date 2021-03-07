/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
