import ch.epfl.tensorflow.api.core._
import ch.epfl.tensorflow.api.core.TF.tf

object Main {
  
  def main(args: Array[String]): Unit = {
    val test = tf.zeros(20 #: 10 #: SNil, tf.bool)
    val matrix1 = tf.zeros(20 #: 10 #: SNil)
    val matrix2 = tf.zeros_like(matrix1)
    val matrix3 = tf.zeros(20 #: 30 #: SNil)

    println(matrix1)
    println(matrix2)

    val res1: Tensor[Float, 20 #: 10 #: SNil.type] = (matrix1 + matrix2 - matrix2) / matrix2
    val res2: Tensor[Float, 20 #: 10 #: SNil.type] = tf.floor(tf.abs(tf.pow(res1, matrix2)))

    val res1T = tf.transpose(res1)

    val x = tf.reduce_mean(matrix1, SNil)
    println(x)
    
    println(res2)
    println(res2.dtype)

    println(tf.constant[Int, Int, 10 #: 3 #: SNil](3, tf.int32, shape = 10 #: 3 #: SNil))
    println(tf.constant[Float, Float, SNil](3f, tf.float32))
    println(tf.Variable(res2))
  }
}
