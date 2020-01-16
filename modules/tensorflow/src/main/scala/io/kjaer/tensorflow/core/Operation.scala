package io.kjaer.tensorflow.core

import me.shadaj.scalapy.tensorflow.{Operation => PyOperation}

class Operation private[core] (val operation: PyOperation)
