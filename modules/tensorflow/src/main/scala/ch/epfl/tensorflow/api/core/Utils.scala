package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.py

object Utils {
    /** 
      * Encode a Dotty union type of `Indices | py.None` to a ScalaPy `py.|[py.None.type, Indices]`
      * in order to be able to pass it to the ScalaPy facade. Note that this relies on an implicit
      * conversion in ScalaPy (either `py.|.fromLeft` or `py.|.fromRight`).
      */
    def encodeAxis(axis: Indices | py.None.type): py.NoneOr[Seq[Int]] = axis match {
        case py.None => py.None
        case axis: Indices => axis.indices.toSeq
    }
}