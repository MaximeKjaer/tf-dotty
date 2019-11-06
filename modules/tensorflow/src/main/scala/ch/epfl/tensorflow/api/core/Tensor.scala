package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.{Tensor => PyTensor}

class Tensor[T : TFEncoding, S <: Shape, L <: Labels] private[core] (val tensor: PyTensor) {    
    override def toString: String = tensor.toString

    def dtype: DataType[T] = summon[TFEncoding[T]].dataType
    def shape(given s: Materialize[S]): S = s.value

    def name: String = tensor.name
    def device: Option[String] = Option(tensor.device).filter(_.trim.nonEmpty)

    /** Element-wise transformations result in a new tensor with the same dimensions */
    def unary_- : Tensor[T, S, L] = new Tensor[T, S, L](-this.tensor)

    /* Element-wise operations result in a new tensor with the same dimensions */
    def +(that: Tensor[T, S, L]): Tensor[T, S, L] = new Tensor[T, S, L](this.tensor + that.tensor)
    def -(that: Tensor[T, S, L]): Tensor[T, S, L] = new Tensor[T, S, L](this.tensor - that.tensor)
    def /(that: Tensor[T, S, L]): Tensor[T, S, L] = new Tensor[T, S, L](this.tensor / that.tensor)
    def floorDiv(that: Tensor[T, S, L]): Tensor[T, S, L] = new Tensor[T, S, L](tensor)

    /** Element-wise comparison results in a boolean tensor with the same dimensions */
    def lt(that: Tensor[T, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](tensor)
    def le(that: Tensor[T, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](tensor)
    def ge(that: Tensor[T, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](tensor)
    def gt(that: Tensor[T, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](tensor)

    // def reshape[NewShape <: Shape](s: NewShape)(given ev: Shape.NumElements[A] <:< Shape.NumElements[NewShape]): Tensor[T, NewShape] = new Tensor[T, NewShape]
    // def reshape[NewShape <: Shape](given ev: Shape.NumElements[A] =:= Shape.NumElements[NewShape]): Tensor[T, NewShape] = new Tensor[T, NewShape]
}

given boolTensorOps: [S <: Shape, L <: Labels](self: Tensor[Boolean, S, L]) {
    def unary_~ : Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](~self.tensor)
    def & (that: Tensor[Boolean, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](self.tensor & that.tensor)
    def | (that: Tensor[Boolean, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](self.tensor | that.tensor)
    def ^ (that: Tensor[Boolean, S, L]): Tensor[Boolean, S, L] = new Tensor[Boolean, S, L](self.tensor ^ that.tensor)
}

object Tensor {
    type Aux[T, A <: Axes] = Tensor[T, Axes.ShapeOf[A], Axes.LabelsOf[A]]

    def apply[T : TFEncoding, A <: Axes](tensor: PyTensor): Tensor.Aux[T, A] = {
        type S = Axes.ShapeOf[A]
        type L = Axes.LabelsOf[A]
        new Tensor[T, S, L](tensor)
    }
}
