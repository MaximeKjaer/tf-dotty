import ch.epfl.tensorflow.api.core._

object Main {
  
  def main(args: Array[String]): Unit = {
    val shape = 20 #: 10 #: HNil
    val labels = "x" @: "y" @: HNil
    val axes = ("x", 20) *: ("y", 10) *: HNil


    val test = TensorFlow.zeros(20 #: 10 #: HNil, TensorFlow.int32)
    val matrix1 = TensorFlow.zeros(20 #: 10 #: HNil)
    val matrix2 = TensorFlow.zeros_like(matrix1)
    val matrix3 = TensorFlow.zeros(20 #: 30 #: HNil)

    // val reshaped = TensorFlow.reshape(matrix1, 2 #: 100 #: HNil)

    println(matrix1)
    println(matrix2)

    val res1: Tensor[Float, 20 #: 10 #: HNil.type] = (matrix1 + matrix2 - matrix2) / matrix2
    val res2: Tensor[Float, 20 #: 10 #: HNil.type] = TensorFlow.floor(TensorFlow.abs(TensorFlow.pow(res1, matrix2)))
    
    println(res2)
    println(res2.dtype)

    println(TensorFlow.constant(3, shape = 10 #: 3 #: HNil))
    println(TensorFlow.constant(3f))
    println(TensorFlow.Variable(res2))
  }
}
