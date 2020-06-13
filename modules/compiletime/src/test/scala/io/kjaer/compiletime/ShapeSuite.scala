package io.kjaer.compiletime

class ShapeSuite extends munit.FunSuite {
  test("numElements of SNil is 1") {
    assert(1 == SNil.numElements)
  }

  test("numElements of example shape 1") {
    val shape = 1 #: 2 #: 3 #: SNil
    assert(6 == shape.numElements)
  }

  test("numElements of example shape 2") {
    val shape = 3 #: 2 #: 3 #: SNil
    assert(18 == shape.numElements)
  }

  test("NumElements of SNil is 1.type") {
    val shape = SNil
    val res = valueOf[Shape.NumElements[shape.type]]
    assertEquals(1, res)
  }

  test("NumElements of example shape 1") {
    val shape = 1 #: 2 #: 3 #: SNil
    val res = valueOf[Shape.NumElements[shape.type]]
    assertEquals(6, res)
  }

  test("NumElements of example shape 2") {
    val shape = 3 #: 2 #: 1 #: SNil
    val res = valueOf[Shape.NumElements[shape.type]]
    assertEquals(6, res)
  }

  test("Reduce along empty index list") {
    val res = shapeOf[Shape.Reduce[1 #: 2 #: SNil, INil]]
    assert(1 #: 2 #: SNil == res)
  }

  test("Reduce along only index") {
    val res = shapeOf[Shape.Reduce[1 #: SNil, 0 :: INil]]
    assert(SNil == res)
  }

  test("Reduce along some indices") {
    val res = shapeOf[Shape.Reduce[1 #: 2 #: SNil, 0 :: INil]]
    assert(2 #: SNil == res)
  }

  test("Head of  1") {
    val res = valueOf[Shape.Head[1 #: 2 #: 3 #: SNil]]
    assertEquals(1, res)
  }

  test("Head 2") {
    val res = valueOf[Shape.Head[1 #: SNil]]
    assertEquals(1, res)
  }

  test("Tail of example Shape 1") {
    val res = shapeOf[Shape.Tail[1 #: 2 #: 3 #: SNil]]
    assert(2 #: 3 #: SNil == res)
  }

  test("Tail of example Shape 2") {
    val res = shapeOf[Shape.Tail[1 #: SNil]]
    assert(SNil == res)
  }

  test("Map all to 1") {
    val res = shapeOf[Shape.Map[1 #: 2 #: 3 #: SNil, [_] =>> 1]]
    assert(1 #: 1 #: 1 #: SNil == res)
  }

  test("RemoveIndex 0") {
    val res = shapeOf[Shape.RemoveIndex[0 #: 1 #: SNil, 0]]
    assert(1 #: SNil == res)
  }

  test("RemoveIndex 1") {
    val res = shapeOf[Shape.RemoveIndex[0 #: 1 #: SNil, 1]]
    assert(0 #: SNil == res)
  }
}

