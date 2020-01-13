---
id: getting-started
title: Getting Started
---

tf-dotty is a shape-safe facade for TensorFlow in Dotty (a.k.a. Scala 3).

_Note that you must use tf-dotty from a Dotty project, and not a Scala 2 project._

## Importing

```scala
import ch.epfl.tensorflow.api.core._
import ch.epfl.tensorflow.api.core.TF.tf

val matrix = tf.zeros(2 #: 2 #: SNil)
 // matrix: Tensor[Float, 2 #: 2 #: SNil] = Tensor("zeros:0", shape=(2, 2), dtype=float32)
```

Read the documentation on [tensor shapes](tensor.md) to learn about `#:` and `SNil`.

## Installing Python dependencies

This project communicates with the TensorFlow 1.14 Python API. You will need to have Python 3.7 and pip installed. To install the dependencies, run:

```console
$ pip install -r requirements.txt
```

You can also choose to do this in a virtualenv with virtualenvwrapper:

```console
$ mkvirtualenv -p python3.7 -r requirements.txt tf-dotty
```
