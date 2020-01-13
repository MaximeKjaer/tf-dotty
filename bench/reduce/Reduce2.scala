import ch.epfl.tensorflow.core._

object Reduce2 {
  val tensor1 = tf.zeros(40 #: 40 #: SNil)
  val tensor2 = tf.zeros(23 #: 239 #: SNil)
  val tensor3 = tf.zeros(18 #: 19 #: SNil)
  val tensor4 = tf.zeros(203 #: 2 #: SNil)
  val tensor5 = tf.zeros(128 #: 128 #: SNil)

  val reshaped1 = tf.reduce_mean(tensor1, 0 :: 1 :: SNil)
  val reshaped2 = tf.reduce_mean(tensor2, 0 :: 1 :: SNil)
  val reshaped3 = tf.reduce_mean(tensor3, 1 :: 0 :: SNil)
  val reshaped4 = tf.reduce_mean(tensor4, 0 :: 1 :: SNil)
  val reshaped5 = tf.reduce_mean(tensor5, 1 :: 0 :: SNil)
}