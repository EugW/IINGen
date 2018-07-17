package pro.eugw.iingen

import java.util.*

fun main(args: Array<String>) {
    println("Enter 0 for questionnaire filling, or any other count for completely random filling")
    if (readInt() == 0)
        startQuestionnaire()
    else
        startRandom()
}

fun startRandom() {
    var iin = ""
    iin += randomDates(0..99)
    iin += randomDates(1..12)
    iin += randomDates(1..31)
    checkDates(iin)
    iin += randomCentury()
    iin += randomUstNumber()
    iin += specialAlgorithm(iin)
    println("Your IIN: $iin")
}

fun startQuestionnaire() {
    var iin = ""
    println("Enter year of birth, or blank for random")
    iin += readDates(0..99)
    println("Enter month of birth, or blank for random")
    iin += readDates(1..12)
    println("Enter day of birth, or blank for random")
    iin += readDates(1..31)
    checkDates(iin)
    println("Enter century of birth, or blank for random")
    iin += readCentury()
    iin += randomUstNumber()
    iin += specialAlgorithm(iin)
    println("Your IIN: $iin")
}

fun randomCentury(): String {
    return (1 + Random().nextInt(6)).toString()
}

fun randomDates(range: IntRange): String {
    val rand = range.first + Random().nextInt(range.last - range.first + 1)
    var str = rand.toString()
    if (rand < 10)
        str = "0$str"
    return str
}

fun specialAlgorithm(iin: String): String {
    var mSum = 0
    iin.forEachIndexed { index, c ->
        mSum += c.toInt() * mass[index]
    }
    val r1 = mSum.rem(11)
    if (r1 in (0..9))
        return r1.toString()
    mSum = 0
    iin.forEachIndexed { index, c ->
        mSum += c.toInt() * mass[index + 2]
    }
    val r2 = mSum.rem(11)
    if (r2 in (0..9))
        return r2.toString()
    else
        println("This IIN can't be used")
    return "1"
}

val mass = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2)

fun randomUstNumber(): String {
    var number = Random().nextInt(10000).toString()
    while (number.length != 4)
        number = "0$number"
    return number
}


fun checkDates(string: String) {
    if (string.length != 6) {
        println("Dates length isn't 6")
        return
    }
    val yr = string.substring(0, 2)
    val mh = string.substring(2, 4)
    val dy = string.substring(4, 6)
    if (dy.toInt() !in (1..daysInMonth[mh]!!.toInt()))
        println("Wrong day in month")
    if (yr.toInt().rem(4) != 0 && mh == "02" && dy == "29")
        println("Feb has only 28 days in not a leap year")
}

val daysInMonth = mapOf(
        Pair("01", "31"),
        Pair("02", "29"),
        Pair("03", "31"),
        Pair("04", "30"),
        Pair("05", "31"),
        Pair("06", "30"),
        Pair("07", "31"),
        Pair("08", "31"),
        Pair("09", "30"),
        Pair("10", "31"),
        Pair("11", "30"),
        Pair("12", "31")
)

fun readCentury(): String {
    var readLine = readLine()
    if (readLine.isEmpty()) {
        return (1 + Random().nextInt(6)).toString()
    }
    while (true)
        try {
            if (readLine.length != 3)
                throw RuntimeException()
            if (readLine.substring(0, 2).toInt() !in (19..21))
                throw RuntimeException()
            when (readLine.substring(0, 2).toInt()) {
                19 -> when (readLine[2]) {
                    'm' -> return "1"
                    'f' -> return "2"
                }
                20 -> when (readLine[2]) {
                    'm' -> return "3"
                    'f' -> return "4"
                }
                21 -> when (readLine[2]) {
                    'm' -> return "5"
                    'f' -> return "6"
                }
            }
        } catch (e: Exception) {
            println("You must enter two-digit number from 19 to 21 and one letter m or f")
            println("Try again")
            readLine = readLine()
        }
}

fun readDates(range: IntRange): String {
    var readLine = readLine()
    if (readLine.isEmpty()) {
        val rand = range.first + Random().nextInt(range.last - range.first + 1)
        var str = rand.toString()
        if (rand < 10)
            str = "0$str"
        return str
    }
    while (true)
        try {
            if (readLine.toInt() !in range)
                throw RuntimeException()
            if (readLine.length != 2)
                throw RuntimeException()
            return readLine
        } catch (e: Exception) {
            println("You must enter two-digit number")
            println("Try again")
            readLine = readLine()
        }
}

fun readInt(): Int? {
    var readLine = readLine()
    while (true)
        try {
            return if (readLine.isEmpty()) return null else readLine.toInt()
        } catch (e: Exception) {
            println("You must enter number")
            println("Try again")
            readLine = readLine()
        }
}

fun readLine(): String = kotlin.io.readLine() ?: ""