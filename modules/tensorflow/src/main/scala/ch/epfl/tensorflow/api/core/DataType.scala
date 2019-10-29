package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow
import me.shadaj.scalapy.tensorflow.TF.tf

sealed trait DataType[T] {
    def dtype: tensorflow.DType
}

case object INT32 extends DataType[Int] {
    override def dtype = tf.int32
}

case object FLOAT32 extends DataType[Float] {
    override def dtype = tf.float32
}

case object FLOAT64 extends DataType[Double] {
    override def dtype = tf.float64
}

case object BOOLEAN extends DataType[Boolean] {
    override def dtype = tf.bool
}
