package io.kjaer.tensorflow.math 

import io.kjaer.compiletime._
import io.kjaer.tensorflow.core._
import io.kjaer.tensorflow.dtypes.DataType
import Utils.encodeAxis
import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.TF.tf

trait Math {
    type Group1 = Float | Double | Int | Long // also: float16, complex64, complex128
    type Trig = Float | Double // also: bfloat16, half, complex64, complex128.
    type InverseTrig = Float | Double | Int | Long // also: bfloat16, half, complex64, complex128.
    type InverseHyperbolicTrig = Float | Double // also: bfloat16, half, complex64, complex128.
    type AddTypes = Float | Double | Int | Long | String // also: bfloat16, half, uint8, int8, int16, complex64, complex128
    type InferrableAsComplex = Float | Double // also: complex64, complex128
    type Comparable = Float | Double | Int | Long // also: uint8, int16, int8, complex64, qint8, quint8, qint32, bfloat16, uint16, complex128, half, uint32, uint64.
    type IndicesConvertibleTo = Int | Long
    type Group2 = Float | Double // also: bfloat16, half
    type Real = Float | Double
    type Roundable = Float | Double // also: half, bfloat16
    type Numeric = Int | Long | Float | Double // also: uint8, uint16, uint32, uint64, int8, int16, float16, complex64, complex128, bfloat16
    type Cumulable = Int | Long | Float | Double // also: uint8, uint16, int16, int8, complex64, complex128, qint8, quint8, qint32, half

    /**
      * Computes the absolute value of a tensor.

      * Given a tensor of integer or floating-point values, this operation returns a tensor of the same type,
      * where each element contains the absolute value of the corresponding element in the input.
      *
      * @param x A Tensor or SparseTensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def abs[T <: Group1, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.abs(x.tensor))

    /**
      * Returns the element-wise sum of a list of tensors.
      * 
      * `accumulate_n` performs the same operation as `tf.math.add_n`, but does not wait for all of its inputs to
      * be ready before beginning to sum. This approach can save memory if inputs are ready at different times,
      * since minimum temporary storage is proportional to the output size rather than the inputs' size.
      *
      * `accumulate_n` is differentiable (but wasn't previous to TensorFlow 1.7).
      * 
      * @param inputs A list of `Tensor` objects, each with same shape and type.
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def accumulate_n[T, S <: Shape](inputs: Seq[Tensor[T, S]]): Tensor[T, S] = 
        new Tensor[T, S](tf.accumulate_n(inputs.map(_.tensor)))

    /**
      * Computes acos of `x` element-wise.
      * @param x A Tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def acos[T <: InverseTrig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sin(x.tensor))
    
    /**
      * Computes inverse hyperbolic cosine of x element-wise.
      * @param x A Tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def acosh[T <: InverseHyperbolicTrig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sin(x.tensor))

    // TODO Support `add`, which supports broadcasting. `add_n` does not.

    /**
      * Adds all input tensors element-wise.
      * 
      * Converts IndexedSlices objects into dense tensors prior to adding.
      * 
      * `tf.math.add_n` performs the same operation as  tf.math.accumulate_n`, but it waits for all of its inputs to 
      * be ready before beginning to sum. This buffering can result in higher memory consumption when inputs are ready
      * at different times, since the minimum temporary storage required is proportional to the input size rather than
      * the output size.
      *
      * This op does not broadcast its inputs. If you need broadcasting, use `tf.math.add` (or the `+` operator) instead.
      * 
      * @param inputs A list of `Tensor` objects, each with same shape and type.
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def add_n[T, S <: Shape](inputs: Seq[Tensor[T, S]]): Tensor[T, S] = 
        new Tensor[T, S](tf.add_n(inputs.map(_.tensor)))

    /**
      * Returns the element-wise argument of a complex (or real) tensor.
      * 
      * Given a tensor input, this operation returns a tensor of type float that is the argument of each element
      * in `input` considered as a complex number.
      * 
      * The elements in input are considered to be complex numbers of the form a + bj, where a is the real part
      * and b is the imaginary part. If `input` is real then b is zero by definition.
      *
      * The argument returned by this function is of the form atan2(b, a). 
      * If `input` is real, a tensor of all zeros is returned.
      *
      * @param input A tensor
      * @tparam T Type of input elements
      * @tparam S Shape of input and output tensor
      */
    def angle[T <: InferrableAsComplex, S <: Shape](input: Tensor[T, S]): Tensor[Float, S] =
        new Tensor[Float, S](tf.angle(input.tensor))

