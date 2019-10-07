object Main {
  import tensorflow.api.core._
  import tensorflow.api.core.intEncoding

  val scalar = Shape.scalar
  val vector = Shape.vector(20)
  val matrix1 = Tensor(Shape.matrix(10, 20))
  val matrix2 = Tensor(Shape.matrix(20, 10))

  def main(args: Array[String]): Unit = {
    val x = matrix1 + matrix1 + matrix1
    // val y = matrix1 * matrix2
  }
}
