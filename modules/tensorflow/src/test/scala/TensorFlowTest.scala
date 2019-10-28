import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._
import me.shadaj.scalapy.py

class TensorFlowTest {
    @Test def `version is 1.14.0`(): Unit = {
        val version = py.module("tensorflow").version.VERSION.as[String]
        assertEquals("1.14.0", version)
    }
}
