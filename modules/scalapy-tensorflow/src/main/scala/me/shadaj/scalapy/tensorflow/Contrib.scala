package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

@py.native trait Contrib extends py.Object {
  def layers: Layers = py.native
}