    /** 
      * Returns the index with the largest value across axes of a tensor.
      * 
      * Note that in case of ties the identity of the return value is not guaranteed.
      *
      * @param input A Tensor
      * @param axis Describes which axis of the input Tensor to reduce across. For vectors, use axis = 0.
      * @tparam T Type of input elements
      * @tparam S Shape of input and output tensor
      */
    def argmax[T <: Comparable, S <: Shape, Axis <: Index](
        input: Tensor[T, S],
        axis: Axis = 0
    ): Tensor[Long, Shape.RemoveIndex[S, Axis]] =
        new Tensor[Long, Shape.RemoveIndex[S, Axis]](tf.argmax(input.tensor, axis))

    /** 
      * Returns the index with the smallest value across axes of a tensor.
      * 
      * Note that in case of ties the identity of the return value is not guaranteed.
      *
      * @param input A Tensor
      * @param axis Describes which axis of the input Tensor to reduce across. For vectors, use axis = 0.
      * @tparam T Type of input elements
      * @tparam S Shape of input and output tensor
      */
    def argmin[T <: Comparable, S <: Shape, Axis <: Index](
        input: Tensor[T, S],
        axis: Axis = 0
    ): Tensor[Long, Shape.RemoveIndex[S, Axis]] =
        new Tensor[Long, Shape.RemoveIndex[S, Axis]](tf.argmin(input.tensor, axis))

    /**
      * Computes the trignometric inverse sine of `x` element-wise.
      * 
      * The `tf.math.asin` operation returns the inverse of `tf.math.sin`, such that if
      * y = tf.math.sin(x) then, x = tf.math.asin(y).
      * 
      * @param x A Tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def asin[T <: InverseTrig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.asin(x.tensor))

    /**
      * Computes inverse hyperbolic sine of `x` element-wise.
      * 
      * @param x A Tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def asinh[T <: InverseHyperbolicTrig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.asinh(x.tensor))

    /**
      * Computes the trignometric inverse tangent of `x` element-wise.
      * 
      * The `tf.math.atan` operation returns the inverse of `tf.math.tan`, such that if
      * y = tf.math.tan(x) then, x = tf.math.atan(y).
      * 
      * @param x A Tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def atan[T <: InverseHyperbolicTrig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atan(x.tensor))

    /**
      * Computes arctangent of y/x element-wise, respecting signs of the arguments.
      * 
      * @param x A Tensor
      * @param y A Tensor of the same type and shape as `x`
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensors
      */
    def atan2[T <: Group2, S <: Shape](x: Tensor[T, S], y: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atan2(x.tensor, y.tensor))

    /**
      * Computes inverse hyperbolic tangent of x element-wise.
      * 
      * @param x A Tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def atanh[T <: InverseHyperbolicTrig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atanh(x.tensor))

    // TODO bessel functions
    
    /**
      * Compute the regularized incomplete beta integral I_x(a, b)
      *
      * 
      * @param a A tensor
      * @param b A tensor of the same type and shape as a
      * @param x A tensor of the same type and shape as a
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def betainc[T <: Real, S <: Shape](a: Tensor[T, S], b: Tensor[T, S], x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.betainc(a.tensor, b.tensor, x.tensor))

    // TODO bincount cannot be implemented type-safely because shape depends on runtime data.
    // https://www.tensorflow.org/versions/r1.14/api_docs/python/tf/math/bincount

    /** 
      * Returns element-wise smallest integer not less than x.
      * 
      * @param x A tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def ceil[T <: Roundable, S <: Shape](x: Tensor[T, S]): Tensor[T, S] = 
        new Tensor[T, S](tf.ceil(x.tensor))
    
    // TODO confusion_matrix has output shape n x n, where n depends on the number of unique values in a 1-D array

    // TODO conj requires a notion of complex numbers

    /**
      * Computes cos of x element-wise.
      * 
      * @param x A tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def cos[T <: Trig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.cos(x.tensor))
    
    /**
      * Computes hyperbolic cosine of x element-wise.
      * 
      * @param x A tensor
      * @tparam T Type of input and output elements
      * @tparam S Shape of input and output tensor
      */
    def cosh[T <: Trig, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.cosh(x.tensor))
    
    /**
      * Computes number of nonzero elements across dimensions of a tensor.
      * 
      * Reduces input_tensor along the dimensions given in axis. Unless keepdims is true, the rank of the tensor is reduced
      * by 1 for each entry in axis. If keepdims is true, the reduced dimensions are retained with length 1.
      *
      * If axis has no entries, all dimensions are reduced, and a tensor with a single element is returned.
      * 
      * NOTE Floating point comparison to zero is done by exact floating point equality check. Small values are not rounded
      * to zero for purposes of the nonzero check.
      * 
      * @param input_tensor The tensor to reduce
      * @param axis         The dimensions to reduce. If SNil (the default), reduces all dimensions.
      * @param keepdims     If true, retains reduced dimensions with length 1.
      * @param dtype        The output dtype
      * @tparam InputT      Type of input elements
      * @tparam OutputT     Type of output elements
      * @tparam S           Shape of input tensor
      * @tparam Axis        Type of axes to reduce over, or None to reduce over all
      */
    def count_nonzero[InputT <: Numeric | Boolean | String, OutputT, S <: Shape, Axis <: None.type | Indices](
        input_tensor: Tensor[InputT, S],
        axis: Axis = None,
        keepdims: Boolean = false,
        dtype: DataType[OutputT] = TensorFlow.int64
    ): Tensor[OutputT, Shape.Reduce[S, Axis]] = {
        new Tensor(tf.count_nonzero(input_tensor.tensor, encodeAxis(axis), keepdims, dtype.dtype))
    }
    
