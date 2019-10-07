package tensorflow.api.core

// TODO look into new (G)ADT enum syntax
sealed trait DataType[T] {
    def byteSize: Int
    def zero: T
}

case object INT32 extends DataType[Int] {
    override def byteSize: 4 = 4
    override def zero: Int = 0
}

case object FLOAT32 extends DataType[Float] {
    override def byteSize: 4 = 4
    override def zero: Float = 0f
}

case object FLOAT64 extends DataType[Double] {
    override def byteSize: 8 = 8
    override def zero: Double = 0d
}

case object BOOLEAN extends DataType[Boolean] {
    override def byteSize: 1 = 1
    override def zero: Boolean = false
}
