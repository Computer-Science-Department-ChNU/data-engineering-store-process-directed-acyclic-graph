package ua.edu.chnu.kkn.dag.familytree.common

import org.folg.gedcom.model.GedcomTag
import ua.edu.chnu.kkn.dag.familytree.neo.Sex
import java.time.LocalDate
import java.time.MonthDay
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatterBuilder
import java.util.*

fun printAllGedcomTags(level: Int, tag: GedcomTag) {
    var localLevel = level
    var str: String? = localLevel++.toString() + " "
    if (tag.id != null) {
        str += "@" + tag.id + "@ " + tag.tag
    } else {
        str += tag.tag
        if (tag.value != null) {
            str += " " + tag.value
        } else if (tag.ref != null) {
            str += " @" + tag.ref + "@"
        }
    }
    println(str)
    for (tag2 in tag.children) {
        printAllGedcomTags(localLevel, tag2)
    }
}

fun getSex(tag: GedcomTag?): Sex {
    return when (tag?.value) {
        "M" -> Sex.MALE
        "F" -> Sex.FEMALE
        else -> Sex.UNDEFINED
    }
}

fun parseDate(date: String): LocalDate? {
    val cleanData = if (date.hasAbbreviation()) date.substring(3).trim()
    else if (date.contains('/')) date.split('/')[0].trim()
    else date.trim()

    val parser = DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .optionalStart()
        .appendPattern("[d MMM yyyy][MMM yyyy][yyyy]")
        .toFormatter(Locale.ENGLISH)

    return when (val parsedDate = parser.parseBest(cleanData, LocalDate::from, YearMonth::from, Year::from)) {
        is LocalDate -> parsedDate
        is YearMonth -> parsedDate.atDay(12)
        is Year -> parsedDate.atMonthDay(MonthDay.of(6, 12))
        else -> null
    }
}

fun String.hasAbbreviation() = this.startsWith("ABT")
        || this.startsWith("AFT")
        || this.startsWith("BEF")