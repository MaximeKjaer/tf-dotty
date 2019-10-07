package tensorflow.api.core

class Tensor[T : TFEncoding](val shape: Shape) {
    type S = shape.type

    def +(that: Tensor.Aux[T, S]): Tensor.Aux[T, S] = Tensor[T](shape)
    
    // def *(that: Tensor[T, Reverse[Shape]]): Tensor[T, Reverse[Shape]] = Tensor(shape.transpose())
}

object Tensor {
    type Aux[T, S2] = Tensor[T] { type S = S2 }
    
    def apply[T : TFEncoding](shape: Shape): Tensor.Aux[T, shape.type] = 
        new Tensor[T](shape).asInstanceOf[Tensor.Aux[T, shape.type]]

    def zeros[T : TFEncoding](shape: Shape): Tensor.Aux[T, shape.type] =
        fill(shape)(summon[TFEncoding[T]].zero)

    def fill[T : TFEncoding](shape: Shape)(value: T): Tensor.Aux[T, shape.type] = {
        val dataType = summon[TFEncoding[T]].dataType
        val numBytes = shape.numElements * dataType.byteSize
        null // TODO
    }
}
