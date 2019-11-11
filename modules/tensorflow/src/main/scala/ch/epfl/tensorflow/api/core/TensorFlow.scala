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
    def Variable[T : TFEncoding, S <: Shape, L <: Labels](initialValue: Tensor[T, S, L]): Variable[T, S, L] =
        new Variable[T, S, L](tf.Variable(initialValue.tensor))

    // TODO constant types where T is a Seq[Seq[T]] etc.
    def constant[T : TFEncoding : py.Reader : py.Writer, A <: Axes](value: T, axes: A = HNil): Tensor.Aux[T, A] = {
        val dtype = summon[TFEncoding[T]].dataType.dtype
        Tensor[T, A](tf.constant(value, dtype, axes.shape.toSeq))
    }

    def zeros[T : TFEncoding, A <: Axes](axes: A, dataType: DataType[T] = float32): Tensor.Aux[T, A] =
        Tensor[T, A](tf.zeros(axes.shape.toSeq, dataType.dtype))
    
    // TensorFlow also has a method accepting a 1D tensor of dimensions,
    // but we cannot know at compiletime what it contains, so we cannot support it.
    //     
    // def zeros[T : TFEncoding, S <: Shape.OfDimension[1]](
    // shape: Tensor[Int, S],
    //     dataType: DataType[T] = float32
    // ): Tensor[T, _] = ??? // unknown shape!

    // Same shape and datatype
    def zeros_like[T : TFEncoding, S <: Shape, L <: Labels](tensor: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.zeros_like(tensor.tensor))

    // Same shape, potentially new datatype
    def zeros_like[T : TFEncoding, S <: Shape, L <: Labels](tensor: Tensor[_, S, L], dataType: DataType[T]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.zeros_like(tensor.tensor, dataType.dtype))

    def random_uniform[T : TFEncoding : IsNumeric : py.Reader : py.Writer, A <: Axes](
        axes: A,
        min: T,
        max: T,
        dataType: DataType[T] = float32
    ): Tensor.Aux[T, A] =
        Tensor[T, A](tf.random_uniform(axes.shape.toSeq, min, max, dataType.dtype))

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

    def reduce_mean[T : TFEncoding](tensor: Tensor[T, ?, ?]): Tensor.Aux[T, HNil] =
        Tensor[T, HNil](tf.reduce_mean(tensor.tensor))

    // TODO fix
    def reduce_mean[T : TFEncoding, A <: Axes, L <: Label](
        tensor: Tensor.Aux[T, A],
        axis: L
    ): Tensor.Aux[T, Axes.Remove[L, A]] = {
        val index = 1 // tensor.labels.indexOf(axis)
        Tensor[T, Axes.Remove[L, A]](tf.reduce_mean(tensor.tensor, index))
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
    def pow[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L], y: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.pow(x.tensor, y.tensor))

    def abs[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.abs(x.tensor))

    def floor[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.floor(x.tensor))
    
    def square[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.square(x.tensor))
    
    def sin[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.sin(x.tensor))

    def sinh[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.sinh(x.tensor))

    def asin[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.asin(x.tensor))

    def asinh[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.asinh(x.tensor))

    def tan[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.tan(x.tensor))

    def tanh[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.tanh(x.tensor))

    def atan[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.atan(x.tensor))

    def atanh[T : TFEncoding, S <: Shape, L <: Labels](x: Tensor[T, S, L]): Tensor[T, S, L] =
        new Tensor[T, S, L](tf.atanh(x.tensor))
}
