---
id: dotty
title: Dotty features
sidebar_label: Dotty features
---

tf-dotty uses some language features introduced in [Dotty](https://dotty.epfl.ch/).

## Existing Dotty types

### Union types

TensorFlow's API restricts some operations to a subset of available tensor element types. For instance, [`tf.math.minimum`](https://www.tensorflow.org/versions/r1.14/api_docs/python/tf/math/minimum) can be applied to tensors of integers and of floats, but not to tensors of strings or of booleans. Note that this is a fixed constraint of the C++ implementation of TensorFlow; it would be incorrect to allow the `minimum` function to be applied whenever an implicit `Ordering` can be found, as the Scala implementation of `Int.min` does: the C++ implementation does not function with user-defined Scala `Ordering`, but only supports a subset of the `Ordering`s defined by Scala's standard library.

Instead, with Dotty, the `minimum` function can impose an upper bound on tensor element types, consisting of the union type `Int | Long | Float | Double` to accurately encode the fixed type constraint. Avoiding the use of an implicit also means that compilation does not need to run an implicit search.

### Match types

Dotty's match types provide support for transforming a type into another; it's even possible to define recursive transformations with match types. tf-dotty makes extensive use of match types to manipulate tensor shapes and indices. For instance, the `tf.transpose` operation reverses the order of the dimensions; the resulting dimension shape is therefore the reverse of the input. This transformation can be written as the `Reverse` match type:

```scala
type Concat[X <: Shape, Y <: Shape] <: Shape = X match {
    case SNil => Y
    case head #: tail => head #: Concat[tail, Y]
}

type Reverse[X <: Shape] <: Shape = X match {
    case SNil => SNil
    case head #: tail => Concat[Reverse[tail], head #: SNil]
}
```

## Extensions to Dotty's type system

tf-dotty also uses a few language features that are not yet part of the official Dotty compiler.

### Singleton operations

Dotty has support for `scala.compiletime.S`, a type which represents the successor of a natural singleton integer type: for instance, the type `S[1]` reduces to the singleton type `2`. Implementing tf-dotty has given a motivation for more general arithmetic operations on singleton types, such as addition and multiplication. For instance, [typing the `reshape` operation](reshape.md) requires computing the product of the tensor's dimensions.

This feature is implemented in a fork of Dotty, and is suggested as an extension to the upstream version in the pull request [lampepfl/dotty#7628](https://github.com/lampepfl/dotty/pull/7628). The implementation defines a series of abstract types in Dotty's standard library, in the `scala.compiletime.ops` package. For example, `scala.compiletime.ops.int.*` provides support for multiplying two singleton `Int` types, and `scala.compiletime.ops.boolean.&&` for the conjunction of two `Boolean` types.

```scala
import scala.compiletime.ops.int._
import scala.compiletime.ops.boolean._

val conjunction: true && true = true
val multiplication: 3 * 5 = 15
```

Many of these singleton operation types are meant to be used infix (as in [SLS § 3.2.8](https://www.scala-lang.org/files/archive/spec/2.12/03-types.html#infix-types)), and are annotated with [`@infix`](scala.annotation.infix) accordingly. This annotation means that the notation `3 + 2` should be used over `+[3, 2]`. The current implementation of Dotty does not enforce this preference, but annotating the types explicitly can be useful for future Scala 3 tooling.

Since type aliases have the same precedence rules as their term-level equivalents, the operations compose with the expected precedence rules:

```scala
import scala.compiletime.ops.int._
val x: 1 + 2 * 3 = 7
```

The implementation of this feature chiefly consists of an extension to Dotty's type comparer. When two types are compared, and one of them is a singleton operation type from the `scala.compiletime.ops` package, the compiler will attempt to constant fold the operation type. If the arguments are singleton types, or can be reduced to singleton types by a constant fold, the compiler can reduce the operation. For instance, in the example above, the type `1 + 2 * 3` is parsed as `+[1, *[2, 3]]`, which can be reduced to `+[1, 6]` by reducing `*[2, 3]` to `6`; the addition can then then be reduced to `7`.

The singleton operations also need to be reduced when appearing in the scrutinee of a match type, or in the arguments to a type application. This allows for interoperation of singleton operation types with match types and type aliases, which allows for powerful type-level programming, such as the example below computing the greatest common denominator (GCD) at the type level:

```scala
import scala.compiletime.int.%

type GCD[A <: Int, B <: Int] <: Int = B match {
    case 0 => A
    case _ => GCD[B, A % B]
}

val gcd: GCD[252, 105] = 21
```

### `Error` singleton operation

With the ability to write complex type-level computations comes the need for better feedback to the user. Dotty's current implementation of match types leaves them unreduced if reduction is not possible. This exposes implementation details to the caller of a match type. Additionally, there may be cases where inputs to a match type are decidedly erroneous, and a custom compilation error would be desirable.

Dotty already has support for custom compilation error messages for implicit search with the [`@implicitNotFound`](https://www.scala-lang.org/api/2.13.1/scala/annotation/implicitNotFound.html) annotation, and for inlining with [`scala.compiletime.error`](https://dotty.epfl.ch/docs/reference/metaprogramming/inline.html#error).

To support custom errors in match types, Dotty could add a `scala.compiletime.ops.Error` type, which can be implemented as another compiletime singleton operation. This type takes a singleton string type, which defines the error message. Unlike other operations, it never returns a value when constant folded by the compiler, but instead throws a type error. A draft implementation of this is available on a [fork of Dotty](https://github.com/MaximeKjaer/dotty/commit/accdc430e6914087eb08a733a9642e9db7f7f225).

The main downside of this approach is that compiletime operations are only reduced when compared to another type. For types that can throw compiletime errors when reduced, it may be desirable to throw an error earlier, when they are applied. This is the case for a method with a return type that is a match type that may throw an error at compiletime; since the match type is only reduced when compared to another type, the compiletime error does not appear when type-checking the method application, but instead when the returned type is compared.

This feature therefore requires more work to be integrated into the language.
