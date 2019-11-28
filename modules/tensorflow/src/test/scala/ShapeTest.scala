import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class ShapeTest {
    val shape1 = 1 #: 2 #: 3 #: SNil
    val shape2 = 3 #: 2 #: 1 #: SNil

    val shape1NumEls = valueOf[Shape.NumElements[shape1.type]]
    val shape2NumEls = valueOf[Shape.NumElements[shape2.type]]

    def assertShapeEquals[Expected <: Shape, Actual <: Shape]
        (given expected: ShapeOf[Expected], actual: ShapeOf[Actual]): Unit = {
        assertEquals(expected.value, actual.value)
    }

    @Test def `(1 #: 2 #: 3 #: SNil) numElements == 6`(): Unit = {    
        val res = shape1.numElements
        assertEquals(6, res)
    }

    @Test def `(1 #: 2 #: 3 #: SNil) NumElements == 6`(): Unit = {
        assertEquals(6, shape1NumEls)
    }

    @Test def `(1 #: 2 #: 3 #: SNil) NumElements == (3 #: 2 #: 1 #: SNil) NumElements`(): Unit = {
        assertEquals(shape1NumEls, shape2NumEls)
    }

    @Test def `Remove[1 #: SNil, ^ :: SNil] == SNil`(): Unit = {   
        assertShapeEquals[SNil, Shape.Remove[1 #: SNil, ^ :: SNil]]
    }

    @Test def `Remove[1 #: 2 #: SNil, ^ :: SNil] == 2 #: SNil`(): Unit = {   
        assertShapeEquals[2 #: SNil, Shape.Remove[1 #: 2 #: SNil, ^ :: SNil]]
    }

    /*
    @Test def `Remove[1 #: 2 #: SNil, v :: ^ :: SNil] == 1 #: SNil`(): Unit = {
        val x: Shape.Remove[1 #: 2 #: SNil, v :: ^ :: SNil] = 1 #: SNil
        assertShapeEquals[1 #: SNil, Shape.Remove[1 #: 2 #: SNil, v :: ^ :: SNil]]
    }
    */
}
