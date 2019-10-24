import tensorflow.api.core.{given, _}
import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.{TensorFlow => tf}

object Main {
  
  def main(args: Array[String]): Unit = {
    val list = py.global.range(1, 3 + 1)
    val listSum = py.global.sum(list)
    println(listSum.as[Int])

    

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
