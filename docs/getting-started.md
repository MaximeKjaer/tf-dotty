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

tf.zeros(2 #: 2 #: SNil) // val res: Tensor[Float, 2 #: 2 #: SNil] = Tensor("zeros:0", shape=(2, 2), dtype=float32)
```

Read the documentation on [tensor shapes](shape.md) to learn about `#:` and `SNil`.

## Dependencies

### tf-dotty

tf-dotty is not yet published on Maven. See how to compile from source in the docs for [contributing](contributing.md), and place the compiled source in a `lib` folder.

### Python dependencies

This project communicates with the Python implementation of TensorFlow. Therefore, you will need to have Python 3.7 and pip installed. One of the installed dependencies, `jep`, needs to have the `JAVA_HOME` environment variable set. This could look as follows (but check your local install for the exact value):

```console
$ export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

To install the dependencies, run:

```console
$ pip install -r requirements.txt
```

You can also choose to do this in a virtualenv with virtualenvwrapper:

```console
$ mkvirtualenv -p python3.7 -r requirements.txt tf-dotty
```
