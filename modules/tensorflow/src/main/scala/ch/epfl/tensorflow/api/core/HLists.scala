package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.py
import scala.compiletime.S
import scala.compiletime.ops.int.*

sealed trait SNil extends Shape with Indices
case object SNil extends SNil


///////////
// Shape //
///////////

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
    def fromSeq(seq: Seq[Int]): Shape = seq match {
        case Nil => SNil
        case head +: tail => head #: Shape.fromSeq(tail)
    }

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
        case head #: tail => head * NumElementsNonEmpty[tail]
    }

    type NumElementsNonEmpty[X <: Shape] <: Int = X match {
        case SNil => 1
        case head #: tail => head * NumElementsNonEmpty[tail]
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

    /** Generate a list of indices */
    type Enumerate[X <: Shape] = IndicesLoop[X, 0]
    protected type IndicesLoop[X <: Shape, Current <: Index] <: Indices = X match {
        case head #: tail => Current :: IndicesLoop[tail, S[Current]]
        case SNil => SNil
    }

    /**
      * Represents reduction along axes, as defined in TensorFlow:
      * 
      *   - None means reduce along all axes
      *   - List of indices contain which indices in the shape to remove
      *   - Empty list of indices means reduce along nothing
      * 
      * @tparam S           Shape to reduce
      * @tparam Axes        List of indices to reduce along. `py.None.type` if no reduction should be done.
      */
    type Reduce[S <: Shape, Axes <: Indices | py.None.type] <: Shape = Axes match {
        case py.None.type => SNil
        case Indices => RemoveAll[S, Enumerate[S], Axes]
    }

    /**
     * Remove indices from a shape
     * 
     * @tparam RemoveFrom   Shape to remove from 
     * @tparam Enumeration  Enumerated indices of the shape
     * @tparam ToRemove     Indices to remove from X
     */
    protected type RemoveAll[RemoveFrom <: Shape, Enumeration <: Indices, ToRemove <: Indices] <: Shape = RemoveFrom match {
        case SNil => SNil
        case head #: tail => Enumeration match {
            case SNil => head #: tail
            case headIndex :: tailIndices => Indices.Contains[ToRemove, headIndex] match {
                case true => RemoveAll[tail, tailIndices, Indices.RemoveValue[ToRemove, headIndex]]
                case false => head #: RemoveAll[tail, tailIndices, ToRemove]
            }
        }
    }

    type RemoveIndex[RemoveFrom <: Shape, I <: Index] = RemoveIndexLoop[RemoveFrom, I, 0]
    
    protected type RemoveIndexLoop[RemoveFrom <: Shape, I <: Index, Current <: Index] <: Shape = RemoveFrom match {
        // todo compile-time error
        // case SNil => Error["out of bounds"]
        case head #: tail => Current match {
            case I => tail
            case _ => head #: RemoveIndexLoop[tail, I, S[Current]]
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

final case class #:[+H <: Dimension, +T <: Shape](head: H, tail: T) extends Shape {
    override def toString = head match {
        case _ #: _ => s"($head) #: $tail"
        case _      => s"$head #: $tail"
    }
}

trait ShapeOf[T <: Shape] {
    def value: T
}

object ShapeOf {
    given ShapeOf[SNil.type] {
        def value = SNil
    }

    given ShapeOf[SNil] {
        def value = SNil
    }

    given [H <: Dimension, T <: Shape](given head: ValueOf[H], tail: ShapeOf[T]): ShapeOf[H #: T] {
        def value = head.value #: tail.value
    }
}

inline def shapeOf[S <: Shape](given s: ShapeOf[S]): S = s.value


////////////
// Select //
////////////

type Index = Int & Singleton

sealed trait Indices {
    def ::[H <: Index, This >: this.type <: Indices](head: H): H :: This = 
        ch.epfl.tensorflow.api.core.::(head, this)

    def indices: Set[Int] = this match {
        case head :: tail => tail.indices + head
        case SNil => Set.empty
    }
}

final case class ::[H <: Index, T <: Indices](head: H, tail: T) extends Indices {
    override def toString = s"$head :: $tail"
}

object Indices {
    type Contains[Haystack <: Indices, Needle <: Index] <: Boolean = Haystack match {
        case head :: tail => head match {
            case Needle => true
            case _ => Contains[tail, Needle]
        }
        case SNil => false
    }

    type RemoveValue[RemoveFrom <: Indices, Value <: Index] <: Indices = RemoveFrom match {
        case SNil => SNil
        case head :: tail => head match {
            case Value => RemoveValue[tail, Value]
            case _ => head :: RemoveValue[tail, Value]
        }
    }
}

final class IndicesOf[T <: Indices](val value: T)

object IndicesOf {
    given indicesOfSNilType: IndicesOf[SNil.type] = IndicesOf(SNil)
    given indicesOfSNil: IndicesOf[SNil] = IndicesOf(SNil)
    given indicesOfCons[H <: Index, T <: Indices](given head: ValueOf[H], tail: IndicesOf[T]): IndicesOf[H :: T] =
        IndicesOf(head.value :: tail.value)
}

inline def indicesOf[I <: Indices](given i: IndicesOf[I]): I = i.value
