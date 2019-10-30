package ch.epfl.tensorflow.api.core

import me.shadaj.scalapy.tensorflow.{Training => PyTraining}

class Training private[core] (val training: PyTraining) {
    def GradientDescentOptimizer(learningRate: Double): Optimizer = 
        new Optimizer(training.GradientDescentOptimizer(learningRate))
}
