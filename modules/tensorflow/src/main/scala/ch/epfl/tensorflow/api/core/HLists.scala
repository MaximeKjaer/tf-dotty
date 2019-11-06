package ch.epfl.tensorflow.api.core

import scala.compiletime.{S, constValue}

trait HList[+T]

type Label = String & Singleton
type Dimension = Int & Singleton
type Axis = (Label, Dimension)

sealed trait HNil extends Shape with Labels with Axes
case object HNil extends HNil

///////////
// Shape //
///////////

sealed trait Shape extends HList[Dimension] {
    import Shape._

    /** Prepend the head to this */
    def #:[H <: Dimension, This >: this.type <: Shape](head: H): H #: This = 
        ch.epfl.tensorflow.api.core.#:(head, this)

    /** Concat with another shape **/
    def ++(that: Shape): this.type Concat that.type = {
        val res: Shape = this match {
            case HNil => that
            case x #: xs => x #: (xs ++ that)
        }
        res.asInstanceOf[this.type Concat that.type]
    }

    def reverse: Reverse[this.type] = {
        val res: Shape = this match {
            case HNil => HNil
            case x #: xs => xs.reverse ++ (x #: HNil)
        }
        res.asInstanceOf[Reverse[this.type]]
    }

    def rank: Size[this.type] = {
        val res: Int = this match {
            case HNil => 0
            case head #: tail => 1 + tail.rank
        }
        res.asInstanceOf[Size[this.type]]
    }

    def numElements: NumElements[this.type] = {
        val res: Int = this match {
            case HNil => 0
            case head #: tail => head * Math.max(1, tail.numElements)
        }
        res.asInstanceOf[NumElements[this.type]]
    }

    def toSeq: Seq[Int] = this match {
        case HNil => Nil
        case head #: tail => head +: tail.toSeq
    }
}

object Shape {
    import TypeUtils.*

    type Map[X <: Shape, F[_ <: Dimension] <: Dimension] <: Shape = X match {
        case HNil => HNil
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
        case HNil => Z
        case head #: tail => FoldLeft[B, tail, F[Z, head], F]
    }

    type Size[X <: Shape] <: Int = X match {
        case HNil => 0
        case head #: tail => S[Size[tail]]
    }

    type NumElements[X <: Shape] <: Int = X match {
        case HNil => 0
        case head #: tail => FoldLeft[Int, X, 1, *]
    }

    type Concat[X <: Shape, Y <: Shape] <: Shape = X match {
        case HNil => Y
        case head #: tail => head #: Concat[tail, Y]
    }

    type Reverse[X <: Shape] <: Shape = X match {
        case HNil => HNil
        case head #: tail => Concat[Reverse[tail], head #: HNil]
    }

    type IsEmpty[X <: Shape] <: Boolean = X match {
        case HNil => true
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
        if (constValue[IsEmpty[S]]) HNil 
        else constValue[Head[S]] #: fromType[Tail[S]]
    */

    def scalar: HNil = HNil
    def vector(length: Dimension): length.type #: HNil = length #: HNil
    def matrix(rows: Dimension, columns: Dimension): rows.type #: columns.type #: HNil = rows #: columns #: HNil
}

final case class #:[H <: Dimension, T <: Shape](head: H, tail: T) extends Shape {
    override def toString = tail match {
        case _ #: _ => s"($head) #: $tail"
        case _      => s"$head #: $tail"
    }
}

////////////
// Labels //
////////////

sealed trait Labels extends HList[Label] {
    def @:[H <: Label, This >: this.type <: Labels](head: H): H @: This = 
        ch.epfl.tensorflow.api.core.@:(head, this)
}

object Labels {
    type IndexOf[L <: Label, LS <: Labels] <: Int = LS match {
        case HNil => Nothing
        case L @: tail => 0
        case _ @: tail => S[IndexOf[L, tail]]
    }
}

final case class @:[H <: Label, T <: Labels](head: H, tail: T) extends Labels {
    override def toString = tail match {
        case _ @: _ => s"($head) @: $tail"
        case _      => s"$head @: $tail"
    }
}

//////////
// Axes //
//////////

// For type inference:
def ax[L <: Label, D <: Dimension](l: L, d: D): (L, D) = (l, d)

sealed trait Axes extends HList[Axis] {
    def *:[H <: Axis, This >: this.type <: Axes](head: H): *:[H, This] =
        ch.epfl.tensorflow.api.core.*:(head, this)
    
    def shape: Axes.ShapeOf[this.type] = {
        val res: Shape = this match {
            case HNil => HNil
            case (_, s) *: tail => s #: tail.shape
        }
        res.asInstanceOf[Axes.ShapeOf[this.type]]
    }

    def labels: Axes.LabelsOf[this.type] = {
        val res: Labels = this match {
            case HNil => HNil
            case (l, _) *: tail => l @: tail.labels
        }
        res.asInstanceOf[Axes.LabelsOf[this.type]]
    }
}

final case class *:[H <: Axis, T <: Axes](head: H, tail: T) extends Axes {
    override def toString = s"$head *: $tail"
}

object Axes {
    type ShapeOf[As <: Axes] <: Shape = As match {
        case HNil => HNil
        case (_, s) *: tail => s #: ShapeOf[tail]
    }

    type LabelsOf[As <: Axes] <: Labels = As match {
        case HNil => HNil
        case (l, _) *: tail => l @: LabelsOf[tail]
    }

    type Remove[L <: Label, As <: Axes] <: Axes = As match {
        case HNil => HNil
        case (l, d) *: tail => l match {
            case L => tail
            case _ => (l, d) *: Remove[L, tail]
        }
    }

    type RemoveAll[Ls <: Labels, As <: Axes] <: Axes = Ls match {
        case HNil => As
        case head @: tail => RemoveAll[tail, Remove[head, As]]
    }

    type IndexOf[L <: Label, As <: Axes] <: Int = As match {
        case HNil => Nothing
        case (L, _) *: _ => 0
        case _ *: tail => S[IndexOf[L, tail]]
    }
}
