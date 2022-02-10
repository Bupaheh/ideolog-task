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

    val file: File? by mainArgument {
        description = "Log file path"
    }
}


fun main(args: Array <String>) {
    val params = Parameters.parse(args)
    val limit = params.limit
    val threadNameColumn = params.column
    val file = params.file

    if (params.help || file == null) {
        println(params.toString())
        return
    }

    file.takeIf { it.exists() }?.run {
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