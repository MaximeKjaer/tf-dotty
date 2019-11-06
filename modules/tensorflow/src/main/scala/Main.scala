import ch.epfl.tensorflow.api.core._

object Main {
  
  def main(args: Array[String]): Unit = {
    val shape = 20 #: 10 #: HNil
    val labels = "x" @: "y" @: HNil

    val axis1 = ax("x", 20) *: ax("y", 10) *: HNil
    val axis2 = ax("x", 20) *: ax("y", 30) *: HNil

    val test = TensorFlow.zeros(axis1, TensorFlow.int32)
    val matrix1 = TensorFlow.zeros(axis1)
    val matrix2 = TensorFlow.zeros_like(matrix1)
    val matrix3 = TensorFlow.zeros(axis2)

    // val reshaped = TensorFlow.reshape(matrix1, 2 #: 100 #: HNil)

    println(matrix1)
    println(matrix2)

    val res1 = (matrix1 + matrix2 - matrix2) / matrix2
    val res2 = TensorFlow.floor(TensorFlow.abs(TensorFlow.pow(res1, matrix2)))
    
    println(res2)
    println(res2.dtype)

    println(TensorFlow.constant(3, axis1))
    println(TensorFlow.constant(3f))
    println(TensorFlow.Variable(res2))
  }
}
