import ch.epfl.tensorflow.core._

object Reshape2 {
  val tensor1 = tf.zeros(40 #: 40 #: SNil)
  val tensor2 = tf.zeros(23 #: 239 #: SNil)
  val tensor3 = tf.zeros(18 #: 19 #: SNil)
  val tensor4 = tf.zeros(203 #: 2 #: SNil)
  val tensor5 = tf.zeros(128 #: 128 #: SNil)

  val reshaped1 = tf.reshape(tensor1, 2 #: 20 #: 40 #: SNil)
  val reshaped2 = tf.reshape(tensor2, 239 #: 23 #: SNil)
  val reshaped3 = tf.reshape(tensor3, 342 #: SNil)
  val reshaped4 = tf.reshape(tensor4, 58 #: 7 #: SNil)
  val reshaped5 = tf.reshape(tensor5, 256 #: 64 #: SNil)
}