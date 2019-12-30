import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class TensorFlowTest {
    @Test def `reduce_mean(tensor) ==> tensor of shape SNil`(): Unit = {
        val tensor = TensorFlow.zeros(2 #: 2 #: SNil)
        val res = TensorFlow.reduce_mean(tensor)
        assertEquals(SNil, res.shape)
    }
}
