# Notes and questions

## Wrong implicit conversion to double

The ScalaPy TensorFlow facade has the following bit of code in `package.scala`:

```scala
package object tensorflow {
  implicit def double2Tensor(d: Double): Tensor = {
    py.Any.from[Double](d)(Writer.doubleWriter).as[Tensor]
  }
}
```

I wrote:

```scala
def reduce_mean(t: Tensor, axis: Int): Tensor =
    (as[py.Dynamic].reduce_mean(t, axis)).as[Tensor]
```

When running this, TensorFlow complains that `axis` was a float and not an int. Running with `scalacOptions += "-Xlog-implicit-conversions"` gives me the following insight:

```scala
inferred view from Int to me.shadaj.scalapy.py.Any via tensorflow.this.`package`.double2Tensor: (d: Double)me.shadaj.scalapy.tensorflow.Tensor
[info]     (as[py.Dynamic].reduce_mean(t, axis2)).as[Tensor]
```

It turns out that this writes the `axis` as a `Double`, and not as an `Int`, which makes TensorFlow complain. It turns out that the following type-checks fine:

```scala
val x: Int = 1
double2Tensor(x)
```

Investigating further with `-Xprint:typer`, we see that this [implicit conversion](https://github.com/scala/scala/blob/2.13.x/src/library/scala/Int.scala#L483) is being prioritized (I've edited the output to make it slightly more legible). Note that `axis.toDouble` has actually been inlined here:

```scala
def reduce_mean(t: Tensor, axis: Int): Tensor = TensorFlow.this.as[py.Dynamic](py.this.Reader.facadeReader[py.Dynamic]({
  final class $anon extends py.FacadeCreator[py.Dynamic] {
    def <init>(): <$anon: py.FacadeCreator[py.Dynamic]> = {
      $anon.super.<init>();
      ()
    };
    def create(value: py.PyValue): py.FacadeValueProvider with py.Dynamic = {
      final class $anon extends _root_.py.FacadeValueProvider with py.Dynamic {
        def <init>(): <$anon: py.FacadeValueProvider with py.Dynamic> = {
          $anon.super.<init>(value);
          ()
        }
      };
      new $anon()
    }
  };
  new $anon()
})).applyDynamic("reduce_mean")(t, tensorflow.this.`package`.double2Tensor(axis.toDouble)).as[Tensor](py.this.Reader.facadeReader[Tensor]({
      final class $anon extends py.FacadeCreator[Tensor] {
        def <init>(): <$anon: py.FacadeCreator[Tensor]> = {
          $anon.super.<init>();
          ()
        };
        def create(value: py.PyValue): py.FacadeValueProvider with Tensor = {
          final class $anon extends _root_.py.FacadeValueProvider with Tensor {
            def <init>(): <$anon: py.FacadeValueProvider with Tensor> = {
              $anon.super.<init>(value);
              ()
            }
          };
          new $anon()
        }
      };
      new $anon()
    }));
```

This [StackOverflow answer](https://stackoverflow.com/a/38967057) was useful in solving this. The solution is to remove the implicit conversion `double2Tensor`.
