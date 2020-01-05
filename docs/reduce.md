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

\\( \mathcal{O}(m \cdot n) \\), where \\( m \\) is the size of the index list, and \\( n \\) is the rank of the tensor.

### Caveat: keepdims
