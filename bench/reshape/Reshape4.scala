import io.kjaer.tensorflow.core._

object Reshape4 {
  val tensor1 = tf.zeros(40 #: 40 #: 10 #: 20 #: SNil)
  val tensor2 = tf.zeros(23 #: 239 #: 19 #: 2 #: SNil)
  val tensor3 = tf.zeros(18 #: 19 #: 14 #: 200 #: SNil)
  val tensor4 = tf.zeros(203 #: 2 #: 10 #: 1 #: SNil)
  val tensor5 = tf.zeros(128 #: 128 #: 32 #: 2 #: SNil)

  val reshaped1 = tf.reshape(tensor1, 20 #: 20 #: 20 #: 40 #: SNil)
  val reshaped2 = tf.reshape(tensor2, 239 #: 23 #: 19 #: 2 #: SNil)
  val reshaped3 = tf.reshape(tensor3, 342 #: 2800 #: SNil)
  val reshaped4 = tf.reshape(tensor4, 58 #: 7 #: 10 #: SNil)
  val reshaped5 = tf.reshape(tensor5, 256 #: 64 #: 64 #: SNil)
}