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
  ///////////    
  // Types //
  ///////////
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

  /////////////
  // Modules //
  /////////////
  def nn: NeuralNetwork = py.native
  def train: Training = py.native

  //////////////////
  // Constructors //
  //////////////////

  def Variable(initialValue: Tensor): Variable = py.native

  def constant[T : py.Reader : py.Writer](value: T, dtype: py.NoneOr[DType], shape: Seq[Int]): Tensor =
    as[py.Dynamic].constant(value, dtype, shape).as[Tensor]
  
  def random_uniform[T : py.Reader : py.Writer](
    shape: Seq[Int], min: T, max: T, dtype: DType = float32
  ): Tensor =
    as[py.Dynamic].random_uniform(shape, min, max, dtype).as[Tensor]


  def placeholder(`type`: DType): Tensor = py.native

  def placeholder(`type`: DType, shape: Seq[py.NoneOr[Int]]): Tensor = py.native

  def matmul(a: Tensor, b: Tensor): Tensor = py.native

  //////////
  // Math //
  //////////
  def abs(x: Tensor): Tensor = py.native
  def accumulate_n(inputs: Seq[Tensor]): Tensor = py.native
  def acos(x: Tensor): Tensor = py.native
  def acosh(x: Tensor): Tensor = py.native
  def add(x: Tensor, y: Tensor): Tensor = py.native
  def add_n(inputs: Seq[Tensor]): Tensor = py.native
  def angle(input: Tensor): Tensor = py.native
  def argmax(input: Tensor, axis: Seq[Int], output_type: DType): Tensor = py.native
  def argmin(input: Tensor, axis: Seq[Int], output_type: DType): Tensor = py.native
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
  def count_nonzero(t: Tensor, axis: Seq[Int], dtype: DType): Tensor = py.native // TODO test
  def cumprod(x: Tensor, axis: Seq[Int], exclusive: Boolean, reverse: Boolean): Tensor = py.native
  def cumsum(x: Tensor, axis: Seq[Int], exclusive: Boolean, reverse: Boolean): Tensor = py.native

  def floor(x: Tensor): Tensor = py.native
  def pow(x: Tensor, y: Tensor): Tensor = py.native
  def square(t: Tensor): Tensor = py.native
  def sin(t: Tensor): Tensor = py.native
  def sinh(t: Tensor): Tensor = py.native
  
  def tan(t: Tensor): Tensor = py.native
  def tanh(t: Tensor): Tensor = py.native
  
  
  

  def identity: PyFunction = py.native

  def identity(t: Tensor): Tensor = py.native

  def zeros(shape: Seq[Int], dtype: DType = float32): Tensor = py.native
  
  def zeros_like(tensor: Tensor, dtype: py.NoneOr[DType] = py.None): Tensor = py.native

  def reshape(tensor: Tensor, shape: Seq[Int]): Tensor = py.native
  
  def reduce_mean(t: Tensor): Tensor = as[py.Dynamic].reduce_mean(t).as[Tensor]

  def reduce_mean(t: Tensor, axis: Seq[Int]): Tensor = 
    as[py.Dynamic].reduce_mean(t, PythonList.seqToPythonList(axis)).as[Tensor]
  
  def transpose(t: Tensor): Tensor = py.native

  def gradients(ys: Tensor | Seq[Tensor], xs: Tensor | Seq[Tensor]): Seq[Tensor] = py.native

  def gradients(ys: Tensor, xs: Seq[Tensor], grad_ys: Tensor): Seq[Tensor] = py.native

  def initialize_all_variables(): Operation = py.native

  def global_variables_initializer(): Operation = py.native

  def Session(): Session = py.native

  def InteractiveSession(): Session = py.native

  def cond(c: Tensor, ifTrue: py.Object, ifFalse: py.Object): Tensor = py.native

  def contrib: Contrib = py.native
}
