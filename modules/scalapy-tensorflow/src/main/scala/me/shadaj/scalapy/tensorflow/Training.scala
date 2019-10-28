package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py

@py.native trait Training extends py.Object {
  def ExponentialMovingAverage(decay: Double): ExponentialMovingAverage = py.native

  def GradientDescentOptimizer(learningRate: Double): Optimizer = py.native

  def AdamOptimizer(lr: Double): Optimizer = py.native
}
