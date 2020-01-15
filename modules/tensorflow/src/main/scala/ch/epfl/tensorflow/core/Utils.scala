package ch.epfl.tensorflow.core

import me.shadaj.scalapy.py

object Utils {
    /** 
      * Encode a Dotty union type of `None.type | Indices` to a ScalaPy `py.|[py.None, Indices]`
      * in order to be able to pass it to the ScalaPy facade. Note that this relies on an implicit
      * conversion in ScalaPy (either `py.|.fromLeft` or `py.|.fromRight`).
      */
    def encodeAxis(axis: None.type | Indices): py.NoneOr[Seq[Int]] = axis match {
        case None => py.None
        case axis: Indices => axis.indices.toSeq
    }
}