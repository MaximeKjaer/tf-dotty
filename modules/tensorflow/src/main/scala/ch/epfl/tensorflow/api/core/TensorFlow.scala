package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.TF.tf
import me.shadaj.scalapy.tensorflow.TensorFlow

object TensorFlow {
    def zeros[S <: Shape](shape: S): Tensor[Int, S] = {
        new Tensor[Int, S](tf.zeros(shape.toSeq))
    }
}
