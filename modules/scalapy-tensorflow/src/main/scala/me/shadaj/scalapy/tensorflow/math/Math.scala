package me.shadaj.scalapy.tensorflow.math

import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.TF.tf
import me.shadaj.scalapy.tensorflow.dtypes.DType
import me.shadaj.scalapy.tensorflow.TensorFlow
import me.shadaj.scalapy.tensorflow.Tensor
import me.shadaj.scalapy.tensorflow.PythonList.seqToPythonList
import me.shadaj.scalapy.tensorflow.dtypes.DType

@py.native trait Math extends py.Object {
  def abs(x: Tensor): Tensor = py.native
  def accumulate_n(inputs: Seq[Tensor]): Tensor = py.native
  def acos(x: Tensor): Tensor = py.native
  def acosh(x: Tensor): Tensor = py.native
  def add(x: Tensor, y: Tensor): Tensor = py.native
  def add_n(inputs: Seq[Tensor]): Tensor = py.native
  def angle(input: Tensor): Tensor = py.native
  def argmax(input: Tensor, axis: py.NoneOr[Int]): Tensor = py.native
  def argmin(input: Tensor, axis: py.NoneOr[Int]): Tensor = py.native
  def asin(t: Tensor): Tensor = py.native
  def asinh(t: Tensor): Tensor = py.native
  def atan(t: Tensor): Tensor = py.native
  def atan2(y: Tensor, x: Tensor): Tensor = py.native
  def atanh(t: Tensor): Tensor = py.native
  // TODO bessel funcitons
  def betainc(a: Tensor, b: Tensor, x: Tensor): Tensor = py.native
  // TODO bincount
  def ceil(x: Tensor): Tensor = py.native
  // TODO confusion_matrix
  // TODO conj
  def cos(t: Tensor): Tensor = py.native
  def cosh(t: Tensor): Tensor = py.native

  def count_nonzero(
    input_tensor: Tensor,
    axis: py.NoneOr[Seq[Int]] = py.None,
    keepdims: py.NoneOr[Boolean] = py.None,
    dtype: DType = tf.int64
  ): Tensor = axis.map(
    none => as[py.Dynamic].count_nonzero(input_tensor, none, keepdims, dtype).as[Tensor],
    seq  => as[py.Dynamic].count_nonzero(input_tensor, seqToPythonList(seq), keepdims, dtype).as[Tensor]
  )

  def cumprod(x: Tensor, axis: Int, exclusive: Boolean, reverse: Boolean): Tensor = py.native
  def cumsum(x: Tensor, axis: Int, exclusive: Boolean, reverse: Boolean): Tensor = py.native

  def floor(x: Tensor): Tensor = py.native
  
  def matmul(a: Tensor, b: Tensor): Tensor = py.native
  def pow(x: Tensor, y: Tensor): Tensor = py.native
  def reduce_mean(
    input_tensor: Tensor,
    axis: py.NoneOr[Seq[Int]] = py.None,
    keepdims: Boolean = false
  ): Tensor = axis.map(
    none => as[py.Dynamic].reduce_mean(input_tensor, none, keepdims).as[Tensor],
    seq  => as[py.Dynamic].reduce_mean(input_tensor, seqToPythonList(seq), keepdims).as[Tensor]
  )

  def square(t: Tensor): Tensor = py.native
  def sin(t: Tensor): Tensor = py.native
  def sinh(t: Tensor): Tensor = py.native
  
  def tan(t: Tensor): Tensor = py.native
  def tanh(t: Tensor): Tensor = py.native
}
