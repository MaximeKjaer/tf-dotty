import ch.epfl.tensorflow.api.core._

object Main {
  
  def main(args: Array[String]): Unit = {
    println(TensorFlow.zeros[20 #: 10 #: SNil])

    
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
