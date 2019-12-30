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


    @Test def `indices empty`(): Unit = {
        assertEquals(SNil.indices, Set.empty)
    }
}
