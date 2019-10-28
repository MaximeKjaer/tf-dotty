import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core.TypeUtils._

class TypeUtilsTest {
    @Test def `1 + 1 == 2`(): Unit = {
        val res = valueOf[1 + 1]
        assertEquals(2, res)
    }

    @Test def `213 + 312 == 525`(): Unit = {
        val res = valueOf[213 + 312]
        assertEquals(525, res)
    }

    @Test def `1 * 1 == 1`(): Unit = {
        val res = valueOf[1 * 1]
        assertEquals(1, res)
    }

    @Test def `2 * 2 == 4`(): Unit = {
        val res = valueOf[2 * 2]
        assertEquals(4, res)
    }

    @Test def `3 * 4 == 12`(): Unit = {
        val res = valueOf[3 * 4]
        assertEquals(12, res)
    }

    @Test def `1 == 1 is true`(): Unit = {
        assertTrue(valueOf[1 == 1])
    }

    @Test def `2 == 1 is false`(): Unit = {
        assertFalse(valueOf[2 == 1])
    }
}
