package tensorflow.api.core

// TODO look into new (G)ADT enum syntax
sealed trait DataType[T] {
    def byteSize: Int
}

case object INT32 extends DataType[Int] {
    override def byteSize: 4 = 4
}

case object FLOAT32 extends DataType[Float] {
    override def byteSize: 4 = 4
}

case object FLOAT64 extends DataType[Double] {
    override def byteSize: 8 = 8
}
