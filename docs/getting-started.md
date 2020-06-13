---
id: getting-started
title: Getting Started
---

tf-dotty is a shape-safe facade for TensorFlow in Dotty (a.k.a. Scala 3).

_Note that you must use tf-dotty from a Dotty project, and not a Scala 2 project._

## Installing Python dependencies

This project communicates with the TensorFlow 1.15 Python API, so you will also need to have Python 3.7 and pip installed. On Ubuntu, you can install these dependencies as follows:

```console
$ sudo apt-get install software-properties-common
$ sudo add-apt-repository ppa:deadsnakes/ppa
$ sudo apt-get update
$ sudo apt-get install python3.7 libpython3.7
```

To install the runtime dependencies, run:

```console
$ pip install -r requirements.txt
```

You can also choose to do this in a virtualenv with [virtualenvwrapper](https://virtualenvwrapper.readthedocs.io/en/latest/install.html):

```console
$ mkvirtualenv -p python3.7 -r requirements.txt tf-dotty
```

## Setting up sbt

To use tf-dotty, you must use the following sbt settings:

```scala
lazy val myProject = project
  .settings(
    fork := true,
    javaOptions += s"-Djna.library.path=${"python3-config --prefix".!!.trim}/lib",
    libraryDependencies += "io.kjaer" %% "tf-dotty" % "insert-version-here"
  )
```

## Importing

```scala
import io.kjaer.compiletime._
import io.kjaer.tensorflow.core._

val matrix = tf.zeros(2 #: 2 #: SNil)
 // matrix: Tensor[Float, 2 #: 2 #: SNil] = Tensor("zeros:0", shape=(2, 2), dtype=float32)
```

Read the documentation on [tensor shapes](tensor.md) to learn about `#:` and `SNil`.
