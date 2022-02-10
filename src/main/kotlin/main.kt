package ideologTask

import java.io.File

const val limit = 10
const val threadNameColumn = 4

fun main(args: Array <String>) {
    if (args.isEmpty()) {
        println("Empty arguments")
        return
    }

    File(args.first()).takeIf { it.exists() }?.run {
        readLines()
            .map { it.split("\t") }
            .filter { it.size > threadNameColumn }
            .map { it[threadNameColumn] }
            .groupBy { it }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
            .take(limit)
            .forEach { println("${it.first} ${it.second}") }
    } ?: println("File doesn't exist")
}