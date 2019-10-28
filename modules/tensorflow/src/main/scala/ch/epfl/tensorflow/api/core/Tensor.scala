package ch.epfl.tensorflow.api.core

import scala.compiletime.constValue
import me.shadaj.scalapy.tensorflow.{Tensor => PyTensor}

class Tensor[T : TFEncoding, S <: Shape](val tensor: PyTensor) {
    override def toString: String = tensor.toString

    /** Element-wise transformations result in a new tensor with the same dimensions */
    def unary_- : Tensor[T, S] = new Tensor[T, S](-this.tensor)

    /* Element-wise operations result in a new tensor with the same dimensions */
    def +(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S](this.tensor + that.tensor)
    def -(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S](this.tensor - that.tensor)
    def /(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S](this.tensor / that.tensor)
    def floorDiv(that: Tensor[T, S]): Tensor[T, S] = new Tensor[T, S](tensor)

    /** Element-wise comparison results in a boolean tensor with the same dimensions */
    def lt(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor)
    def le(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor)
    def ge(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor)
    def gt(that: Tensor[T, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor)

    // def reshape[NewShape <: Shape](s: NewShape)(given ev: Shape.NumElements[S] <:< Shape.NumElements[NewShape]): Tensor[T, NewShape] = new Tensor[T, NewShape]
    // def reshape[NewShape <: Shape](given ev: Shape.NumElements[S] =:= Shape.NumElements[NewShape]): Tensor[T, NewShape] = new Tensor[T, NewShape]
}

given boolTensorOps: [S <: Shape](tensor: Tensor[Boolean, S]) {
    def unary_~ : Tensor[Boolean, S] = new Tensor[Boolean, S](tensor.tensor)
    def & (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor.tensor)
    def | (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor.tensor)
    def ^ (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](tensor.tensor)
}
