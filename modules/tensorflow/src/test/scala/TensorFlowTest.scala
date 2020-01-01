import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class TensorFlowTest {
    def shapeFromType[S <: Shape](t: Tensor[_, S])(given s: ShapeOf[S]): S = s.value

    @Test def `reduce_mean without axes`(): Unit = {
        val tensor = TensorFlow.zeros(2 #: 2 #: SNil)
        val res = TensorFlow.reduce_mean(tensor)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along empty axes`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, SNil)
        assertEquals(20 #: 22 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along axis 0`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 0 :: SNil)
        assertEquals(22 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along axis 1`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 1 :: SNil)
        assertEquals(20 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }    

    @Test def `reduce_mean along all axes in order`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 0 :: 1 :: SNil)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along all axes not in order`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 1 :: 0 :: SNil)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    /*
    @Test def `reduce_mean along wrong axes`(): Unit = {
        val tensor = TensorFlow.zeros(20 #: 22 #: SNil)
        val res = TensorFlow.reduce_mean(tensor, 13 :: 30 :: SNil)
        assertEquals(SNil, res.shape)
    }
    */
}
