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

    //////////////////
    // Constructors //
    //////////////////
    def Variable[T : TFEncoding, S <: Shape](initialValue: Tensor[T, S]): Variable[T, S] =
        new Variable[T, S](tf.Variable(initialValue.tensor))

    // TODO constant types where T is a Seq[Seq[T]] etc.
    def constant[T : TFEncoding : py.Reader : py.Writer](value: T, shape: Shape = SNil): Tensor[T, shape.type] = {
        val dtype = summon[TFEncoding[T]].dataType.dtype
        new Tensor[T, shape.type](tf.constant(value, dtype, shape.toSeq))
    }

    /** 
     * Creates a tensor with all elements set to zero.
     * @tparam S Type of the shape
     * @return Tensor of Float, of the shape {@code S}
     */
    def zeros[S <: Shape](given shape: ShapeOf[S]): Tensor[Float, S] =
        new Tensor[Float, S](tf.zeros(shape.value.toSeq))

    /**
     * Creates a tensor with all elements set to zero.
     * @param tensor Tensor to use as a template
     * @return Tensor of the same type and shape as {@code tensor}, with all elements set to zero.
     */
    def zerosLike[T : TFEncoding, S <: Shape](tensor: Tensor[T, S])(given shape: ShapeOf[S]): Tensor[T, S] =
        new Tensor[T, S](tf.zeros(shape.value.toSeq))
    
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
