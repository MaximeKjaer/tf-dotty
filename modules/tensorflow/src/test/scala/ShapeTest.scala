import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class ShapeTest {
    val shape1 = 1 #: 2 #: 3 #: HNil
    val shape2 = 3 #: 2 #: 1 #: HNil

    val shape1NumEls = valueOf[Shape.NumElements[shape1.type]]
    val shape2NumEls = valueOf[Shape.NumElements[shape2.type]]

    def assertShapeEquals[Expected <: Shape, Actual <: Shape]
        (given expected: ShapeOf[Expected], actual: ShapeOf[Actual]): Unit = {
        assertEquals(expected.value, actual.value)
    }

    @Test def `(1 #: 2 #: 3 #: HNil) numElements == 6`(): Unit = {    
        val res = shape1.numElements
        assertEquals(6, res)
    }

    @Test def `(1 #: 2 #: 3 #: HNil) NumElements == 6`(): Unit = {
        assertEquals(6, shape1NumEls)
    }

    @Test def `(1 #: 2 #: 3 #: HNil) NumElements == (3 #: 2 #: 1 #: HNil) NumElements`(): Unit = {
        assertEquals(shape1NumEls, shape2NumEls)
    }

    @Test def `Remove[1 #: HNil, 0] == HNil`(): Unit = {   
        assertShapeEquals[HNil, Shape.Remove[1 #: HNil, 0]]
    }

    @Test def `Remove[1 #: 2 #: HNil, 0] == 2 #: HNil`(): Unit = {   
        assertShapeEquals[2 #: HNil, Shape.Remove[1 #: 2 #: HNil, 0]]
    }

    @Test def `Remove[1 #: 2 #: HNil, 1] == 1 #: HNil`(): Unit = {   
        assertShapeEquals[1 #: HNil, Shape.Remove[1 #: 2 #: HNil, 1]]
    }
}
