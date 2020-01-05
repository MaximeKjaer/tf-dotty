---
id: contributing
title: Contributing
---

## Further work

tf-dotty is still in experimental stages, and some areas topics could be explored further:

- **Keras**: tf-dotty currently only supports TensorFlow Core, but most developers use Keras in their day-to-day; tf-dotty could also provide a shape-safe Keras interface.
- **TensorFlow 2.0**: TensorFlow recently released version 2.0, but tf-dotty uses 1.14.
- **Negative indices**: Python lists have support for negative indices, which count from the end. Many data scientists are used to these, and supporting them would help port Python code to tf-dotty.
- **API support**: TensorFlow's API is quite large, and many areas are still unsupported. Some parts of the API are straightforward forwarding to the Python implementation, but there is opportunity for stricter type checks in others.
- **Broadcasting**: tf-dotty does not support broadcasting. Some people consider broadcasting a bug, and others consider it a feature they couldn't live without. Ideally, support for broadcasting would be opt-in, perhaps through implicit conversions.
- **Labeled axes**: The standard TensorFlow API does not support labeled axes, but instead takes a list of indices for reduction operations. There have been [arguments against this API](http://nlp.seas.harvard.edu/NamedTensor). Could tf-dotty support labeled axes at the type level, and materialize the correct indices?

## Guidelines

- Do not expose deprecated arguments
- Encode default arguments in Scala, and pass them explicitly to the Python implementation.
- You can remove documentation about the types of the arguments if they are encoded in the type system instead

## Compiling and testing

See how to install Python dependencies in the [Getting Started](getting-started.md) page. Compile and test using sbt:

```console
$ workon tf-dotty  # optional: activate tf-dotty Python virtualenv
$ sbt
> compile
> test
```

## Documentation

The documentation website is built by [Docusaurus](https://docusaurus.io/docs/en/installation). The code for the website is in the `website` directory. To change the documentation, edit the files in `docs`, and serve the site by running:

```console
$ cd website
$ npm start
```

The docs are not a part of the sbt build for now. The project may use [mdoc](https://scalameta.org/mdoc) or [Dottydoc](https://dotty.epfl.ch/docs/usage/dottydoc.html) down the road.
