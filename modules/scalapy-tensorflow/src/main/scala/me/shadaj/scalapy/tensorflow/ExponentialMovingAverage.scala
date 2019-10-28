package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

@py.native trait ExponentialMovingAverage extends py.Object {
  def apply(vars: Seq[Tensor]): Operation = py.native

  def average(v: Variable): Variable = py.native
}
