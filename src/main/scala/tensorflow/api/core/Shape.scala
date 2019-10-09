package tensorflow.api.core

import scala.compiletime.S

type Dimension = Int & Singleton

sealed trait Shape extends Product with Serializable {
    import Shape._

    type NE = NumElements[this.type]

    /** Prepend the head to this */
    def #:[H <: Dimension, This >: this.type <: Shape](head: H): H #: This = 
        tensorflow.api.core.#:(head, this)

    def rank[This >: this.type <: Shape]: Size[This] = {
        val res: Int = this match {
            case SNil => 0
            case head #: tail => 1 + tail.rank
        }
        res.asInstanceOf[Size[This]]
    }

    def numElements: NE = {
        val res: Int = this match {
            case SNil => 0
            case head #: tail => head * Math.max(1, tail.numElements)
        }
        res.asInstanceOf[NE]
    }
}

object Shape {
    import TypeUtils.*

    type Size[X <: Shape] <: Int = X match {
        case SNil => 0
        case x #: xs => S[Size[xs]]
    }

    type NumElements[X <: Shape] <: Int = X match {
        case SNil => 0
        case x #: xs => x * NumElementsNonEmpty[xs]
    }

    protected type NumElementsNonEmpty[X <: Shape] <: Int = X match {
        case SNil => 1
        case x #: xs => x * NumElementsNonEmpty[xs]
    }

    def scalar: SNil = SNil
    def vector(length: Dimension): length.type #: SNil = length #: SNil
    def matrix(rows: Dimension, columns: Dimension): rows.type #: columns.type #: SNil = rows #: columns #: SNil
}

final case class #:[H <: Dimension, T <: Shape](head: H, tail: T) extends Shape {
    override def toString = head match {
        case _ #: _ => s"($head) #: $tail"
        case _      => s"$head #: $tail"
    }
}

sealed trait SNil extends Shape
case object SNil extends SNil

