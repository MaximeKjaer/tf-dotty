---
id: reshape
title: Reshaping with singleton ops
sidebar_label: Reshape
---

## Reshaping tensors

A common operation in TensorFlow is [`tf.reshape`](https://www.tensorflow.org/versions/r1.15/api_docs/python/tf/reshape), which changes the shape of a tensor, but does not change its. A restriction imposed by the TensorFlow API is that the output shape must have the same number of elements as the input shape. The Python implementation of TensorFlow throws a `ValueError` when the shapes are incompatible:

```python
>>> import tensorflow as tf
>>> tf.reshape(tf.zeros([10, 10]), [20, 20])
# ValueError: Cannot reshape a tensor with 100 elements to shape [20,20] (400 elements) for 'Reshape' (op: 'Reshape') with input shapes: [10,10], [2] and with input tensors computed as partial shapes: input[1] = [20,20].
```

Indeed, a tensor can only be reshaped to another shape containing the same number of elements. The number of elements of a shape is the product of dimension sizes; a tensor of shape `10 #: 20 #: 30 #: SNil` contains \\(10 \times 20 \times 30 = 6000\\) elements, and can only be reshaped into another shape containing 6000 elements.

## Statically checking reshapes

How can we check this constraint statically? As Maclaurin et al. ask in "[Dex: array programming with typed indices](https://openreview.net/pdf?id=rJxd7vsWPS)":

> What about the dreaded reshape, which would seem to require type-level arithmetic?

### First attempt: with match types and `S`

With match type and `scala.compiletime.S`, it is possible to define some arithmetic operations in Dotty's type system:

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

These match types are highly recursive; with the default setting of 512 MB of memory for the JVM, computing `100 * 100` results in a stack overflow on Dotty 0.21.0-RC1, which is too limiting for what typical machine learning workloads need to compute.

### Second attempt: with compiler support

To support constant time multiplication, the Dotty compiler must be able to evaluate a type-level arithmetic operation as its term-level equivalent. This feature is [now available in Dotty](https://github.com/lampepfl/dotty/pull/7628). With these operation types, we can compute the number of elements in a tensor's shape:

```scala
import scala.compiletime.ops.int.*

type NumElements[X <: Shape] <: Int = X match {
    case SNil => 1
    case head #: tail => head * NumElements[tail]
}
```

A reshape operation can then ensure that the new shape has the same number of elements as the old shape, by demanding an implicit parameter proving that the types representing the number of elements are equal:

```scala
def reshape[T, Old <: Shape, New <: Shape](tensor: Tensor[T, Old], shape: New)
  (given NumElements[Old] =:= NumElements[New]): Tensor[T, New] =
    new Tensor[T, New](tf.reshape(tensor.tensor, shape.toSeq))
```
