package ch.epfl.tensorflow.core

import me.shadaj.scalapy.tensorflow.{Operation => PyOperation}

class Operation private[core] (val operation: PyOperation)
