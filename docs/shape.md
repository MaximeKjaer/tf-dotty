---
id: shape
title: Tensor shape
---

The central type of tf-dotty is a `Tensor`, which takes two type parameters:

- `T` for the type of the values in the tensor,
- `S` for the shape of the tensor. This type is a subtype of `Shape`

`Shape` type is similar to an `HList` in the [shapeless](https://github.com/milessabin/shapeless) library, or to a `Tuple` in Dotty. Like `HList` and `Tuple`, it represents a list of types of any length. Unlike `HList` and `Tuple`, it can impose an upper bound on the type of the elements. The upper bound for `Shape` is a singleton integer type, `Int & Singleton`.

Analogously to a `List`, a `Shape` type is constructed by using Cons and Nil types, which are `#:` and `SNil` in tf-dotty. Applications of Cons (`#:`) return the precise type of the Cons when dimension sizes are written as integer literals:

```scala
val shape1 /* inferred type: 32 #: 64 #: SNil */ = 32 #: 64 #: SNil
```

`Shape`s can also programmatically be generated from other shapes, while retaining type information. This is implemented as a match type to transform the type, and a function that casts to the match type.

```scala
val shape2: /* inferred type: 64 #: 32 #: SNil */ = shape1.reverse
```

Note that a shape of `SNil` corresponds to a single value.
