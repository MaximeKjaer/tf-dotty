package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.{Variable => PyVariable}

class Variable[T : TFEncoding, S <: Shape, L <: Labels] private[core] (
    val variable: PyVariable
) extends Tensor[T, S, L](variable)

object Variable {
    type Aux[T, A <: Axes] = Variable[T, Axes.ShapeOf[A], Axes.LabelsOf[A]]
}
