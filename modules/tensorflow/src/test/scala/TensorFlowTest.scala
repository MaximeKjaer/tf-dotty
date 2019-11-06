import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class TensorFlowTest {
    @Test def `reduce_mean(tensor) ==> tensor of shape HNil`(): Unit = {
        val tensor = TensorFlow.zeros(2 #: 2 #: HNil)
        val res = TensorFlow.reduce_mean(tensor)
        assertEquals(HNil, res.shape)
    }

    @Test def `reduce_mean(tensor, 0) ==> tensor of shape 2 #: HNil`(): Unit = {
        val tensor = TensorFlow.zeros(2 #: 2 #: HNil)
        val res = TensorFlow.reduce_mean(tensor, 0)
        assertEquals(2 #: HNil, res.shape)
    }
}
