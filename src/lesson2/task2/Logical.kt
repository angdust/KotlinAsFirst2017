@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import java.lang.Math.abs

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean =
        number % 10 + (number / 10) % 10 == (number / 100) % 10 + (number / 1000) % 10

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = when {
    x2 == x1 -> true
    y2 == y1 -> true
    abs(x1 - x2) == abs(y1 - y2) -> true
    else -> false
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean =
        sqr(x2 - x1) + sqr(y2 - y1) == sqr(r2 - r1)

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    val a1: Int
    val b1: Int
    val r1: Int
    val s1: Int
    when {
        a == maxOf(a, b, c) -> {
            a1 = b; b1 = c
        }
        b == maxOf(a, b, c) -> {
            a1 = a; b1 = c
        }
        else -> {
            a1 = a; b1 = b
        }
    }
    if (r > s) {
        r1 = r; s1 = s
    } else {
        r1 = s; s1 = r
    }
    return if (a1 > b1) {
        (a1 <= r1) && (b1 <= s1)
    } else (b1 <= r1) && (a1 <= s1)
}