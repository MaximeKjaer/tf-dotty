import ch.epfl.tensorflow.api.core._

object Main {
  
  def main(args: Array[String]): Unit = {
    val test = TensorFlow.zeros(20 #: 10 #: SNil, TensorFlow.bool)
    val matrix1 = TensorFlow.zeros(20 #: 10 #: SNil)
    val matrix2 = TensorFlow.zeros_like(matrix1)
    val matrix3 = TensorFlow.zeros(20 #: 30 #: SNil)

    println(matrix1)
    println(matrix2)

    val res1: Tensor[Float, 20 #: 10 #: SNil.type] = (matrix1 + matrix2 - matrix2) / matrix2
    val res2: Tensor[Float, 20 #: 10 #: SNil.type] = TensorFlow.floor(TensorFlow.abs(TensorFlow.pow(res1, matrix2)))

    val x = TensorFlow.reduce_mean(matrix1, ^ :: ^ :: SNil)
    println(x)
    
    println(res2)
    println(res2.dtype)

    println(TensorFlow.constant(3, shape = 10 #: 3 #: SNil))
    println(TensorFlow.constant(3f))
    println(TensorFlow.Variable(res2))
  }
}
