package me.shadaj.scalapy.tensorflow

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.PyFunction

@py.native trait NeuralNetwork extends py.Object {
  def relu: PyFunction = py.native
  def relu(features: Tensor): Tensor = py.native

  def softsign: PyFunction = py.native
  def softsign(features: Tensor): Tensor = py.native

  def tanh: PyFunction = py.native
  def tanh(features: Tensor): Tensor = py.native

  def sigmoid: PyFunction = py.native
  def sigmoid(features: Tensor): Tensor = py.native

  def l2_loss(t: Tensor): Tensor = py.native
}
