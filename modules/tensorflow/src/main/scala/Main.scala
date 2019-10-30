import ch.epfl.tensorflow.api.core._

object Main {
  
  def main(args: Array[String]): Unit = {
    val matrix1 = TensorFlow.zeros[20 #: 10 #: SNil]
    val matrix2 = TensorFlow.zerosLike(matrix1)
    val matrix3 = TensorFlow.zeros[20 #: 30 #: SNil]

    println(matrix1)
    println(matrix2)

    val res1: Tensor[Float, 20 #: 10 #: SNil] = (matrix1 + matrix2 - matrix2) / matrix2
    val res2: Tensor[Float, 20 #: 10 #: SNil] = TensorFlow.floor(TensorFlow.abs(TensorFlow.pow(res1, matrix2)))
    
    println(res2)
    println(res2.dtype)

    println(TensorFlow.constant(3, shape = 10 #: 3 #: SNil))
    println(TensorFlow.constant(3f))
    println(TensorFlow.Variable(res2))


    val optimizer = TensorFlow.train.GradientDescentOptimizer(0.5)
    
    /*
    val matrix1 = Tensor[Int, 1 #: 2 #: 3 #: SNil]
    val matrix2 = Tensor[Int, 3 #: 2 #: 1 #: SNil]
    val matrixBool1 = Tensor[Boolean, 1 #: 4 #: SNil]
    val matrixBool2 = Tensor[Boolean, 2 #: 2 #: SNil]

    matrix1 + matrix1 - matrix1
    ~matrixBool1 & matrixBool1
    
    // Should not compile:
    // matrix1 + matrix2
    // matrix2 + matrix1
    // ~matrix1
    */
  }
}
