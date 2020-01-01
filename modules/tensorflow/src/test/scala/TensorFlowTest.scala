import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class TensorFlowTest {
    @Test def `reduce_mean without axes`(): Unit = {
        val tensor = TensorFlow.zeros(2 #: 2 #: SNil)
        val res = TensorFlow.reduce_mean(tensor)
        assertEquals(SNil, res.shape)
    }

    @Test def `reduce_mean along axis 0`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 0 :: SNil)
        assertEquals(22 #: SNil, res.shape)
    }

    @Test def `reduce_mean along axis 1`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 1 :: SNil)
        assertEquals(20 #: SNil, res.shape)
    }    

    @Test def `reduce_mean along all axes in order`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 0 :: 1 :: SNil)
        assertEquals(SNil, res.shape)
    }

    @Test def `reduce_mean along all axes not in order`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 1 :: 0 :: SNil)
        assertEquals(SNil, res.shape)
    }
}
