import ch.epfl.tensorflow.api.core._
import ch.epfl.tensorflow.api.core.TF.tf

object Reshape8 {
  val tensor1 = tf.zeros(2 #: 1 #: 2 #: 1 #: 40 #: 40 #: 10 #: 20 #: SNil)
  val tensor2 = tf.zeros(2 #: 1 #: 2 #: 1 #: 23 #: 239 #: 19 #: 2 #: SNil)
  val tensor3 = tf.zeros(2 #: 1 #: 2 #: 1 #: 18 #: 19 #: 14 #: 200 #: SNil)
  val tensor4 = tf.zeros(2 #: 1 #: 2 #: 1 #: 203 #: 2 #: 10 #: 1 #: SNil)
  val tensor5 = tf.zeros(2 #: 1 #: 2 #: 1 #: 128 #: 128 #: 32 #: 2 #: SNil)

  val reshaped1 = tf.reshape(tensor1, 80 #: 20 #: 20 #: 40 #: SNil)
  val reshaped2 = tf.reshape(tensor2, 4 #: 239 #: 23 #: 19 #: 2 #: SNil)
  val reshaped3 = tf.reshape(tensor3, 4 #: 342 #: 2800 #: SNil)
  val reshaped4 = tf.reshape(tensor4, 58 #: 7 #: 40 #: SNil)
  val reshaped5 = tf.reshape(tensor5, 256 #: 128 #: 128 #: SNil)
}