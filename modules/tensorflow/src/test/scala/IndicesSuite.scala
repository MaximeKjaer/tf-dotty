import io.kjaer.tensorflow.core._

class IndicesSuite extends munit.FunSuite {
    test("indices 1") {
        assertEquals((0 :: 2 :: SNil).indices, Set(0, 2))
    }

    test("indices 2") {
        assertEquals((0 :: 1 :: 2 :: SNil).indices, Set(0, 1, 2))
    }

    test("indices empty for SNil") {
        assertEquals(SNil.indices, Set.empty)
    }

    test("Contains false for SNil") {
        val res = valueOf[Indices.Contains[SNil, 1]]
        assert(!res)
    }

    test("Contains true 1") {
        val res = valueOf[Indices.Contains[1 :: SNil, 1]]
        assert(res)
    }
    
    test("Contains true 2") {
        val res = valueOf[Indices.Contains[1 :: 2 :: 3 :: SNil, 2]]
        assert(res)
    }

    test("Contains false") {
        val res = valueOf[Indices.Contains[1 :: 2 :: 3 :: SNil, 12]]
        assert(!res)
    }

    test("RemoveValue 1") {
        val res = indicesOf[Indices.RemoveValue[1 :: 10 :: SNil, 1]]
        assertEquals(10 :: SNil, res)
    }

    test("RemoveValue 2") {
        val res = indicesOf[Indices.RemoveValue[1 :: 10 :: SNil, 10]]
        assertEquals(1 :: SNil, res)
    }

    test("RemoveValue only value") {
        val res = indicesOf[Indices.RemoveValue[1 :: SNil, 1]]
        assert(SNil == res)
    }

    test("RemoveValue SNil") {
        val res = indicesOf[Indices.RemoveValue[SNil, 1]]
        assert(SNil == res)
    }

    test("RemoveValue not contained") {
        val res = indicesOf[Indices.RemoveValue[1 :: 10 :: SNil, 20]]
        assertEquals(1 :: 10 :: SNil, res)
    }
}
