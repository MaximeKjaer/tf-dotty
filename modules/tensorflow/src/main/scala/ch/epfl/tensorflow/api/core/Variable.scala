package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.{Variable => PyVariable}

class Variable[T, S <: Shape] private[core] (val variable: PyVariable) extends Tensor[T, S](variable)
