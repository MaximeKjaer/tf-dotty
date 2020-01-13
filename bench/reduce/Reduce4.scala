import ch.epfl.tensorflow.core._

object Reduce4 {
  val tensor1 = tf.zeros(40 #: 40 #: 10 #: 20 #: SNil)
  val tensor2 = tf.zeros(23 #: 239 #: 19 #: 2 #: SNil)
  val tensor3 = tf.zeros(18 #: 19 #: 14 #: 200 #: SNil)
  val tensor4 = tf.zeros(203 #: 2 #: 10 #: 1 #: SNil)
  val tensor5 = tf.zeros(128 #: 128 #: 32 #: 2 #: SNil)

  val reshaped1 = tf.reduce_mean(tensor1, 0 :: 1 :: 2 :: 3 :: SNil)
  val reshaped2 = tf.reduce_mean(tensor2, 0 :: 1 :: 3 :: 2 :: SNil)
  val reshaped3 = tf.reduce_mean(tensor3, 1 :: 3 :: 0 :: 2 :: SNil)
  val reshaped4 = tf.reduce_mean(tensor4, 3 :: 0 :: 2 :: 1 :: SNil)
  val reshaped5 = tf.reduce_mean(tensor5, 2 :: 3 :: 1 :: 0 :: SNil)
}
