package io.kjaer.tensorflow.core

import io.kjaer.tensorflow.math.Math
import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.TF.tf
import scala.annotation.implicitNotFound
import scala.compiletime.ops.any.==

object TensorFlow extends Math {
    ///////////    
    // Types //
    ///////////

    def float16: DataType[Float16] = DataType.FLOAT16
    def float32: DataType[Float] = DataType.FLOAT32
    def float64: DataType[Double] = DataType.FLOAT64
    def bfloat16: DataType[BFloat16] = DataType.BFLOAT16
    def complex64: DataType[Complex64] = DataType.COMPLEX64
    def complex128: DataType[Complex128] = DataType.COMPLEX128
    def int8: DataType[Int8] = DataType.INT8
    def uint8: DataType[UInt8] = DataType.UINT8
    def uint16: DataType[UInt16] = DataType.UINT16
    def uint32: DataType[UInt32] = DataType.UINT32
    def uint64: DataType[UInt64] = DataType.UINT64
    def int16: DataType[Int16] = DataType.INT16
    def int32: DataType[Int] = DataType.INT32
    def int64: DataType[Long] = DataType.INT64
    def bool: DataType[Boolean] = DataType.BOOL
    def string: DataType[String] = DataType.STRING
    def qint8: DataType[QInt8] = DataType.QINT8
    def quint8: DataType[QUInt8] = DataType.QUINT8
    def qint16: DataType[QInt16] = DataType.QINT16
    def quint16: DataType[QUInt16] = DataType.QUINT16
    def qint32: DataType[QInt32] = DataType.QINT32
    def resource: DataType[Resource] = DataType.RESOURCE
    def variant: DataType[Variant] = DataType.VARIANT

    /////////////
    // Modules //
    /////////////
    def train: Training = new Training(tf.train)
    def math: Math = new Math { }
    
    //////////////////
    // Constructors //
    //////////////////
    def Variable[T, S <: Shape](initialValue: Tensor[T, S]): Variable[T, S] =
        new Variable[T, S](tf.Variable(initialValue.tensor))

    def Session(): Session = new Session(tf.Session())

    type Unbox[S] = S match {
        case Seq[t] => Unbox[t]
        case _ => S
    }

    // TODO check that shape has the correct rank, at least.
    def constant[V, T <: Unbox[V] : py.Reader : py.Writer, S <: Shape](
        value: V,
        dtype: DataType[T],
        shape: S = SNil
    )(given py.Reader[V], py.Writer[V]): Tensor[T, S] = {
        new Tensor(tf.constant(value, dtype.dtype, shape.toSeq))
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
    def random_uniform[T : py.Reader : py.Writer, S <: Shape](
        shape: S,
        min: T,
        max: T,
        dataType: DataType[T] = float32
    ): Tensor[T, S] = {
        new Tensor[T, S](tf.random_uniform(shape.toSeq, min, max, dataType.dtype))
    }
    
    def transpose[T, S <: Shape](tensor: Tensor[T, S]): Tensor[T, Shape.Reverse[S]] =
        new Tensor[T, Shape.Reverse[S]](tf.transpose(tensor.tensor))

    
    @implicitNotFound("Reshape dimensions mismatch")
    type CanReshape[Old <: Shape, New <: Shape] = (Shape.NumElements[Old] == Shape.NumElements[New]) match {
        case true => ValueOf[true]
        // case false => Error["Cannot reshape tensor from " + ToString[Shape.NumElements[Old]] +
        //                     " elements to " + Shape.NumElements[Old] + " elements. Number of elements must be the same"]
    }

    
    def reshape[T, OldShape <: Shape, NewShape <: Shape](tensor: Tensor[T, OldShape], shape: NewShape)(
        given ev: CanReshape[OldShape, NewShape]
    ): Tensor[T, NewShape] = {
        new Tensor[T, NewShape](tf.reshape(tensor.tensor, shape.toSeq))
    }

    def broadcast_to[T, OldShape <: Shape, NewShape <: Shape](
        input: Tensor[T, OldShape],
        shape: NewShape
    ): Tensor[T, NewShape] =
        new Tensor(tf.broadcast_to(input.tensor, shape.toSeq))

    //////////////////
    // Initializers //
    //////////////////

    def initialize_all_variables(): Operation = new Operation(tf.initialize_all_variables())
    def global_variables_initializer(): Operation = new Operation(tf.global_variables_initializer())

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

}
