package io.kjaer.compiletime

class IndicesSuite extends munit.FunSuite {
  test("indices of INil") {
    assertEquals(INil.indices, Set.empty)
  }

  test("indices of 0 :: 2 :: INil") {
    assertEquals((0 :: 2 :: INil).indices, Set(0, 2))
  }

  test("indices of 0 :: 1 :: 2 :: INil") {
    assertEquals((0 :: 1 :: 2 :: INil).indices, Set(0, 1, 2))
  }

  test("Contains of INil is false") {
    val res = valueOf[Indices.Contains[INil, 1]]
    assert(!res)
  }

  test("Contains true example 1") {
    val res = valueOf[Indices.Contains[1 :: INil, 1]]
    assert(res)
  }

  test("Contains true example 2") {
    val res = valueOf[Indices.Contains[1 :: 2 :: 3 :: INil, 2]]
    assert(res)
  }

  test("Contains false example 1") {
    val res = valueOf[Indices.Contains[1 :: 2 :: 3 :: INil, 12]]
    assert(!res)
  }

  test("RemoveValue example 1") {
    val res = indicesOf[Indices.RemoveValue[1 :: 10 :: INil, 1]]
    assertEquals(10 :: INil, res)
  }

  test("RemoveValue example 2") {
    val res = indicesOf[Indices.RemoveValue[1 :: 10 :: INil, 10]]
    assertEquals(1 :: INil, res)
  }

  test("RemoveValue of the only value is INil") {
    val res = indicesOf[Indices.RemoveValue[1 :: INil, 1]]
    assert(INil == res)
  }

  test("RemoveValue in INil is INil") {
    val res = indicesOf[Indices.RemoveValue[INil, 1]]
    assert(INil == res)
  }

  test("RemoveValue removed value is no longer contained") {
    val res = indicesOf[Indices.RemoveValue[1 :: 10 :: INil, 20]]
    assertEquals(1 :: 10 :: INil, res)
  }
}
