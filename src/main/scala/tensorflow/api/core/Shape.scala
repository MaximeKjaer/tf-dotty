package tensorflow.api.core

import scala.compiletime.S

type Dimension = Int & Singleton

sealed trait Shape extends Product with Serializable {
    import Shape._

    /** Prepend the head to this */
    def #:[H <: Dimension, This >: this.type <: Shape](head: H): H #: This = 
        tensorflow.api.core.#:(head, this)

    def rank[This >: this.type <: Shape]: Size[This] = this match {
        case SNil => 0.asInstanceOf[Size[This]]
        case head #: tail => (1 + tail.rank).asInstanceOf[Size[This]]
    }

    def numElements: Int = this match {
        case SNil => 0
        case head #: tail => head * Math.max(1, tail.numElements)
    }
}

object Shape {
    type Size[X <: Shape] <: Int = X match {
        case SNil => 0
        case x #: xs => S[Size[xs]]
    }

    def scalar: SNil = SNil
    def vector(length: Dimension): length.type #: SNil = length #: SNil
    def matrix(rows: Dimension, columns: Dimension): rows.type #: columns.type #: SNil = rows #: columns #: SNil
}

final case class #:[H <: Dimension, T <: Shape](head: H, tail: T) extends Shape

sealed trait SNil extends Shape
case object SNil extends SNil