    /** Compute the cumulative product of the tensor `x` along `axis`.
      * 
      * By default, this op performs an inclusive cumprod, which means that the first element of the input
      * is identical to the first element of the output.
      * By setting the `exclusive` arg to `true`, an exclusive cumprod is performed instead.
      * By setting the `reverse` arg to `true`, the cumprod is performed in the opposite direction.
      * This is more efficient than using separate `tf.reverse ops`.
      * The reverse and exclusive args can also be combined.
      * 
      * @param x            A tensor
      * @param axis         Axis to reduce over
      * @param exclusive    If `true`, perform exclusive cumprod
      * @param reverse      If `true`, perform cumprod in opposite direction.
      * 
      * @tparam T           Type of input and output elements
      * @tparam S           Shape of input tensor
      * @tparam Axis        Type of the axis to reduce over
      */
    def cumprod[T <: Cumulable, S <: Shape, Axis <: Index](
        x: Tensor[T, S],
        axis: Axis = 0,
        exclusive: Boolean = false,
        reverse: Boolean = false
    ): Tensor[T, Shape.RemoveIndex[S, Axis]] = 
        new Tensor(tf.cumprod(x.tensor, axis, exclusive, reverse))

    /** Compute the cumulative sum of the tensor `x` along `axis`.
      * 
      * By default, this op performs an inclusive cumsum, which means that the first element of the input
      * is identical to the first element of the output.
      * By setting the `exclusive` arg to `true`, an exclusive cumsum is performed instead.
      * By setting the `reverse` arg to `true`, the cumsum is performed in the opposite direction.
      * This is more efficient than using separate `tf.reverse ops`.
      * The reverse and exclusive args can also be combined.
      * 
      * @param x            A tensor
      * @param axis         Axis to reduce over
      * @param exclusive    If `true`, perform exclusive cumsum
      * @param reverse      If `true`, perform cumsum in opposite direction.
      * 
      * @tparam T           Type of input and output elements
      * @tparam S           Shape of input tensor
      * @tparam Axis        Type of the axis to reduce over
      */
    def cumsum[T <: Cumulable, S <: Shape, Axis <: Index](
        x: Tensor[T, S],
        axis: Axis = 0,
        exclusive: Boolean = false,
        reverse: Boolean = false
    ): Tensor[T, Shape.RemoveIndex[S, Axis]] = 
        new Tensor(tf.cumsum(x.tensor, axis, exclusive, reverse))
    
    // ------ TODO add rest of tf.math

     /**
      * Computes the mean of elements across dimensions of a tensor.
      *
      * Reduces `input_tensor` along the dimensions given in `axis`. Unless `keepdims` is `true`, the rank of the tensor
      * is reduced by 1 for each entry in `axis`. If `keepdims` is `true`, the reduced dimensions are retained with length 1.
      * 
      * If axis is `None`, all dimensions are reduced, and a tensor with a single element is returned.
      * 
      * @param input_tensor The tensor to reduce
      * @param axis         The dimensions to reduce. If `None` (the default), reduces all dimensions
      * @param keepdims     If `true`, retains reduced dimensions with length 1. Note that there is no type-level support for `keepdims=true`.
      */
    // TODO keepdims type-level support. Requires this issue to be resolved https://github.com/lampepfl/dotty/issues/8010#issuecomment-575061446
    def reduce_mean[T <: Numeric, S <: Shape, Axis <: None.type | Indices](
        input_tensor: Tensor[T, S],
        axis: Axis = None,
        keepdims: Boolean = false
    ): Tensor[T, Shape.Reduce[S, Axis]] = 
        new Tensor(tf.reduce_mean(input_tensor.tensor, encodeAxis(axis), keepdims))

    /**
      * Returns element-wise largest integer not greater than `x`.
      * 
      * @param x    A Tensor
      * @tparam T   Type of input and output elements
      * @tparam S   Type of input and output shape
      */
    def floor[T <: Roundable, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.floor(x.tensor))

    def pow[T, S <: Shape](x: Tensor[T, S], y: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.pow(x.tensor, y.tensor))
    
    def square[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.square(x.tensor))
    
    def sin[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sin(x.tensor))

    def sinh[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sinh(x.tensor))

    def tan[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.tan(x.tensor))

    def tanh[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.tanh(x.tensor))
}
