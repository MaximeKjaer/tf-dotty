package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.TF.tf
import me.shadaj.scalapy.tensorflow.TensorFlow

object TensorFlow {
    def zeros[S <: Shape](given shape: ShapeOf[S]): Tensor[Int, S] = {
        new Tensor[Int, S](tf.zeros(shape.value.toSeq))
    }
}
