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
        assertTrue(helperForTests(arrayOf("-d", "input/test/sub_3", "test.txt")))
        assertFalse(helperForTests(arrayOf("-d", "input/test", "test.txt")))
        assertTrue(helperForTests(arrayOf("-r", "-d", "input/test", "test.txt")))
        assertTrue(helperForTests(arrayOf("-r", "sub_default.txt")))
        assertThrows(java.lang.IllegalArgumentException::class.java) {
            helperForTests(arrayOf("-d", "F:/test/sub", "test.txt")) }
        assertThrows(java.lang.IllegalArgumentException::class.java) {
            helperForTests(arrayOf("-d", "input/test/sub", "test.txt")) }
        assertThrows(com.xenomachina.argparser.UnrecognizedOptionException::class.java) {
            helperForTests(arrayOf("-s", "input/test/sub_3", "test.txt")) }
        assertThrows(com.xenomachina.argparser.MissingRequiredPositionalArgumentException::class.java) {
            helperForTests(arrayOf("-d", "input/test/sub_2")) }
    }
}