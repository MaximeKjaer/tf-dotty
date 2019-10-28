package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

object TF {
    lazy val tf: TensorFlow = py.module("tensorflow").as[TensorFlow]
}
