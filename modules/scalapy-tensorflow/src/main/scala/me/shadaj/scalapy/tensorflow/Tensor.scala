package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py
import me.shadaj.scalapy.tensorflow.dtypes.DType

@py.native trait Tensor extends py.Object {
  def name: String = py.native
  def device: String = py.native
  def dtype: DType = py.native
  def shape: Seq[Int] = (as[py.Dynamic].shape.as_list().as[Seq[Int]].toSeq)

  def unary_+(): Tensor = (+as[py.Dynamic]).as[Tensor]
  def unary_-(): Tensor = (-as[py.Dynamic]).as[Tensor]
  def unary_~(): Tensor = (~as[py.Dynamic]).as[Tensor]

  def +(that: Tensor): Tensor = (as[py.Dynamic] + that).as[Tensor]
  def -(that: Tensor): Tensor = (as[py.Dynamic] - that).as[Tensor]
  def *(that: Tensor): Tensor = (as[py.Dynamic] * that).as[Tensor]
  def /(that: Tensor): Tensor = (as[py.Dynamic] / that).as[Tensor]
  def &(that: Tensor): Tensor = (as[py.Dynamic] & that).as[Tensor]
  def |(that: Tensor): Tensor = (as[py.Dynamic] | that).as[Tensor]
  def ^(that: Tensor): Tensor = (as[py.Dynamic] ^ that).as[Tensor]
}
