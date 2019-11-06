package ch.epfl.tensorflow.api.core

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
