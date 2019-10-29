package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

@py.native trait DType extends py.Object {
    def name: String = py.native
}
