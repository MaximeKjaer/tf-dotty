import io.kjaer.tensorflow.core._
import me.shadaj.scalapy.py
import util.Random

object Example extends App {    

    val xData = Seq.fill(5000)(Random.nextFloat)
    val yData = xData.map(_ * 0.1f + 0.3f)

    // Dotty's type inference needs a little help here:
    val yTensor = tf.constant[Seq[Float], Float, 5000 #: SNil.type](yData, tf.float32, 5000 #: SNil)

    val W = tf.Variable(tf.random_uniform(1 #: SNil, -1f, 1f))
    val b = tf.Variable(tf.zeros(1 #: SNil))
    val x = tf.constant[Seq[Float], Float, 5000 #: SNil.type](xData, tf.float32, 5000 #: SNil)
    val y = (tf.broadcast_to(W, 5000 #: SNil) * x) + tf.broadcast_to(b, 5000 #: SNil)
    
    val loss = tf.reduce_mean(tf.square(y - yTensor))
    val optimizer = tf.train.GradientDescentOptimizer(0.5)
    val train = optimizer.minimize(loss)
    
    val init = tf.global_variables_initializer()

    val sess = tf.Session()
    sess.run(init)

    (0 to 200).foreach { step =>
        sess.run(train)

        if (step % 20 == 0) {
            println(s"$step ${sess.run(W).head} ${sess.run(b).head}")
        }
    }
}