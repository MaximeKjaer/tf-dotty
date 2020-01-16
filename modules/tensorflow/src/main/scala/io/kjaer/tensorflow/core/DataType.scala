package io.kjaer.tensorflow.core

import me.shadaj.scalapy.py.{Reader, Writer, PyValue}
import me.shadaj.scalapy.tensorflow
import me.shadaj.scalapy.tensorflow.TF.tf

opaque type Float16 = Short
opaque type BFloat16 = Short
case class Complex64(real: Float, imaginary: Float)
case class Complex128(real: Double, imaginary: Double)
opaque type Int8 = Byte
opaque type UInt8 = Byte
opaque type UInt16 = Short
opaque type UInt32 = Int
opaque type UInt64 = Double
opaque type Int16 = Short
opaque type QInt8 = Byte
opaque type QUInt8 = Byte
opaque type QInt16 = Short
opaque type QUInt16 = Short
opaque type QInt32 = Int
opaque type Resource = Long
opaque type Variant = Long


given shortReader: (floatReader: Reader[Float]) => Reader[Short] {
    def read(v: PyValue): Short = floatReader.read(v).toShort
}

given shortWriter: (floatWriter: Writer[Float]) => Writer[Short] {
    def write(s: Short): PyValue = floatWriter.write(s.toFloat)
}

enum DataType[T] {
    case FLOAT16 extends DataType[Float16]
    case FLOAT32 extends DataType[Float]
    case FLOAT64 extends DataType[Double]
    case BFLOAT16 extends DataType[BFloat16]
    case COMPLEX64 extends DataType[Complex64]
    case COMPLEX128 extends DataType[Complex128]
    case INT8 extends DataType[Int8]
    case UINT8 extends DataType[UInt8]
    case UINT16 extends DataType[UInt16]
    case UINT32 extends DataType[UInt32]
    case UINT64 extends DataType[UInt64]
    case INT16 extends DataType[Int16]
    case INT32 extends DataType[Int]
    case INT64 extends DataType[Long]
    case BOOL extends DataType[Boolean]
    case STRING extends DataType[String]
    case QINT8 extends DataType[QInt8]
    case QUINT8 extends DataType[QUInt8]
    case QINT16 extends DataType[QInt16]
    case QUINT16 extends DataType[QUInt16]
    case QINT32 extends DataType[QInt32]
    case RESOURCE extends DataType[Resource]
    case VARIANT extends DataType[Variant]

    def dtype: tensorflow.DType = this match {
        case FLOAT16 => tf.float16
        case FLOAT32 => tf.float32
        case FLOAT64 => tf.float64
        case BFLOAT16 => tf.bfloat16
        case COMPLEX64 => tf.complex64
        case COMPLEX128 => tf.complex128
        case INT8 => tf.int8
        case UINT8 => tf.uint8
        case UINT16 => tf.uint16
        case UINT32 => tf.uint32
        case UINT64 => tf.uint64
        case INT16 => tf.int16
        case INT32 => tf.int32
        case INT64 => tf.int64
        case BOOL => tf.bool
        case STRING => tf.string
        case QINT8 => tf.qint8
        case QUINT8 => tf.quint8
        case QINT16 => tf.qint16
        case QUINT16 => tf.quint16
        case QINT32 => tf.qint32
        case RESOURCE => tf.resource
        case VARIANT => tf.variant
    }
    
    override def toString: String = dtype.toString
}
