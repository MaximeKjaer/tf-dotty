package tensorflow.api.core

import scala.compiletime.S

type Dimension = Int & Singleton

sealed trait Shape extends Product with Serializable {
    import Shape._

    /** Prepend the head to this */
    def #:[H <: Dimension, This >: this.type <: Shape](head: H): H #: This = 
        tensorflow.api.core.#:(head, this)

    /** Concat with another shape **/
    def ++(that: Shape): this.type Concat that.type = {
        val res: Shape = this match {
            case SNil => that
            case x #: xs => x #: (xs ++ that)
        }
        res.asInstanceOf[this.type Concat that.type]
    }

    def reverse: Reverse[this.type] = {
        val res: Shape = this match {
            case SNil => SNil
            case x #: xs => xs.reverse ++ (x #: SNil)
        }
        res.asInstanceOf[Reverse[this.type]]
    }

    def rank: Size[this.type] = {
        val res: Int = this match {
            case SNil => 0
            case head #: tail => 1 + tail.rank
        }
        res.asInstanceOf[Size[this.type]]
    }

    def numElements: NumElements[this.type] = {
        val res: Int = this match {
            case SNil => 0
            case head #: tail => head * Math.max(1, tail.numElements)
        }
        res.asInstanceOf[NumElements[this.type]]
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

    type Concat[X <: Shape, Y <: Shape] <: Shape = X match {
        case SNil => Y
        case x #: xs => x #: Concat[xs, Y]
    }

    type Reverse[X <: Shape] <: Shape = X match {
        case SNil => SNil
        case x #: xs => Concat[Reverse[xs], x #: SNil]
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

