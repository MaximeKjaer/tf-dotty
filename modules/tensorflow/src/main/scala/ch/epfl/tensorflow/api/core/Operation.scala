package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.{Operation => PyOperation}

class Operation private[core] (val operation: PyOperation)
