# tf-dotty

[![Build Status](https://travis-ci.com/MaximeKjaer/tf-dotty.svg?branch=master)](https://travis-ci.com/MaximeKjaer/tf-dotty)

tf-dotty is a [Dotty](https://github.com/lampepfl/dotty) library that adds type safety to TensorFlow code by statically checking tensor types and shape.

## Dependencies

This project communicates with the Python implementation of TensorFlow. Therefore, you will need to have Python 3.7 and pip installed. To install the dependencies, run:

```console
$ pip install -r requirements.txt
```

You can also choose to do this in a virtualenv with virtualenvwrapper:

```console
$ mkvirtualenv -p python3.7 -r requirements.txt tf-dotty
```
