import ideologTask.getThreadToCountList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class GetTreadToCountListTest {
    private val testFile = "src/test/kotlin/testData/test_log.log"
    private val answerFile = "src/test/kotlin/testData/answer.txt"
    private val limit = 10
    private val column = 4
    private val delimiters = "\t"

    @Test
    fun `sample test`() {
        val answer = File(answerFile).readLines()
        val result = getThreadToCountList(File(testFile), delimiters, column, limit)
            ?.map { "${it.first} ${it.second}" }
        assertEquals(answer, result)
    }
}
