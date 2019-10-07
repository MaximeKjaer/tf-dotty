object Main {
  import tensorflow.api.core._
  val scalar = Shape.scalar
  val vector = Shape.vector(20)
  val matrix1 = Tensor[Int](Shape.matrix(10, 20))
  val matrix1Bool = Tensor[Boolean](Shape.matrix(10, 20))
  val matrix2 = Tensor[Int](Shape.matrix(20, 10))

  def main(args: Array[String]): Unit = {
    val x = matrix1 + matrix1 - matrix1
    val xBool = ~matrix1Bool ^ matrix1Bool | matrix1Bool & matrix1Bool
    // val y = matrix1 * matrix2
  }
}
