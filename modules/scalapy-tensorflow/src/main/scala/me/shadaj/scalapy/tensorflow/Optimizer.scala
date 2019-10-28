package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

@py.native trait Optimizer extends py.Object {
  def minimize(loss: Tensor): Operation = py.native

  def apply_gradients(grads_and_vars: Seq[(Tensor, Variable)]): Operation = py.native
}
