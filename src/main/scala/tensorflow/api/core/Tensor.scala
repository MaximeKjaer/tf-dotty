package tensorflow.api.core

import scala.compiletime.constValue

class Tensor[T : TFEncoding, S <: Shape] {
    /** Element-wise transformations result in a new tensor with the same dimensions */
    def unary_- : Tensor[T, S] = new Tensor[T, S]
    def abs: Tensor[T, S] = new Tensor[T, S]

    /* Element-wise operations result in a new tensor with the same dimensions */
    def +(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S]
    def -(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S]
    def /(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S]
    def **(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S]
    def floorDiv(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S]

    /** Element-wise comparison results in a boolean tensor with the same dimensions */
    def lt(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]
    def le(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]
    def ge(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]
    def gt(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]

    // def reshape[NewShape <: Shape](given ev: Shape.NumElements[S] <:< Shape.NumElements[NewShape]): Tensor[T, NewShape] = new Tensor[T, NewShape]
}

given boolTensorOps: [S <: Shape](tensor: Tensor[Boolean, S]) {
    def unary_~ : Tensor[Boolean, S] = new Tensor[Boolean, S]
    def & (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]
    def | (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]
    def ^ (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S]
}

object Tensor {    
    def apply[T : TFEncoding, S <: Shape]: Tensor[T, S] = new Tensor[T, S]

    inline def zeros[T : TFEncoding, S <: Shape]: Tensor[T, S] = fill[T, S](summon[TFEncoding[T]].dataType.zero)
    inline def fill[T : TFEncoding, S <: Shape](value: T): Tensor[T, S] = {
        val dataType = summon[TFEncoding[T]].dataType
        val shape = constValue[S]
        // val numBytes = shape.numElements * dataType.byteSize
        null // TODO
    }
}
