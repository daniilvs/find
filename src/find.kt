@file:Suppress("UNUSED_PARAMETER", "unused", "NAME_SHADOWING")

package find

import java.io.File
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class FindFile (parser: ArgParser) {
    private val r by parser.flagging(
        "-r", help = "Поиск во всех поддиректориях"
    )
    private val d by parser.flagging(
        "-d", help = "Директория для поиска"
    )
    private val directoryName by parser.positional(
        "Имя директории для поиска"
    ).default("\\input")
    private val fileName by parser.positional(
        "Имя файла, который необходимо найти"
    )

    // Фнукция для поиска по поддиректориям
    private fun sub(path: File): Boolean {
        val listOfFiles = path.listFiles()
        var subRes = false
        for (file in listOfFiles) {
            if (file.isDirectory || (file.isFile && file.name != fileName)) {
                sub(file)
                continue
            } else
                subRes = true
        }
        return subRes
    }

    // Основная функция
    fun find(): Boolean {
        val path = File(directoryName)
        val res: Boolean
        res = if (!r) {
            (File("$directoryName.\\$fileName").exists() &&
                    File("$directoryName.\\$fileName").isFile)
        } else {
            sub(path)
        }
        return res
    }

    fun write() {
        val res = find()
        if (res)
            println("Файл находится в данной папке")
        else
            println("Файл не найден")
    }
}