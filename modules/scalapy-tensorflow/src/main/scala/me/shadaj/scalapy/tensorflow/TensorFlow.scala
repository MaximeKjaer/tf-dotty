package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.{Writer, PyFunction, |}

// some TensorFlow operations require a LIST list, not just something iterable
@py.native trait PythonList[T] extends py.Object
object PythonList {
  implicit def seqToPythonList[T](seq: Seq[T])(implicit writer: Writer[Seq[T]]): PythonList[T] = {
    py.global.list(py.Any.from(seq)(writer)).as[PythonList[T]]
  }
}

@py.native trait TensorFlow extends py.Object {
  // Types
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

  def Variable(initialValue: Tensor): Variable = py.native

  def constant[T : py.Reader : py.Writer](value: T, dtype: DType, shape: Seq[Int], name: String = "Const"): Tensor =
    as[py.Dynamic].constant(value, dtype, shape, name).as[Tensor]
  
  def random_uniform(shape: PythonList[Int], min: Double, max: Double): Tensor = py.native

  def placeholder(`type`: DType): Tensor = py.native

  def placeholder(`type`: DType, shape: Seq[py.NoneOr[Int]]): Tensor = py.native

  def nn: NeuralNetwork = py.native
  def train: Training = py.native

  def matmul(a: Tensor, b: Tensor): Tensor = py.native

  def abs(x: Tensor): Tensor = py.native

  def floor(x: Tensor): Tensor = py.native

  def identity: PyFunction = py.native

  def identity(t: Tensor): Tensor = py.native

  def zeros(shape: Seq[Int]): Tensor = py.native

  def reshape(tensor: Tensor, shape: PythonList[Int]): Tensor = py.native

  def add_n(ts: Seq[Tensor]): Tensor = py.native

  def square(t: Tensor): Tensor = py.native

  def pow(x: Tensor, y: Tensor): Tensor = py.native

  def tanh(t: Tensor): Tensor = py.native

  def reduce_mean(t: Tensor): Tensor = py.native

  def gradients(ys: Tensor | Seq[Tensor], xs: Tensor | Seq[Tensor]): Seq[Tensor] = py.native

  def gradients(ys: Tensor, xs: Seq[Tensor], grad_ys: Tensor): Seq[Tensor] = py.native

  def initialize_all_variables(): Operation = py.native

  def global_variables_initializer(): Operation = py.native

  def Session(): Session = py.native

  def InteractiveSession(): Session = py.native

  def cond(c: Tensor, ifTrue: py.Object, ifFalse: py.Object): Tensor = py.native

  def contrib: Contrib = py.native
}