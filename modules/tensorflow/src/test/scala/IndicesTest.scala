import org.junit.Test
import org.junit.Assert._

import ch.epfl.tensorflow.api.core._

class IndicesTest {
    @Test def `indices 1`(): Unit = {
        assertEquals((0 :: 2 :: SNil).indices, Set(0, 2))
    }

    @Test def `indices 2`(): Unit = {
        assertEquals((0 :: 1 :: 2 :: SNil).indices, Set(0, 1, 2))
    }

    @Test def `indices empty for SNil`(): Unit = {
        assertEquals(SNil.indices, Set.empty)
    }

    @Test def `Contains false for SNil`(): Unit = {
        val res = valueOf[Indices.Contains[SNil, 1]]
        assertFalse(res)
    }

    @Test def `Contains true 1`(): Unit = {
        val res = valueOf[Indices.Contains[1 :: SNil, 1]]
        assertTrue(res)
    }
    
    @Test def `Contains true 2`(): Unit = {
        val res = valueOf[Indices.Contains[1 :: 2 :: 3 :: SNil, 2]]
        assertTrue(res)
    }

    @Test def `Contains false`(): Unit = {
        val res = valueOf[Indices.Contains[1 :: 2 :: 3 :: SNil, 12]]
        assertFalse(res)
    }
}
