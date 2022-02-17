package ideologTask

import com.apurebase.arkenv.Arkenv
import com.apurebase.arkenv.util.argument
import com.apurebase.arkenv.util.mainArgument
import com.apurebase.arkenv.util.parse
import java.io.File

object Parameters : Arkenv("ideolog-task") {
    val limit: Int by argument("--limit", "-l") {
        description = "Limit of output"
        defaultValue = { 10 }
    }

    val column: Int by argument("--column", "-c") {
        description = "Thread name column number"
        defaultValue = { 4 }
    }

    val delimiters: String by argument("--delimiter", "-d") {
        description = "Column delimiters"
        defaultValue = { "\t" }
    }

    val file: File? by mainArgument {
        description = "Log file path"
    }
}

fun getThreadToCountList(file: File, delimiters: String,
                         threadNameColumn: Int, limit: Int) =
    file.takeIf { it.exists() }?.run {
    readLines()
        .map { it.split(delimiters) }
        .filter { it.size > threadNameColumn }
        .map { it[threadNameColumn] }
        .groupBy { it }
        .mapValues { it.value.size }
        .toList()
        .sortedByDescending { it.second }
        .take(limit)
}


fun main(args: Array <String>) {
    Parameters.parse(args)
    if (Parameters.help || Parameters.file == null) {
        println(Parameters.toString())
        return
    }

    Parameters.run {
        getThreadToCountList(file!!, delimiters, column, limit)?.forEach {
            println("${it.first} ${it.second} f")
        } ?: println("File doesn't exist")
    }
}