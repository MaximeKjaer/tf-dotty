package ch.epfl.tensorflow.api.core

import scala.compiletime.{S, constValue}

type Dimension = Int & Singleton

sealed trait Shape extends Product with Serializable {
    import Shape._

    /** Prepend the head to this */
    def #:[H <: Dimension, This >: this.type <: Shape](head: H): H #: This = 
        ch.epfl.tensorflow.api.core.#:(head, this)

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

    def toSeq: Seq[Int] = this match {
        case SNil => Nil
        case head #: tail => head +: tail.toSeq
    }
}

object Shape {
    import TypeUtils.*

    type Map[X <: Shape, F[_ <: Dimension] <: Dimension] <: Shape = X match {
        case SNil => SNil
        case head #: tail => F[head] #: Map[tail, F]
    }

    /**
     * Type-level representation of `def foldLeft[B](z: B)(op: (B, A) ⇒ B): B`
     * @tparam B Return type of the operation
     * @tparam X Shape to fold over
     * @tparam Z Zero element
     * @tparam F Function taking an accumulator of type B, and an element of type Int, returning B
     */
    type FoldLeft[B, X <: Shape, Z <: B, F[_ <: B, _ <: Int] <: B] <: B = X match {
        case SNil => Z
        case head #: tail => FoldLeft[B, tail, F[Z, head], F]
    }

    type Size[X <: Shape] <: Int = X match {
        case SNil => 0
        case head #: tail => S[Size[tail]]
    }

    type NumElements[X <: Shape] <: Int = X match {
        case SNil => 0
        case head #: tail => FoldLeft[Int, X, 1, *]
    }

    type Concat[X <: Shape, Y <: Shape] <: Shape = X match {
        case SNil => Y
        case head #: tail => head #: Concat[tail, Y]
    }

    type Reverse[X <: Shape] <: Shape = X match {
        case SNil => SNil
        case head #: tail => Concat[Reverse[tail], head #: SNil]
    }

    type IsEmpty[X <: Shape] <: Boolean = X match {
        case SNil => true
        case _ #: _ => false
    }

    type Head[X <: Shape] <: Dimension = X match {
        case head #: _ => head
    }

    type Tail[X <: Shape] <: Shape = X match {
        case _ #: tail => tail
    }

    type Remove[X <: Shape, Index <: Int] <: Shape = Index match {
        case 0 => X match {
            case _ #: tail => tail
        }
        case S[indexMinusOne] => X match {
            case head #: tail => head #: Remove[tail, indexMinusOne]
        }
    }

    /* This was a previous attempt at materializing type-level values. Has now been replaced by ShapeOf.
    
    inline def fromType[S <: Shape]: Shape = 
        if (constValue[IsEmpty[S]]) SNil 
        else constValue[Head[S]] #: fromType[Tail[S]]
    */

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
