package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

// some TensorFlow operations require a LIST list, not just something iterable
@py.native trait PythonList[T] extends py.Object

object PythonList {
  implicit def seqToPythonList[T](seq: Seq[T])(implicit writer: py.Writer[Seq[T]]): PythonList[T] = {
    py.global.list(py.Any.from(seq)(writer)).as[PythonList[T]]
  }
}
