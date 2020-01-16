import scala.sys.process._

val dottyVersion = "0.22.0-bin-20200110-842e4c4-NIGHTLY"

val scala213Version = "2.13.1"

lazy val tensorflow = project
  .in(file("modules/tensorflow"))
  .dependsOn(scalapyTensorflow)
  .settings(
    name := "tf-dotty",
    version := "0.1.0",
    organization := "io.kjaer",
    scalaVersion := dottyVersion,

    // Tests:
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-s", "-v"),

    // ScalaPy:
    libraryDependencies += "me.shadaj" % "scalapy-core_2.13" % "0.3.0+15-598682f0",
    fork := true,
    javaOptions += s"-Djna.library.path=${"python3-config --prefix".!!.trim}/lib",
    
    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )

lazy val scalapyTensorflow = project
  .in(file("modules/scalapy-tensorflow"))
  .settings(
    name := "scalapy-tensorflow",
    scalaVersion := scala213Version,
    organization := "me.shadaj",

    // ScalaPy:
    libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.3.0+15-598682f0",
    libraryDependencies += "me.shadaj" %% "scalapy-numpy" % "0.1.0+5-ad550211",

    // Tests:
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,

    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )
