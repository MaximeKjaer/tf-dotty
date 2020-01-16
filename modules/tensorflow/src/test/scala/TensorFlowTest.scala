import org.junit.Test
import org.junit.Assert._
import io.kjaer.tensorflow.core._

class TensorFlowTest {
    def shapeFromType[S <: Shape](t: Tensor[?, S])(given s: ShapeOf[S]): S = s.value

    @Test def `reduce_mean without axes`(): Unit = {
        val tensor = tf.zeros(2 #: 2 #: SNil)
        val res = tf.reduce_mean(tensor)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along empty axes`(): Unit = {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, SNil)
        assertEquals(20 #: 22 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along axis 0`(): Unit = {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 0 :: SNil)
        assertEquals(22 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along axis 1`(): Unit = {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 1 :: SNil)
        assertEquals(20 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }    

    @Test def `reduce_mean along all axes in order`(): Unit = {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 0 :: 1 :: SNil)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reduce_mean along all axes not in order`(): Unit = {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 1 :: 0 :: SNil)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    /*
    @Test def `reduce_mean along wrong axes`(): Unit = {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 13 :: 30 :: SNil)
        assertEquals(SNil, res.shape)
    }
    */

    @Test def `reshape same shape`(): Unit = {
        val shape: 1 #: 2 #: 3 #: 4 #: SNil = 1 #: 2 #: 3 #: 4 #: SNil
        val tensor = tf.zeros(shape)
        val res = tf.reshape(tensor, shape)
        assertEquals(shape, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reshape flipped`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.reshape(tensor, 3 #: 2 #: SNil)
        assertEquals(3 #: 2 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `reshape 1`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.reshape(tensor, 6 #: SNil)
        assertEquals(6 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `argmax along axis 0`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.argmax(tensor, axis = 0)
        assertEquals(3 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    @Test def `argmax along axis 1`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.argmax(tensor, axis = 1)
        assertEquals(2 #: 4 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    /* Should not compile:
    @Test def `argmax along axis out of bounds`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.argmax(tensor, axis = 1000)
        assertEquals(3 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }
    */

    @Test def `count_nonzero with default args`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, keepdims=false)
        assertEquals(SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    /*
    @Test def `count_nonzero with keepdims`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, keepdims=true)
        assertEquals(1 #: 1 #: 1 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }
    */

    @Test def `count_nonzero along axis 0`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, 0 :: SNil, keepdims=false)
        assertEquals(3 #: 4 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }

    /* Should not compile:
    @Test def `count_nonzero along invalid axis`(): Unit = {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, 1 :: -1 :: 3203 :: 20 :: 0 :: SNil, keepdims=false)
        assertEquals(3 #: 4 #: SNil, res.shape)
        assertEquals(res.shape, shapeFromType(res))
    }
    */
}
