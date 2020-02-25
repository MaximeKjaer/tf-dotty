package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.{PyFunction, |}
import me.shadaj.scalapy.tensorflow.math.Math
import me.shadaj.scalapy.tensorflow.dtypes.{DType, DTypes}

@py.native trait TensorFlow extends py.Object with Math with DTypes {
  /////////////
  // Modules //
  /////////////
  def dtypes: DTypes = py.native
  def math: Math = py.native
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

  def identity: PyFunction = py.native

  def identity(t: Tensor): Tensor = py.native

  def zeros(shape: Seq[Int], dtype: DType = float32): Tensor = py.native
  
  def zeros_like(tensor: Tensor, dtype: py.NoneOr[DType] = py.None): Tensor = py.native

  def reshape(tensor: Tensor, shape: Seq[Int]): Tensor =
    as[py.Dynamic].reshape(tensor, PythonList.seqToPythonList(shape)).as[Tensor]
  
  def transpose(t: Tensor): Tensor = py.native

  def gradients(ys: Tensor | Seq[Tensor], xs: Tensor | Seq[Tensor]): Seq[Tensor] = py.native

  def gradients(ys: Tensor, xs: Seq[Tensor], grad_ys: Tensor): Seq[Tensor] = py.native

  def initialize_all_variables(): Operation = py.native

  def global_variables_initializer(): Operation = py.native

  def Session(): Session = py.native

  def InteractiveSession(): Session = py.native

  def cond(c: Tensor, ifTrue: py.Object, ifFalse: py.Object): Tensor = py.native

  def broadcast_to(input: Tensor, shape: Seq[Int]): Tensor = py.native

  def contrib: Contrib = py.native
}
