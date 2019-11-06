val dottyVersion = "0.20.0-RC1"
val scala213Version = "2.13.1"

lazy val tensorflow = project
  .in(file("modules/tensorflow"))
  .dependsOn(scalapyTensorflow)
  .settings(
    name := "tf-dotty",
    version := "0.1.0",
    organization := "ch.epfl",
    scalaVersion := dottyVersion,

    libraryDependencies += ("me.shadaj" %% "scalapy-core" % "0.3.0+1-35dca37d").withDottyCompat(dottyVersion),
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",

    fork := true,
    javaOptions += s"-Djava.library.path=${sys.env.getOrElse("JEP_PATH", "/home/maxime/.virtualenvs/tf-dotty/lib/python3.7/site-packages/jep")}",
    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )

lazy val scalapyTensorflow = project
  .in(file("modules/scalapy-tensorflow"))
  .settings(
    name := "scalapy-tensorflow",
    scalaVersion := scala213Version,
    organization := "me.shadaj",

    // ScalaPy:
    // Available by running `sbt publishLocal` from https://github.com/MaximeKjaer/scalapy/tree/port-jna-2.13
    // libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.3.0+6-73fec340",
    // Available by running `sbt publishLocal` from https://github.com/MaximeKjaer/scalapy/tree/port-2.13
    libraryDependencies += ("me.shadaj" %% "scalapy-core" % "0.3.0+1-35dca37d").withDottyCompat(dottyVersion),
    libraryDependencies += ("me.shadaj" %% "scalapy-numpy" % "0.1.0+3-046d1d67+20191023-1508").withDottyCompat(dottyVersion),

    // Tests:
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
  )
