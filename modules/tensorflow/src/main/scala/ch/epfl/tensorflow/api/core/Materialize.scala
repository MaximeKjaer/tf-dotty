package ch.epfl.tensorflow.api.core

trait Materialize[T] {
    def value: T
}

object Materialize {
    given Materialize[HNil.type] {
        def value = HNil
    }

    given Materialize[HNil] {
        def value = HNil
    }

    given shapeMaterialize[H <: Dimension, T <: Shape](given head: ValueOf[H], tail: Materialize[T]): Materialize[H #: T] {
        def value = head.value #: tail.value
    }

    given labelsMaterialize[H <: Label, T <: Labels](given head: ValueOf[H], tail: Materialize[T]): Materialize[H @: T] {
        def value = head.value @: tail.value
    }

    given axesMaterialize[H <: Axis, T <: Axes](given head: ValueOf[H], tail: Materialize[T]): Materialize[H *: T] {
        def value = head.value *: tail.value
    }
}

inline def shapeOf[S <: Shape](given s: Materialize[S]): S = s.value
inline def labelsOf[L <: Labels](given l: Materialize[L]): L = l.value
inline def axesOf[A <: Axes](given a: Materialize[A]): A = a.value
