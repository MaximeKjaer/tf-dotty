---
id: reshape
title: Reshaping with singleton ops
sidebar_label: Reshape
---

## Reshaping tensors

A common operation in TensorFlow is [`tf.reshape`](https://www.tensorflow.org/api_docs/python/tf/reshape?version=stable), which changes the shape, but not the values, of a tensor. A restriction imposed by the TensorFlow API is that the output shape must have the same number of elements as the input shape. The Python implementation of TensorFlow throws a `ValueError` when the output dimensions incompatible:

```python
>>> import tensorflow as tf
>>> tf.reshape(tf.zeros([10, 10]), [20, 20])
# ValueError: Cannot reshape a tensor with 100 elements to shape [20,20] (400 elements) for 'Reshape' (op: 'Reshape') with input shapes: [10,10], [2] and with input tensors computed as partial shapes: input[1] = [20,20].
```

Indeed, a tensor can only be reshaped to another shape containing the same number of elements. The number of elements of a shape is the product of dimension sizes; a tensor of shape `10 #: 20 #: 30 #: SNil` contains \\(10 \times 20 \times 30 = 6000\\) elements.

## Statically checking reshapes

Computing the number of elements in a tensor at compile-time requires support for type-level arithmetic, as Maclaurin et al. write in "[Dex: array programming with typed indices](https://openreview.net/pdf?id=rJxd7vsWPS)":

> What about the dreaded reshape, which would seem to require type-level arithmetic?

### First attempt: with match types and `S`

Dotty has support for `scala.compiletime.S`, a type which represents the successor of a natural singleton integer type. Additionally, match types provide support for transforming a type into another; it's even possible to define recursive transformations. These two features make it possible to define some arithmetic operations:

```scala
import scala.annotation.infix

@infix type +[A <: Int, B <: Int] <: Int = A match {
    case 0 => B
    case S[aMinusOne] => S[aMinusOne + B]
}

@infix type *[A <: Int, B <: Int] <: Int = A match {
    case 0 => 0
    case _ => MultiplyLoop[A, B, 0]
}

protected type MultiplyLoop[A <: Int, B <: Int, Acc <: Int] <: Int = A match {
    case 0 => Acc
    case S[aMinusOne] => MultiplyLoop[aMinusOne, B, B + Acc]
}
```

These match types are highly recursive; with the default setting of 512 MB of memory for the JVM, computing `100 * 100` results in a stack overflow on Dotty 0.21.0-RC1 [check this; give big-O of depth], which is too limiting for what actual machine learning workloads need to compute.

### Second attempt: with compiler support

To support O(1) computations, the Dotty compiler must be able to evaluate a type-level arithmetic operation as its term-level equivalent. We have implemented this as an extension to be [integrated to Dotty](https://github.com/lampepfl/dotty/pull/7628), currently awaiting approval. This implementation adds unbound type aliases to Dotty's standard library, in `scala.compiletime.ops`; when the type comparer considers one of those types, it can normalize it to the result of the arithmetic operation if all arguments are constant types. This functions analogously to a constant folding optimization, except at the type level.

With these types, we can compute the number of elements in a tensor's shape:

```scala
import scala.compiletime.ops.int.*

type NumElements[X <: Shape] <: Int = X match {
    case SNil => 0
    case head #: tail => head * NumElementsNonEmpty[tail]
}

protected type NumElementsNonEmpty[X <: Shape] <: Int = X match {
    case SNil => 1
    case head #: tail => head * NumElementsNonEmpty[tail]
}
```

A reshape can then ensure that the new shape has the same number of elements as the old shape:

```scala
def reshape[T, OldShape <: Shape, NewShape <: Shape](tensor: Tensor[T, OldShape], shape: NewShape)(
    given ev: Shape.NumElements[Old] =:= Shape.NumElements[New]
): Tensor[T, NewShape] = {
    new Tensor[T, NewShape](tf.reshape(tensor.tensor, shape.toSeq))
}
```

[todo benchmark comparisons]
