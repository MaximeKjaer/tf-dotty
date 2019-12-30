/** This version is compiled from this fork of dotty:
 *  https://github.com/MaximeKjaer/dotty/tree/singleton-arithmetic
 *  It has been published locally with `sbt dotty-bootstrapped/publishLocal`.
 */
val dottyVersion = "0.21.0-bin-SNAPSHOT"

val scala213Version = "2.13.1"

lazy val tensorflow = project
  .in(file("modules/tensorflow"))
  .dependsOn(scalapyCore, scalapyTensorflow)
  .settings(
    name := "tf-dotty",
    version := "0.1.0",
    organization := "ch.epfl",
    scalaVersion := dottyVersion,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-s", "-v"),

    fork := true,
    javaOptions += s"-Djava.library.path=${sys.env.getOrElse("JEP_PATH", "/home/maxime/.virtualenvs/tf-dotty/lib/python3.7/site-packages/jep")}",
    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )

lazy val scalapyTensorflow = project
  .in(file("modules/scalapy-tensorflow"))
  .dependsOn(scalapyCore, scalapyNumpy)
  .settings(
    name := "scalapy-tensorflow",
    scalaVersion := scala213Version,
    organization := "me.shadaj",

    // Tests:
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,

    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )

lazy val scalapyCore = ProjectRef(uri("git://github.com/MaximeKjaer/scalapy#port-2.13"), "coreJVM")
lazy val scalapyNumpy = ProjectRef(uri("git://github.com/MaximeKjaer/scalapy-numpy#port-2.13"), "scalaPyNumpyCrossJVM")
