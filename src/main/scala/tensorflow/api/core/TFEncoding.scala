package tensorflow.api.core

// Typeclass for TF encoding
trait TFEncoding[T] {
    def dataType: DataType[T]
    def zero: T
}

given intEncoding: TFEncoding[Int] {
    override def dataType: DataType[Int] = INT32
    override def zero: Int = 0
}

given floatEncoding: TFEncoding[Float] {
    override def dataType: DataType[Float] = FLOAT32
    override def zero: Float = 0f
}

given doubleEncoding: TFEncoding[Double] {
    override def dataType: DataType[Double] = FLOAT64
    override def zero: Double = 0d
}
