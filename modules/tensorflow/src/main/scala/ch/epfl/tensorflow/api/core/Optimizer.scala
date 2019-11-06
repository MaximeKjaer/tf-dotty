package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.Operation
import me.shadaj.scalapy.tensorflow.{Optimizer => PyOptimizer}

class Optimizer private[core] (val optimizer: PyOptimizer) {
    def minimize(loss: Tensor[?, ?, ?]): Operation = optimizer.minimize(loss.tensor)

    def apply_gradients(grads_and_vars: Seq[(Tensor[?, ?, ?], Variable[?, ?, ?])]): Operation = 
        optimizer.apply_gradients(grads_and_vars.map((tensor, variable) => (tensor.tensor, variable.variable)))
}
