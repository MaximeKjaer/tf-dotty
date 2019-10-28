package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.PyFunction

@py.native trait Layers extends py.Object {
  def batch_norm(inputs: Tensor, decay: Double, center: Boolean, scale: Boolean, epsilon: Double,
                 activation_fn: py.NoneOr[PyFunction], updates_collections: py.NoneOr[py.Object],
                 is_training: Boolean, reuse: py.NoneOr[Boolean], scope: py.NoneOr[String]): Tensor = py.nativeNamed

  def flatten(inputs: Tensor): Tensor = py.native
}
