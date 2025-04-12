package com.example.kakaobooksearchapp.data.datasource

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDateTime(dateTimeString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())

    val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())

    val date = inputFormat.parse(dateTimeString) ?: dateTimeString

    return outputFormat.format(date)
}