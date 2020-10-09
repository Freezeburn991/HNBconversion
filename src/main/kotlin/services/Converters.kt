package services

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


fun convertStringToDateTime(datum: String): DateTime {

    val formatter: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")

    return formatter.parseDateTime(datum)
}