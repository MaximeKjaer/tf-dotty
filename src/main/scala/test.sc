import tensorflow.api.core._
import scala.compiletime.constValue

val shape = (1 #: 2 #: 3 #: SNil)

shape.rank
valueOf[Shape.Size[shape.type]]

shape.numElements
valueOf[Shape.NumElements[shape.type]]

shape.reverse
inline def rev1 = constValue[Shape.Reverse[shape.type]]

val matrix1 = Tensor[Int](Shape.matrix(10, 20))
matrix1 + matrix1 - matrix1

val boolMatrix = Tensor[Boolean](Shape.matrix(10, 20))
~boolMatrix ^ boolMatrix | boolMatrix & boolMatrix


import TypeUtils._
valueOf[32 + 32]
valueOf[8 * 8]
valueOf[4 == 4]
