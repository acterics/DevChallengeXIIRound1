package it.devchallenge.snake.common.extension

import java.util.*

fun <T> Random.select(n: Int, list: List<T>): List<T> =

        if (n == 0) emptyList()
        else {
            val value = list[nextInt(list.size)]
            select(n - 1, list.filter { it != value }) + value
        }

fun <T> Random.select(list: List<T>): T = list[nextInt(list.size)]
