---
id: contributing
title: Contributing
---

### Documentation

The documentation website is built by [Docusaurus](https://docusaurus.io/docs/en/installation). The code for the website is in the `website` directory. To change the documentation, edit the files in `docs`, and serve the site by running:

```console
$ cd website
$ npm start
```

The docs are not a part of the sbt build. When [mdoc](https://scalameta.org/mdoc) supports Dotty/Scala 3, the project will switch to using mdoc.