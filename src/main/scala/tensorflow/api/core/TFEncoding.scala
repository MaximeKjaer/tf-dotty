package tensorflow.api.core

// Typeclass for TF encoding
trait TFEncoding[T] {
    def dataType: DataType[T]
}

object TFEncoding {
    given intEncoding: TFEncoding[Int] {
        override def dataType: DataType[Int] = INT32
        
    }

    given floatEncoding: TFEncoding[Float] {
        override def dataType: DataType[Float] = FLOAT32
    }

    given doubleEncoding: TFEncoding[Double] {
        override def dataType: DataType[Double] = FLOAT64
    }

    given boolEncoding: TFEncoding[Boolean] {
        override def dataType: DataType[Boolean] = BOOLEAN
    }
}
