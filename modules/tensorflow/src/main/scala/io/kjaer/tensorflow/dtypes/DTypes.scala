package io.kjaer.tensorflow.dtypes

trait DTypes {
    def float16: DataType[Float16] = DataType.FLOAT16
    def float32: DataType[Float] = DataType.FLOAT32
    def float64: DataType[Double] = DataType.FLOAT64
    def bfloat16: DataType[BFloat16] = DataType.BFLOAT16
    def complex64: DataType[Complex64] = DataType.COMPLEX64
    def complex128: DataType[Complex128] = DataType.COMPLEX128
    def int8: DataType[Int8] = DataType.INT8
    def uint8: DataType[UInt8] = DataType.UINT8
    def uint16: DataType[UInt16] = DataType.UINT16
    def uint32: DataType[UInt32] = DataType.UINT32
    def uint64: DataType[UInt64] = DataType.UINT64
    def int16: DataType[Int16] = DataType.INT16
    def int32: DataType[Int] = DataType.INT32
    def int64: DataType[Long] = DataType.INT64
    def bool: DataType[Boolean] = DataType.BOOL
    def string: DataType[String] = DataType.STRING
    def qint8: DataType[QInt8] = DataType.QINT8
    def quint8: DataType[QUInt8] = DataType.QUINT8
    def qint16: DataType[QInt16] = DataType.QINT16
    def quint16: DataType[QUInt16] = DataType.QUINT16
    def qint32: DataType[QInt32] = DataType.QINT32
    def resource: DataType[Resource] = DataType.RESOURCE
    def variant: DataType[Variant] = DataType.VARIANT
}
