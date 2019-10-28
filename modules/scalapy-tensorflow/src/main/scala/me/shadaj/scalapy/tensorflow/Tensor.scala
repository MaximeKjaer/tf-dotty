package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

@py.native trait Tensor extends py.Object {
  def unary_+(): Tensor = {
    (+as[py.Dynamic]).as[Tensor]
  }

  def unary_-(): Tensor = {
    (-as[py.Dynamic]).as[Tensor]
  }

  def unary_~(): Tensor = {
    (~as[py.Dynamic]).as[Tensor]
  }

  def +(that: Tensor): Tensor = {
    (as[py.Dynamic] + that).as[Tensor]
  }

  def -(that: Tensor): Tensor = {
    (as[py.Dynamic] - that).as[Tensor]
  }

  def *(that: Tensor): Tensor = {
    (as[py.Dynamic] * that).as[Tensor]
  }

  def /(that: Tensor): Tensor = {
    (as[py.Dynamic] / that).as[Tensor]
  }

  def &(that: Tensor): Tensor = {
    (as[py.Dynamic] & that).as[Tensor]
  }

  def |(that: Tensor): Tensor = {
    (as[py.Dynamic] | that).as[Tensor]
  }

  def ^(that: Tensor): Tensor = {
    (as[py.Dynamic] ^ that).as[Tensor]
  }

}
