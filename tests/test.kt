package tests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.xenomachina.argparser.ArgParser
import find.FindFile

fun helperForTests(args: Array<String>) =
    ArgParser(args).parseInto(::FindFile).find()

class Test {
    @Test
    fun find() {
        assertTrue(helperForTests(arrayOf("-d", "input/test/sub", "test.txt")))
        assertFalse(helperForTests(arrayOf("-d", "input/test", "test.txt")))
        assertTrue(helperForTests(arrayOf("-r", "-d", "input/test", "test.txt")))
        assertTrue(helperForTests(arrayOf("-r", "-d", "find.kt")))
    }
}