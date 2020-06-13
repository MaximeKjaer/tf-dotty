package io.kjaer.tensorflow.core

import io.kjaer.compiletime._
import me.shadaj.scalapy.tensorflow.{Variable => PyVariable}

class Variable[T, S <: Shape] private[core] (val variable: PyVariable) extends Tensor[T, S](variable)
