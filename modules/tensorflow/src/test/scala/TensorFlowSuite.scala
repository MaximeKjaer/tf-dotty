import io.kjaer.tensorflow.core._

import scala.compiletime.testing.typeChecks

class TensorFlowSuite extends munit.FunSuite {
    def shapeFromType[S <: Shape](t: Tensor[?, S])(using s: ShapeOf[S]): S = s.value

    test("tf.math is mixed-in to top-level tf") {
        assert(typeChecks("tf.math.abs"))
        assert(typeChecks("tf.abs"))
    }

    test("tf.dtypes is mixed-in to top-level tf") {
        assert(typeChecks("tf.int32"))
        assert(typeChecks("tf.dtypes.int32"))
    }

    test("reduce_mean without axes") {
        val tensor = tf.zeros(2 #: 2 #: SNil)
        val res = tf.reduce_mean(tensor)
        assert(SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("reduce_mean along empty axes") {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, SNil)
        assert(20 #: 22 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("reduce_mean along axis 0") {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 0 :: SNil)
        assert(22 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("reduce_mean along axis 1") {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 1 :: SNil)
        assert(20 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }    

    test("reduce_mean along all axes in order") {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 0 :: 1 :: SNil)
        assert(SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("reduce_mean along all axes not in order") {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 1 :: 0 :: SNil)
        assert(SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    /*
    test("reduce_mean along wrong axes") {
        val tensor = tf.zeros(20 #: 22 #: SNil)
        val res = tf.reduce_mean(tensor, 13 :: 30 :: SNil)
        assert(SNil == res.shape)
    }
    */

    test("reshape same shape") {
        val shape: 1 #: 2 #: 3 #: 4 #: SNil = 1 #: 2 #: 3 #: 4 #: SNil
        val tensor = tf.zeros(shape)
        val res = tf.reshape(tensor, shape)
        assert(shape == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("reshape flipped") {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.reshape(tensor, 3 #: 2 #: SNil)
        assert(3 #: 2 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("reshape 1") {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.reshape(tensor, 6 #: SNil)
        assert(6 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("argmax along axis 0") {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.argmax(tensor, axis = 0)
        assert(3 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    test("argmax along axis 1") {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.argmax(tensor, axis = 1)
        assert(2 #: 4 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    /* Should not compile:
    test("argmax along axis out of bounds") {
        val tensor = tf.zeros(2 #: 3 #: SNil)
        val res = tf.argmax(tensor, axis = 1000)
        assert(3 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }
    */

    test("count_nonzero with default args") {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, keepdims=false)
        assert(SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    /*
    test("count_nonzero with keepdims") {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, keepdims=true)
        assert(1 #: 1 #: 1 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }
    */

    test("count_nonzero along axis 0") {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, 0 :: SNil, keepdims=false)
        assert(3 #: 4 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }

    /* Should not compile:
    test("count_nonzero along invalid axis") {
        val tensor = tf.zeros(2 #: 3 #: 4 #: SNil)
        val res = tf.count_nonzero(tensor, 1 :: -1 :: 3203 :: 20 :: 0 :: SNil, keepdims=false)
        assert(3 #: 4 #: SNil == res.shape)
        assert(res.shape == shapeFromType(res))
    }
    */
}
