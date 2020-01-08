---
id: architecture
title: Architecture
---

Calling a TensorFlow operation in tf-dotty traverses multiple layers, from top to bottom:

1. **tf-dotty**: Ensures shape-safety at compilation
2. **ScalaPy TensorFlow facade**: Macros for calling JNA
3. **TensorFlow Python**: TensorFlow Python API
4. **libtensorflow**: TensorFlow C++ library

## Why call TensorFlow Python instead of libtensorflow?

The core of TensorFlow is libtensorflow, a C++ library. TensorFlow also offers some higher-level APIs for constructing and executing TensorFlow graphs. These are available in several programming languages, but the most commonly used implementation is in Python. The tf-dotty library provides the same API for Dotty, and adds statically checked tensor shapes.

The TensorFlow maintainers' [recommended approach](https://github.com/tensorflow/docs/blob/master/site/en/r1/guide/extend/bindings.md) for writing such an API is to use the host language's foreign function interface (FFI) to call libtensorflow's C API. On the Java Virtual Machine, this would mean using either Java Native Interface (JNI) or Java Native Access (JNA). This is the approach taken by [tensorflow_scala](https://github.com/eaplatanios/tensorflow_scala), which targets Scala 2 and uses JNI to call libtensorflow.

Tf-dotty does not follow the recommended approach, for a few practical reasons. First, the build configuration for FFIs in Scala is complex [todo quantify]. Second, some TensorFlow modules, such as `tf.train` and `tf.nn`, are implemented in the Python API and not in C++ in libtensorflow [^work-in-progress], so tf-dotty would have to re-implement the functionality of large parts of TensorFlow. Third, even for modules that _are_ implemented in libtensorflow, the work of translating high-level API calls to low-level FFI calls is not the focus of tf-dotty.

[^work-in-progress]: Providing more functionality in the C library is [a work in progress](https://github.com/tensorflow/docs/blob/d13a1a14aeb09d0c0cc15b564ef1f4fd1ec2814c/site/en/r1/guide/extend/bindings.md#current-status) by the TensorFlow maintainers.

Instead, calling the TensorFlow Python API makes it possible to delegate the work of communicating with libtensorflow to CPython, the Python interpreter. CPython can is written in C and can therefore efficiently call the C++ libtensorflow library [citation needed]. Python was originally designed as ["glue" between components](https://www.python.org/doc/essays/omg-darpa-mcc-position/), and serves that purpose in tf-dotty.

## How to call Python from Dotty

The [ScalaPy library](https://github.com/shadaj/scalapy) makes it possible to call Python code from Scala. It uses JNA to bind to the CPython interpreter. Additionally, it provides an interface for defining static type "facades" to Python libraries, similarly to [ScalaJS facades](https://www.scala-js.org/libraries/facades.html) which type JavaScript libraries in Scala.js, or [DefinitelyTyped type definitions](https://github.com/DefinitelyTyped/DefinitelyTyped) which type JavaScript libraries in TypeScript.

The ScalaPy interface to write facades makes heavy use of Scala 2 macros, which are [not supported by Dotty](https://dotty.epfl.ch/docs/reference/dropped-features/macros.html). However, Dotty-compiled code [can call Scala 2.13 dependencies](https://www.scala-lang.org/2019/12/18/road-to-scala-3.html). In order to compile the TensorFlow facade with Scala 2.13, support for cross-building ScalaPy to Scala 2.13 was added in the pull request [shadaj/scalapy#46](https://github.com/shadaj/scalapy/pull/46).
