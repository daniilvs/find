@file:Suppress("UNUSED_PARAMETER", "unused", "NAME_SHADOWING")

package find

import java.io.File
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.lang.IllegalArgumentException

class FindFile (parser: ArgParser) {
    private val r by parser.flagging(
        "-r", help = "Поиск во всех поддиректориях"
    )
    private val directoryName by parser.storing(
        "-d", "--name", help = "Директория для поиска"
    ).default("input/default")
    private val fileName by parser.positional(
        "Имя файла, который необходимо найти"
    )
    private var filePath: String = ""

    init {
        if (!File(directoryName).exists())
            throw IllegalArgumentException("Введенной директории не существует")
    }

    // Фнукция для поиска по поддиректориям
    private fun sub(path: File): Boolean {
        val listOfFiles = path.listFiles()
        var subRes = false
        if (listOfFiles == null)
            return false
        else {
            for (file in listOfFiles) {
                if (file.isFile && file.name == fileName) {
                    filePath = file.absolutePath
                    return true
                } else {
                    if (sub(file))
                        subRes = true
                    else {
                        sub(file)
                        filePath = file.absolutePath + "\\$fileName"
                    }
                }
            }
        }
        return subRes
    }

    // Основная функция
    fun find(): Boolean {
        val path = File(directoryName)
        val res: Boolean
        if (!r) {
            res = (File("$directoryName\\$fileName").exists() &&
                    File("$directoryName\\$fileName").isFile)
            filePath = "$directoryName\\$fileName"
        } else {
            res = sub(path)
        }
        return res
    }

    fun write() {
        val res = find()
        if (res)
            println("Файл находится по пути: $filePath")
        else
            println("Файл не найден")
    }
}