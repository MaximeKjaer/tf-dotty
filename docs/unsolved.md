# Unsolved

## Content types

We have a Scala -> TF conversion through `TFEncoding`, and TF -> Scala through `DataType`.

Many functions take a Scala type `T`, but can also take a `DataType`. If you don't specify anything, `T` is `Float` and `dtype` is `tf.float32`. If you specify one, you must also specify the other.
