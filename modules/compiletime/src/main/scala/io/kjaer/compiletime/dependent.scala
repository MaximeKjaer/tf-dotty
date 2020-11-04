package io.kjaer.compiletime

import scala.annotation.infix
import scala.compiletime.ops.int._

// Extensions on ints that allow scala.compiletime.ops to be dependently typed
extension [X <: Int, Y <: Int](x: Int) {
    @infix def add(y: Y): X + Y  = (x + y).asInstanceOf[X + Y]
    @infix def sub(y: Y): X - Y  = (x - y).asInstanceOf[X - Y]
    @infix def mul(y: Y): X * Y  = (x * y).asInstanceOf[X * Y]
    @infix def lt(y: Y):  X < Y  = (x < y).asInstanceOf[X < Y]
    @infix def le(y: Y):  X <= Y = (x <= y).asInstanceOf[X <= Y]
}