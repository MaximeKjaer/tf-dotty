import scala.sys.process._

val dottyVersion = "0.19.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tf-dotty",
    version := "0.1.0",
    scalaVersion := dottyVersion,

    // ScalaPy:
    // Available by running `sbt publishLocal` from https://github.com/MaximeKjaer/scalapy/tree/port-jna-2.13
    // libraryDependencies += ("me.shadaj" %% "scalapy-core" % "0.3.0+6-73fec340").withDottyCompat(dottyVersion),
    // Available by running `sbt publishLocal` from https://github.com/MaximeKjaer/scalapy/tree/port-2.13
    libraryDependencies += ("me.shadaj" %% "scalapy-core" % "0.3.0+1-35dca37d").withDottyCompat(dottyVersion),
    libraryDependencies += ("me.shadaj" %% "scalapy-tensorflow" % "0.1.0+2-41e09e30+20191023-1510").withDottyCompat(dottyVersion),

    fork := true,
    // Get this value by running "pip show jep"
    javaOptions in Test += s"-Djava.library.path=/home/maxime/.local/lib/python3.7/site-packages/jep",

    // JUnit:
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
