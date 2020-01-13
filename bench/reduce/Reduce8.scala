import ch.epfl.tensorflow.core._

object Reduce8 {
  val tensor1 = tf.zeros(2 #: 1 #: 2 #: 1 #: 40 #: 40 #: 10 #: 20 #: SNil)
  val tensor2 = tf.zeros(2 #: 1 #: 2 #: 1 #: 23 #: 239 #: 19 #: 2 #: SNil)
  val tensor3 = tf.zeros(2 #: 1 #: 2 #: 1 #: 18 #: 19 #: 14 #: 200 #: SNil)
  val tensor4 = tf.zeros(2 #: 1 #: 2 #: 1 #: 203 #: 2 #: 10 #: 1 #: SNil)
  val tensor5 = tf.zeros(2 #: 1 #: 2 #: 1 #: 128 #: 128 #: 32 #: 2 #: SNil)

  val reshaped1 = tf.reduce_mean(tensor1, 7 :: 6 :: 4 :: 0 :: 1 :: 5 :: 2 :: 3 :: SNil)
  val reshaped2 = tf.reduce_mean(tensor2, 0 :: 1 :: 6 :: 3 :: 2 :: 7 :: 4 :: 5 :: SNil)
  val reshaped3 = tf.reduce_mean(tensor3, 1 :: 4 :: 3 :: 0 :: 5 :: 2 :: 6 :: 7 :: SNil)
  val reshaped4 = tf.reduce_mean(tensor4, 3 :: 0 :: 2 :: 1 :: 7 :: 6 :: 4 :: 5 :: SNil)
  val reshaped5 = tf.reduce_mean(tensor5, 6 :: 7 :: 4 :: 2 :: 3 :: 5 :: 1 :: 0 :: SNil)
}