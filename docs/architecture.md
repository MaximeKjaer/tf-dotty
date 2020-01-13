---
id: architecture
title: Architecture
---

Calling a TensorFlow operation in tf-dotty traverses multiple layers, from top to bottom:

1. **tf-dotty**: Ensures shape-safety at compilation
2. **ScalaPy TensorFlow facade**: Macros for calling the Python interpreter
3. **TensorFlow Python API**: TensorFlow operations in Python
4. **libtensorflow**: TensorFlow C++ library

The core of TensorFlow is **libtensorflow**, a C++ library. TensorFlow also offers some higher-level APIs for constructing and executing TensorFlow graphs. These are available in several programming languages, but the most commonly used implementation is the **TensorFlow Python API**. The **tf-dotty** library is a layer that provides the same API as the Python API, but adds type safety by statically checking tensor types and shapes. It calls the Python implementation by using a **ScalaPy TensorFlow facade**.

## Why call TensorFlow Python instead of libtensorflow?

The TensorFlow maintainers' [recommended approach](https://github.com/tensorflow/docs/blob/master/site/en/r1/guide/extend/bindings.md) for writing a TensorFlow API is to use the host language's foreign function interface (FFI) to call libtensorflow's C API. On the Java Virtual Machine (JVM), this would mean using either Java Native Interface (JNI) or Java Native Access (JNA). This is the approach taken by [tensorflow_scala](https://github.com/eaplatanios/tensorflow_scala), which targets Scala 2 and uses JNI to call libtensorflow.

Tf-dotty does not currently follow the recommended approach, mainly in order to avoid re-implementing translation of high-level API calls to low-level FFI calls. Instead, calling the TensorFlow Python API makes it possible to delegate the work of communicating with libtensorflow to CPython, the Python interpreter. CPython is written in C and can therefore efficiently call a C++ library such as libtensorflow. Python was originally designed as ["glue" between components](https://www.python.org/doc/essays/omg-darpa-mcc-position/), and serves that purpose in tf-dotty.

## How to call Python from Dotty

The [ScalaPy library](https://github.com/shadaj/scalapy) makes it possible to call Python code from Scala by using JNA to bind to the CPython interpreter. Additionally, it provides an interface for defining static type "facades" to Python libraries, similarly to [Scala.js facades](https://www.scala-js.org/libraries/facades.html), which type JavaScript libraries in Scala.js.

The ScalaPy interface to write facades makes heavy use of Scala 2 macros, which are [not supported by Dotty](https://dotty.epfl.ch/docs/reference/dropped-features/macros.html). However, Dotty-compiled code [can call Scala 2.13 dependencies](https://www.scala-lang.org/2019/12/18/road-to-scala-3.html), and ScalaPy cross-compiles to 2.13 since the [shadaj/scalapy#46](https://github.com/shadaj/scalapy/pull/46) PR.
