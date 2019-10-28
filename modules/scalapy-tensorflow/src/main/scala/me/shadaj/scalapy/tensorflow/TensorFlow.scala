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
  def Variable(initialValue: Tensor): Variable = py.native

  def random_uniform(shape: PythonList[Int], min: Double, max: Double): Tensor = py.native

  def placeholder(`type`: String): Tensor = py.native

  def placeholder(`type`: String, shape: Seq[py.NoneOr[Int]]): Tensor = py.native

  def nn: NeuralNetwork = py.native
  def train: Training = py.native

  def matmul(a: Tensor, b: Tensor): Tensor = py.native

  def identity: PyFunction = py.native

  def identity(t: Tensor): Tensor = py.native

  def zeros(shape: Seq[Int]): Tensor = py.native

  def reshape(tensor: Tensor, shape: PythonList[Int]): Tensor = py.native

  def add_n(ts: Seq[Tensor]): Tensor = py.native

  def square(t: Tensor): Tensor = py.native

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
