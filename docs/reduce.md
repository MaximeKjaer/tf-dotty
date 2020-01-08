---
id: reduce
title: Reducing with match types
sidebar_label: Reduce
---

## Reducing a tensor

Tensorflow has a series of reduction operations, like `tf.cumprod`, `reduce_mean` or `reduce_variance`. These operations take:

- `tensor`: a tensor to reduce over.
- `axis`: indices of the axes to reduce along, or `py.None` to reduce along all axes.
- `keepdims`: if `true`, reduced dimensions are retained with size `1`; if `false`, they are removed.

The example code below illustrates the use of these parameters in tf-dotty.

```scala
val tensor = TensorFlow.zeros(10 #: 20 #: 30 #: SNil) //: Tensor[Float, 10 #: 20 #: 30 #: SNil]
TensorFlow.reduce_mean(tensor, axis = py.None) //: Tensor[Float, SNil]
TensorFlow.reduce_mean(tensor, axis = 0 :: SNil) //: Tensor[Float, 20 #: 30 #: SNil]
TensorFlow.reduce_mean(tensor, axis = 1 :: 2 :: SNil) //: Tensor[Float, 10 #: SNil]
TensorFlow.reduce_mean(tensor, axis = 0 :: 2 :: SNil, keepdims = true) //: Tensor[Float, 1 #: 20 #: 1 #: SNil]
```

The `axis` parameter takes a list of indices of axes, of type `Indices`. This list is built with the `::` Cons type, which is different from the `#:` Cons type used for `Shape`. Both are lists of singleton integer types, but have different semantic meaning.

In the above examples, the indices are ordered, unique and within bounds. However, the Python TensorFlow API supports unordered and repeated indices, and it raises an error for indices out of bounds.

## Statically checking reductions

### First attempt: `Selection`

To avoid the problem of unordered, repeated indices, we can deviate from the TensorFlow Python API, and create a new type to represent index sets. This type is `Selection`, and is an HList that can only contain one of two singleton object types, `^` and `v`: the former includes an index in the reduction, and the latter excludes it. For instance, the code below reduces over dimensions 0 and 2:

```scala
TensorFlow.reduce_mean(
    input_tensor = TensorFlow.zeros(1 #: 2 #: 3 #: SNil),
    axis         =                  ^ :: v :: ^ :: SNil,
    keepdims     = false
) //: Tensor[Float, 2 #: SNil]
```

The advantage of this `Selection` type is that it is always ordered, and cannot contain duplicates, by construction. If the `Selection` is longer than the `Shape`, the remaining selection indicators can be ignored, or an error could be raised.

However, there are also disadvantages to this type:

- It is very verbose for high-dimensional tensors
- It strays from the TensorFlow Python API, which makes portability of Python programs to tf-dotty more difficult
- In code using TensorFlow, the call to the reduction is not always implemented adjacent to the definition of the shape, which makes the selection harder to read

### Second attempt: Nested type loop

It is possible to compute reduction along a list of given indices by implementing logic similar to two nested loops:

- The outer loop iterates over the shape and counts the current index;
- The inner loop iterates over the indices to remove, returns whether the index is in the list, and removes all occurrences of the index.

This implementation is \\( \mathcal{O}(m \cdot n) \\), where \\( m \\) is the size of the index list, and \\( n \\) is the rank of the tensor. In practice, the rank of tensors is often small enough that compilation time suffers a negligible slowdown because of this inefficient implementation [todo: big "citation needed" here, based on benchmarks].

When the outer loop reaches the end of the `Shape`, any indices remaining in the index list are out of bounds, and can be reported as such through a compiletime error.

### Caveat: keepdims

Some TensorFlow operations, such as `tf.reduce_mean` and `tf.count_nonzero` provide an additional, optional argument called `keepdims`, defaulting to `false`. If set to `true`, the reduced dimensions are kept with size `1` instead of being removed. To implement this, the `Reduce` match type can take an additional `KeepDims` type parameter. When `KeepDims` is `true`, the match type replaces the selected `Shape` elements with `1` instead of removing them.

However, a bug in Dotty's type inference for default parameters currently prevents this from being accurately typed, and tf-dotty therefore does not support the `keepdims` parameter. This bug is tracked in [todo: submit issue].
