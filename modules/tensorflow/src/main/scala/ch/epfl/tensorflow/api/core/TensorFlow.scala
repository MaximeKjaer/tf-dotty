package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.TF.tf

object TensorFlow {
    ///////////    
    // Types //
    ///////////
    def float32 = FLOAT32
    def float64 = FLOAT64
    def int32 = INT32
    def bool = BOOLEAN

    /////////////
    // Modules //
    /////////////
    def train: Training = new Training(tf.train)
    
    //////////////////
    // Constructors //
    //////////////////
    def Variable[T : TFEncoding, S <: Shape](initialValue: Tensor[T, S]): Variable[T, S] =
        new Variable[T, S](tf.Variable(initialValue.tensor))

    // TODO constant types where T is a Seq[Seq[T]] etc.
    def constant[T : TFEncoding : py.Reader : py.Writer, S <: Shape](value: T, shape: S = SNil): Tensor[T, S] = {
        val dtype = summon[TFEncoding[T]].dataType.dtype
        new Tensor[T, S](tf.constant(value, dtype, shape.toSeq))
    }

    def zeros[T : TFEncoding, S <: Shape](shape: S, dataType: DataType[T] = float32): Tensor[T, S] =
        new Tensor[T, S](tf.zeros(shape.toSeq, dataType.dtype))
    
    // TensorFlow also has a method accepting a 1D tensor of dimensions,
    // but we cannot know at compiletime what it contains, so we cannot support it.
    //     
    // def zeros[T : TFEncoding, S <: Shape.OfDimension[1]](
    // shape: Tensor[Int, S],
    //     dataType: DataType[T] = float32
    // ): Tensor[T, _] = ??? // unknown shape!

    // Same shape and datatype
    def zeros_like[T : TFEncoding, S <: Shape](tensor: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.zeros_like(tensor.tensor))

    // Same shape, potentially new datatype
    def zeros_like[T : TFEncoding, S <: Shape](tensor: Tensor[_, S], dataType: DataType[T]): Tensor[T, S] =
        new Tensor[T, S](tf.zeros_like(tensor.tensor, dataType.dtype))

    def random_uniform[T : TFEncoding : IsNumeric : py.Reader : py.Writer, S <: Shape](
        shape: S,
        min: T,
        max: T,
        dataType: DataType[T] = float32
    ): Tensor[T, S] =
        new Tensor[T, S](tf.random_uniform(shape.toSeq, min, max, dataType.dtype))

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

    def reduce_mean[T : TFEncoding, S <: Shape, S2 <: Select](
        tensor: Tensor[T, S],
        axes: S2 = SNil
    ): Tensor[T, Shape.Remove[S, S2]] = {
        println(axes.selectedIndices)
        new Tensor[T, Shape.Remove[S, S2]](tf.reduce_mean(tensor.tensor, axes.selectedIndices))
    }
    
    
    //////////
    // Math //
    //////////
    /**
     * Given a tensor {@code x} and {@code y}, this operation computes x^y element-wise.
     * @param x Tensor to exponentiate
     * @param y Tensor to use as exponent
     * @return Element-wise result of x^y 
     */
    def pow[T : TFEncoding, S <: Shape](x: Tensor[T, S], y: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.pow(x.tensor, y.tensor))

    def abs[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.abs(x.tensor))

    def floor[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.floor(x.tensor))
    
    def square[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.square(x.tensor))
    
    def sin[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sin(x.tensor))

    def sinh[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.sinh(x.tensor))

    def asin[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.asin(x.tensor))

    def asinh[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.asinh(x.tensor))

    def tan[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.tan(x.tensor))

    def tanh[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.tanh(x.tensor))

    def atan[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atan(x.tensor))

    def atanh[T : TFEncoding, S <: Shape](x: Tensor[T, S]): Tensor[T, S] =
        new Tensor[T, S](tf.atanh(x.tensor))
}
