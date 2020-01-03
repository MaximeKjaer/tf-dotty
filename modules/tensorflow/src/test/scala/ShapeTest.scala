import org.junit.Test
import org.junit.Assert._
import ch.epfl.tensorflow.api.core._

class ShapeTest {
    def assertShapeEquals[Expected <: Shape, Actual <: Shape]
        (given expected: ShapeOf[Expected], actual: ShapeOf[Actual]): Unit = {
        assertEquals(expected.value, actual.value)
    }

    @Test def `numElements empty`(): Unit = {    
        assertEquals(0, SNil.numElements)
    }

    @Test def `numElements 1`(): Unit = {    
        val shape = 1 #: 2 #: 3 #: SNil
        assertEquals(6, shape.numElements)
    }

    @Test def `numElements 2`(): Unit = {    
        val shape = 3 #: 2 #: 3 #: SNil
        assertEquals(18, shape.numElements)
    }

    @Test def `NumElements 1`(): Unit = {
        val shape = 1 #: 2 #: 3 #: SNil
        val res = valueOf[Shape.NumElements[shape.type]]
        assertEquals(6, res)
    }

    @Test def `NumElements 2`(): Unit = {
        val shape = 3 #: 2 #: 1 #: SNil
        val res = valueOf[Shape.NumElements[shape.type]]
        assertEquals(6, res)
    }

    @Test def `Reduce empty`(): Unit = {
        val res = shapeOf[Shape.Reduce[1 #: 2 #: SNil, SNil]]
        assertEquals(1 #: 2 #: SNil, res)
    }

    @Test def `Reduce 1`(): Unit = {
        val res = shapeOf[Shape.Reduce[1 #: SNil, 0 :: SNil]]
        assertEquals(SNil, res)
    }

    @Test def `Reduce 2`(): Unit = {
        val res = shapeOf[Shape.Reduce[1 #: 2 #: SNil, 0 :: SNil]]
        assertEquals(2 #: SNil, res)
    }

    @Test def `Head 1`(): Unit = {
        val res = valueOf[Shape.Head[1 #: 2 #: 3 #: SNil]]
        assertEquals(1, res)
    }

    @Test def `Head 2`(): Unit = {
        val res = valueOf[Shape.Head[1 #: SNil]]
        assertEquals(1, res)
    }

    @Test def `Tail 1`(): Unit = {
        val res = shapeOf[Shape.Tail[1 #: 2 #: 3 #: SNil]]
        assertEquals(2 #: 3 #: SNil, res)
    }

    @Test def `Tail 2`(): Unit = {
        val res = shapeOf[Shape.Tail[1 #: SNil]]
        assertEquals(SNil, res)
    }

    @Test def `Enumerate empty`(): Unit = {
        val res = indicesOf[Shape.Enumerate[SNil]]
        assertEquals(SNil, res)
    }

    @Test def `Enumerate 1`(): Unit = {
        val res = indicesOf[Shape.Enumerate[1 #: SNil]]
        assertEquals(0 :: SNil, res)
    }

    // TODO fix https://github.com/lampepfl/dotty/issues/7868
    @Test def `Enumerate 2`(): Unit = {
        // val res = indicesOf[Shape.Enumerate[1 #: 10 #: SNil]]
        // assertEquals(0 :: 1 :: SNil, res)
    }

    @Test def `RemoveIndex 0`(): Unit = {
        val res = shapeOf[Shape.RemoveIndex[0 #: 1 #: SNil, 0]]
        assertEquals(1 #: SNil, res)
    }

    @Test def `RemoveIndex 1`(): Unit = {
        val res = shapeOf[Shape.RemoveIndex[0 #: 1 #: SNil, 1]]
        assertEquals(0 #: SNil, res)
    }
}
