# Tensorflow Dotty

[![Build Status](https://travis-ci.com/MaximeKjaer/tf-dotty.svg?token=soqG4sgcMQUgpCtPSUUr&branch=master)](https://travis-ci.com/MaximeKjaer/tf-dotty)

## Dependencies

This project communicates with the Python implementation of TensorFlow. Therefore, you will need to have Python 3.7 and pip installed. Therefore, you will need to have Python 3.7 and pip installed. To install the dependencies, run:

```console
$ pip install -r requirements.txt
```

You can also choose to do this in a virtualenv with virtualenvwrapper:

```console
$ mkvirtualenv -p python3.7 -r requirements.txt tf-dotty
```

## Usage

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL.

For more information on the sbt-dotty plugin, see the
[dotty-example-project](https://github.com/lampepfl/dotty-example-project/blob/master/README.md).
