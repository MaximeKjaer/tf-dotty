package io.kjaer.tensorflow.core

import me.shadaj.scalapy.tensorflow.{Optimizer => PyOptimizer}

class Optimizer private[core] (val optimizer: PyOptimizer) {
    def minimize(loss: Tensor[_, _]): Operation = new Operation(optimizer.minimize(loss.tensor))

    def apply_gradients(grads_and_vars: Seq[(Tensor[_, _], Variable[_, _])]): Operation = 
        new Operation(
            optimizer.apply_gradients(
                grads_and_vars.map((tensor, variable) => (tensor.tensor, variable.variable))
            )
        )
}
