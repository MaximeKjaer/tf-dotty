import org.junit.Test
import org.junit.Assert._

import ch.epfl.tensorflow.api.core._

class SelectTest {
    val select1 = ^ :: v :: ^ :: SNil
    val select2 = v :: v :: v :: SNil
    val select3 = ^ :: ^ :: ^ :: SNil

    @Test def `select1.selectedIndices`(): Unit = {
        assertEquals(select1.selectedIndices, Seq(0, 2))
    }

    @Test def `select2.selectedIndices`(): Unit = {
        assertEquals(select2.selectedIndices, Seq.empty)
    }

    @Test def `select3.selectedIndices`(): Unit = {
        assertEquals(select3.selectedIndices, Seq(0, 1, 2))
    }
}
