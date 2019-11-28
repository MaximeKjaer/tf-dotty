# Tensorflow Dotty

[![Build Status](https://travis-ci.com/MaximeKjaer/tf-dotty.svg?token=soqG4sgcMQUgpCtPSUUr&branch=master)](https://travis-ci.com/MaximeKjaer/tf-dotty)

## Dependencies

This project communicates with the Python implementation of TensorFlow. Therefore, you will need to have Python 3.7 and pip installed. One of the installed dependencies, `jep`, needs to have the `JAVA_HOME` environment variable set, so we'll start there. I had to set:

```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

This setup is a little finnicky with version numbers: we need TensorFlow 1.14.0 and jep 3.8.2. To get something as close to my environment, you can start a Python virtualenv by running:

```
mkvirtualenv -p python3.7 -r requirements.txt tf-dotty
```

## Usage

This is a normal sbt project, you can compile code with `sbt compile` and run it
with `sbt run`, `sbt console` will start a Dotty REPL.

For more information on the sbt-dotty plugin, see the
[dotty-example-project](https://github.com/lampepfl/dotty-example-project/blob/master/README.md).
