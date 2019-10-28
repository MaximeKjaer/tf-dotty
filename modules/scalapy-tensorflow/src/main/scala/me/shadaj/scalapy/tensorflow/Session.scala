package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.numpy._
import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.Writer

@py.native trait PythonDict[K, V] extends py.Object
object PythonDict {
  implicit def mapToPythonDict[K, V](map: Map[K, V])(implicit writer: Writer[Map[K, V]]): PythonDict[K, V] = {
    py.global.dict(map).as[PythonDict[K, V]]
  }
}

@py.native trait Session extends py.Object {
  def run(fetches: Operation): Unit = py.native

  def run(fetches: Variable): Seq[Double] = py.native

  def run(fetches: Tensor): Seq[NDArray[Double]] = py.native

  def run(fetches: Operation, feedDict: PythonDict[Tensor, py.Object]): Unit = py.native

  def run(fetches: Tensor, feedDict: PythonDict[Tensor, py.Object]): Seq[NDArray[Double]] = py.native

  def run(fetches: Seq[Tensor], feedDict: PythonDict[Tensor, py.Object]): Seq[Seq[NDArray[Double]]] = py.native
}
