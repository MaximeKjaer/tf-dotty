import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class ShapeTest {
    val shape1 = 1 #: 2 #: 3 #: SNil
    val shape2 = 3 #: 2 #: 1 #: SNil

    val shape1NumEls = valueOf[Shape.NumElements[shape1.type]]
    val shape2NumEls = valueOf[Shape.NumElements[shape2.type]]

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
}
