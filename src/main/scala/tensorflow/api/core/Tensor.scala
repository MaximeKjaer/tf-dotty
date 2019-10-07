package tensorflow.api.core

class Tensor[T : TFEncoding](val shape: Shape) {
    type S = shape.type

    /** Element-wise transformations result in a new tensor with the same dimensions */
    def unary_- : Tensor.Aux[T, S] = Tensor[T](shape)
    def abs: Tensor.Aux[T, S] = Tensor[T](shape)

    /* Element-wise operations result in a new tensor with the same dimensions */
    def +(that: Tensor.Aux[T, S]): Tensor.Aux[T, S] = Tensor[T](shape)
    def -(that: Tensor.Aux[T, S]): Tensor.Aux[T, S] = Tensor[T](shape)
    def /(that: Tensor.Aux[T, S]): Tensor.Aux[T, S] = Tensor[T](shape)
    def **(that: Tensor.Aux[T, S]): Tensor.Aux[T, S] = Tensor[T](shape)
    def floorDiv(that: Tensor.Aux[T, S]): Tensor.Aux[T, S] = Tensor[T](shape)

    /** Element-wise logical functions for boolean tensors */ 
    def unary_~(implicit ev: T <:< Boolean) : Tensor.Aux[T, S] = Tensor[T](shape)
    def &(that: Tensor.Aux[Boolean, S])(implicit ev: T <:< Boolean): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)
    def |(that: Tensor.Aux[Boolean, S])(implicit ev: T <:< Boolean): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)
    def ^(that: Tensor.Aux[Boolean, S])(implicit ev: T <:< Boolean): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)

    /** Element-wise comparison results in a boolean tensor with the same dimensions */
    def lt(that: Tensor.Aux[T, S]): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)
    def le(that: Tensor.Aux[T, S]): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)
    def ge(that: Tensor.Aux[T, S]): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)
    def gt(that: Tensor.Aux[T, S]): Tensor.Aux[Boolean, S] = Tensor[Boolean](shape)
}

object Tensor {
    type Aux[T, S2] = Tensor[T] { type S = S2 }
    
    def apply[T : TFEncoding](shape: Shape): Tensor.Aux[T, shape.type] = 
        new Tensor[T](shape).asInstanceOf[Tensor.Aux[T, shape.type]]

    def zeros[T : TFEncoding](shape: Shape): Tensor.Aux[T, shape.type] =
        fill(shape)(summon[TFEncoding[T]].dataType.zero)

    def fill[T : TFEncoding](shape: Shape)(value: T): Tensor.Aux[T, shape.type] = {
        val dataType = summon[TFEncoding[T]].dataType
        val numBytes = shape.numElements * dataType.byteSize
        null // TODO
    }
}
