package me.shadaj.scalapy

import me.shadaj.scalapy.py
import me.shadaj.scalapy.numpy.NDArray

package object tensorflow {
  import me.shadaj.scalapy.py.Writer
  import me.shadaj.scalapy.py.FacadeValueProvider
  import me.shadaj.scalapy.py.Reader
  implicit def double2Tensor(d: Double): Tensor = {
    py.Any.from(d)(Writer.doubleWriter).as[Tensor]
  }

  implicit def nd2Tensor(nd: NDArray[Double]): Tensor = {
    nd.as[Tensor]
  }
}
