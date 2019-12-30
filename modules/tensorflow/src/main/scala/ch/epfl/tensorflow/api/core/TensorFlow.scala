package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.TF.tf

object TensorFlow {
    ///////////    
    // Types //
    ///////////
    def float32: DataType[Float] = FLOAT32
    def float64: DataType[Double] = FLOAT64
    def int32: DataType[Int] = INT32
    def int64: DataType[Long] = INT64
    def bool: DataType[Boolean] = BOOLEAN

    /////////////
    // Modules //
    /////////////
    def train: Training = new Training(tf.train)
    
    //////////////////
    // Constructors //
    //////////////////
    def Variable[T, S <: Shape](initialValue: Tensor[T, S]): Variable[T, S] =
        new Variable[T, S](tf.Variable(initialValue.tensor))

    // TODO constant types where T is a Seq[Seq[T]] etc.
    // TODO constant types where dtype is inferred from the value

    def constant[T, S <: Shape](value: T, dtype: DataType[T], shape: S = SNil): Tensor[T, S] = {
        implicit val reader = dtype.reader
        implicit val writer = dtype.writer
        new Tensor[T, S](tf.constant(value, dtype.dtype, shape.toSeq))
    }

    def zeros[T, S <: Shape](shape: S, dataType: DataType[T] = float32): Tensor[T, S] =
        new Tensor[T, S](tf.zeros(shape.toSeq, dataType.dtype))
    
    // TensorFlow also has a method accepting a 1D tensor of dimensions,
    // but we cannot know at compiletime what it contains, so we cannot support it.
    //     
    // def zeros[T, S <: Shape.OfDimension[1]](
    // shape: Tensor[Int, S],
    //     dataType: DataType[T] = float32
    // ): Tensor[T, _] = ??? // unknown shape!

    // Same shape and datatype
    def zeros_like[T, S <: Shape](tensor: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.zeros_like(tensor.tensor))

    // Same shape, potentially new datatype
    def zeros_like[T, S <: Shape](tensor: Tensor[_, S], dataType: DataType[T]): Tensor[T, S] =
        new Tensor[T, S](tf.zeros_like(tensor.tensor, dataType.dtype))

    // TODO encode T is numeric
    def random_uniform[T, S <: Shape](
        shape: S,
        min: T,
        max: T,
        dataType: DataType[T] = float32
    ): Tensor[T, S] = {
        implicit val reader = dataType.reader
        implicit val writer = dataType.writer
        new Tensor[T, S](tf.random_uniform(shape.toSeq, min, max, dataType.dtype))
    }
    
    def transpose[T, S <: Shape](tensor: Tensor[T, S]): Tensor[T, Shape.Reverse[S]] =
        new Tensor[T, Shape.Reverse[S]](tf.transpose(tensor.tensor))

    //////////////
    // Reducers //
    //////////////

    // TODO
    // - reduce_all
    // - reduce_any
    // - reduce_euclidian_norm
    // - reduce_logsum_exp
    // - reduce_max
    // - reduce_min
    // - reduce_prod
    // - reduce_std
    // - reduce_sum
    // - reduce_variance

    def reduce_mean[T, S <: Shape, S2 <: Indices](
        tensor: Tensor[T, S],
        axes: S2 = SNil
    ): Tensor[T, Shape.Reduce[S, S2]] =
        new Tensor[T, Shape.Reduce[S, S2]](tf.reduce_mean(tensor.tensor, axes.indices.toSeq))
    
    
    //////////
    // Math //
    //////////

    def abs[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.abs(x.tensor))
        
    def accumulate_n[T, S <: Shape](inputs: Seq[Tensor[T, S]]): Tensor[T, S] = 
        new Tensor[T, S](tf.accumulate_n(inputs.map(_.tensor)))

    def acos[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sin(x.tensor))
    
    def acosh[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sin(x.tensor))

    // TODO add (supports broadcasting)

    def add_n[T, S <: Shape](inputs: Seq[Tensor[T, S]]): Tensor[T, S] = 
        new Tensor[T, S](tf.add_n(inputs.map(_.tensor)))

    def angle[T, S <: Shape](input: Tensor[T, S]): Tensor[Float, S] =
        new Tensor[Float, S](tf.angle(input.tensor))

    // todo encode T1, T2 isNumeric
    def argmax[T1, T2 <: Int | Long, S <: Shape](
        input: Tensor[T1, S],
        axes: Indices = SNil,
        output_type: DataType[T2] = int64
    ): Tensor[T2, S] =
        new Tensor[T2, S](tf.argmax(input.tensor, axes.indices.toSeq, output_type.dtype))

    // todo encode T1, T2 isNumeric
    def argmin[T1, T2 <: Int | Long, S <: Shape](
        input: Tensor[T1, S],
        axes: Indices = SNil,
        output_type: DataType[T2] = int64
    ): Tensor[T2, S] =
        new Tensor[T2, S](tf.argmin(input.tensor, axes.indices.toSeq, output_type.dtype))

    def asin[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.asin(x.tensor))

    def asinh[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.asinh(x.tensor))
    
    def atan[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atan(x.tensor))

    def atan2[T <: Float | Double, S <: Shape](x: Tensor[T, S], y: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atan2(x.tensor, y.tensor))

    def atanh[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atanh(x.tensor))

    // TODO bessel functions
    
    def betainc[T <: Float | Long, S <: Shape](a: Tensor[T, S], b: Tensor[T, S], x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.betainc(a.tensor, b.tensor, x.tensor))

    // TODO bincount cannot be implemented type-safely without min/max
    // https://www.tensorflow.org/versions/r1.14/api_docs/python/tf/math/bincount

    def ceil[T <: Float | Double, S <: Shape](x: Tensor[T, S]): Tensor[T, S] = 
        new Tensor[T, S](tf.ceil(x.tensor))
    
    // TODO confusion_matrix has shape that depends on the number of unique values in a 1-D array

    // TODO conj requires a notion of complex numbers

    def cos[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.cos(x.tensor))
    
    def cosh[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.cosh(x.tensor))
    
    // Todo must be numeric, bool or string
    def count_nonzero[T1 <: Float | Double | Int | Long | Boolean | String, T2, S <: Shape, Sel <: Indices](
        input_tensor: Tensor[T1, S],
        axis: Sel = SNil,
        dtype: DataType[T2] = int64
    ): Tensor[T2, Shape.Reduce[S, Sel]] =
        new Tensor(tf.count_nonzero(input_tensor.tensor, axis.indices.toSeq, dtype.dtype))

    def cumprod[T <: Float | Double | Int | Long, S <: Shape, Sel <: Indices](
        x: Tensor[T, S],
        axis: Sel = SNil,
        exclusive: Boolean = false,
        reverse: Boolean = false
    ): Tensor[T, Shape.Reduce[S, Sel]] = 
        new Tensor(tf.cumprod(x.tensor, axis.indices.toSeq, exclusive, reverse))

    def cumsum[T <: Float | Double | Int | Long, S <: Shape, Sel <: Indices](
        x: Tensor[T, S],
        axis: Sel = SNil,
        exclusive: Boolean = false,
        reverse: Boolean = false
    ): Tensor[T, Shape.Reduce[S, Sel]] = 
        new Tensor(tf.cumprod(x.tensor, axis.indices.toSeq, exclusive, reverse))
    
    // ------

    def floor[T, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
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
