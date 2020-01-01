package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.{Tensor => PyTensor}

class Tensor[T, S <: Shape] private[core] (val tensor: PyTensor) {
    override def toString: String = tensor.toString

    def dtype: DataType[T] = ??? // TODO 
    def shape: S = Shape.fromSeq(tensor.shape).asInstanceOf[S]
    def name: String = tensor.name
    def device: Option[String] = Option(tensor.device).filter(_.trim.nonEmpty)

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

given boolTensorOps: [S <: Shape](self: Tensor[Boolean, S]) {
    def unary_~ : Tensor[Boolean, S] = new Tensor[Boolean, S](~self.tensor)
    def & (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](self.tensor & that.tensor)
    def | (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](self.tensor | that.tensor)
    def ^ (that: Tensor[Boolean, S]): Tensor[Boolean, S] = new Tensor[Boolean, S](self.tensor ^ that.tensor)
}
