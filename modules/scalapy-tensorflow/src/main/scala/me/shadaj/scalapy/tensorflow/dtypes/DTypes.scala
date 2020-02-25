package me.shadaj.scalapy.tensorflow.dtypes

import me.shadaj.scalapy.py

@py.native trait DType extends py.Object {
    def name: String = py.native
}

@py.native trait DTypes extends py.Object {
  def float16: DType = py.native
  def float32: DType = py.native
  def float64: DType = py.native
  def bfloat16: DType = py.native
  def complex64: DType = py.native
  def complex128: DType = py.native
  def int8: DType = py.native
  def uint8: DType = py.native
  def uint16: DType = py.native
  def uint32: DType = py.native
  def uint64: DType = py.native
  def int16: DType = py.native
  def int32: DType = py.native
  def int64: DType = py.native
  def bool: DType = py.native
  def string: DType = py.native
  def qint8: DType = py.native
  def quint8: DType = py.native
  def qint16: DType = py.native
  def quint16: DType = py.native
  def qint32: DType = py.native
  def resource: DType = py.native
  def variant: DType = py.native
}
