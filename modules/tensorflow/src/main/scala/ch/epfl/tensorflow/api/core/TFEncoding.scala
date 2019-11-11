package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.py

sealed trait TFEncoding[T : py.Reader : py.Writer] {
    def dataType: DataType[T]
}

object TFEncoding {
    given TFEncoding[Int] {
        override def dataType: DataType[Int] = INT32
    }

    given TFEncoding[Long] {
        override def dataType: DataType[Long] = INT64
    }

    given TFEncoding[Float] {
        override def dataType: DataType[Float] = FLOAT32
    }

    given TFEncoding[Double] {
        override def dataType: DataType[Double] = FLOAT64
    }
    
    given TFEncoding[Boolean] {
        override def dataType: DataType[Boolean] = BOOLEAN
    }
}

sealed trait IsNumeric[T : TFEncoding]
object IsNumeric {
    given IsNumeric[Int]
    given IsNumeric[Float]
    given IsNumeric[Double]
}
