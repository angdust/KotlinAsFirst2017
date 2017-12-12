@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

import com.sun.org.apache.regexp.internal.RE
import lesson1.task1.accountInThreeYears
import java.util.regex.Pattern

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */

fun daysInMonth(day: Int, month: Int, year: Int): Int {
    var cday = -1
    when {
        (month <= 7) && (month % 2 == 1) && (day <= 31) -> cday = day
        (month > 7) && (month % 2 == 0) && (day <= 31) -> cday = day
        (month != 2) && (month <= 7) && (month % 2 == 0) && (day <= 30) -> cday = day
        (month != 2) && (month > 7) && (month % 2 == 1) && (day <= 30) -> cday = day
        (month == 2) && (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) && (day <= 29) -> cday = day
        (month == 2) && (((year % 4 != 0) || (year % 100 == 0)) || (year % 400 != 0)) && (day <= 28) -> cday = day
    }
    return cday
}

fun dateStrToDigit(str: String): String {
    val x = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
            "октября", "ноября", "декабря")
    val str1 = str.split(" ")
    if (str1.size != 3) return ""
    try {
        var day = 0
        var month = 0
        var year = 0
        if (str1[2].toInt() > 0) year = str1[2].toInt()
        month = x.indexOf(str1[1]) + 1
        if (month < 1) return ""
        day = daysInMonth(str1[0].toInt(), month, year)
        return String.format("%02d.%02d.%d", day, month, year)
    } catch (month: NumberFormatException) {
        return ""
    }

}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val x = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
            "октября", "ноября", "декабря")
    val parts = digital.split(".")
    val digital1 = mutableListOf<Int>()
    var mounth = ""
    var day = 0
    var year = 0
    if (parts.size != 3) return ""
    try {
        for (part in parts) {
            digital1.add(part.toInt())
        }
        if (digital1[2] > 0) {
            year = digital1[2]
        }
        if (digital1[1] == 0) return ""
        mounth = x[digital1[1] - 1]
        if (digital1[1] != 0) day = daysInMonth(digital1[0], digital1[1], digital1[2])
        return String.format("%d %s %d", day, mounth, year)
    } catch (part: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val parts = phone.split(" ", "(", ")", "-", "+")
    val phone1 = mutableListOf<Int>()
    var k = 0
    if (phone == "") return ""
    if (phone.matches(Regex("\\+?[ ]*[0-9]+[ -]*(\\([-0-9 ]+\\))?[-0-9 ]*"))) {
        if (phone[0] == '+') k = 1
    } else return ""
    return try {
        for (part in parts) {
            if (part != "") phone1.add(part.toInt())
        }
        if (k == 1) "+" + phone1.joinToString(separator = "")
        else phone1.joinToString(separator = "")
    } catch (part: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(" ").filter { (it != "-") && (it != "%") }
    val results: MutableList<Int> = mutableListOf()
    try {
        for (part in parts)
            results.add(part.toInt())
    } catch (part: NumberFormatException) {
        return -1
    }
    return results.max() ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int = TODO()

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val expression1 = expression.split(" ")
    var sum = 0
    try {
        sum = expression1[0].toInt()
    } catch (sum: NumberFormatException) {
        throw IllegalArgumentException()
    }
    if (expression1.size == 2) throw IllegalArgumentException()
    try {
        for (i in 2 until expression1.size step 2) {
            when {
                expression1[i - 1] == "+" -> sum += expression1[i].toInt()
                expression1[i - 1] == "-" -> sum -= expression1[i].toInt()
                else -> throw IllegalArgumentException()
            }
        }

    } catch (i: NumberFormatException) {
        throw IllegalArgumentException()
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    val correctDescription = description.split(" ", "; ").filter { it != "" }
    var max = 0.0
    var k = 0
    try {
        if (correctDescription.size >= 2)
            max = correctDescription[1].toDouble()
        else return ""
    } catch (max: NumberFormatException) {
        return ""
    }
    try {
        for (i in 3 until correctDescription.size step 2) {
            if (correctDescription[i].toDouble() >= 0.0) {
                if (correctDescription[i].toDouble() > max) {
                    max = correctDescription[i].toDouble()
                    k = i - 1
                }
            } else {
                return ""
            }
        }
    } catch (i: NumberFormatException) {
        return ""
    }

    return correctDescription[k]
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()

fun myFun(people: List<String>): String {
    val people1: MutableList<List<String>> = mutableListOf()
    var spart = ""
    var result: List<String>
    var result1 = mutableListOf<String>()
    var k = 0
    for (part in people) {
        spart = part.replace(Regex(",[ ]*|:[ ]*|[ ]+"), ",")
        people1.add(spart.split(","))
    }
    for (i in 0 until people1.size - 1) {
        k = 0
        for (j in i + 1 until people1.size) {
            for (i1 in 3 until people1[i].size)
                for (j1 in 3 until people1[j].size)
                    if (people1[i][i1] == people1[j][j1]) k = 1
        }
        if (k == 0) {
            result = spget(people1[i])
            result1.add(result.joinToString(separator = ", ") + " -> " + people1[i][1] + " " + people1[i][0])
        }
    }
    k = 0
    val i = people1.size - 1
    for (j in 0 until people1.size - 1) {
        for (i1 in 3 until people1[i].size)
            for (j1 in 3 until people1[j].size)
                if (people1[i][i1] == people1[j][j1]) k = 1
    }
    if (k == 0) {
        result = spget(people1[i])
        result1.add(result.joinToString(separator = ", ") + " -> " + people1[i][1] + " " + people1[i][0])
    }
    return result1.joinToString(separator = " ")
}

fun spget(list: List<String>): List<String> {
    val list1 = mutableListOf<String>()
    for (i in 3 until list.size)
        list1.add(list[i])
    return list1
}

