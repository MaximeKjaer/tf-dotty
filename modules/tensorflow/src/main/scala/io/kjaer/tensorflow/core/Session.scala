package io.kjaer.tensorflow.core

import me.shadaj.scalapy.tensorflow.{Session => PySession}

class Session private[core] (val session: PySession) {
    def run(fetches: Operation): Unit = session.run(fetches.operation)
    def run(fetches: Variable[_, _]): Seq[Double] = session.run(fetches.variable)
    
}
