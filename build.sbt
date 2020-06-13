import scala.sys.process._

val dottyVersion = "0.25.0-RC1"
val scala213Version = "2.13.2"

inThisBuild(List(
  organization := "io.kjaer",
  homepage := Some(url("https://github.com/maximekjaer/tf-dotty")),
  licenses := List("MIT" -> url("https://github.com/MaximeKjaer/tf-dotty/blob/master/LICENSE")),
  developers := List(
    Developer(
      "maximekjaer",
      "Maxime Kjaer",
      "maxime.kjaer@gmail.com",
      url("https://kjaer.io")
    )
  )
))

lazy val root = project
  .in(file("."))
  .aggregate(tensorflow)
  .settings(
    name := "tf-dotty",
    scalaVersion := dottyVersion
  )

lazy val tensorflow = project
  .in(file("modules/tensorflow"))
  .dependsOn(`scalapy-tensorflow`)
  .settings(
    organization := "io.kjaer",
    scalaVersion := dottyVersion,

    // Tests:
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.9",
    testFrameworks += new TestFramework("munit.Framework"),

    // ScalaPy:
    libraryDependencies += "me.shadaj" % "scalapy-core_2.13" % "0.3.0+15-598682f0",
    fork := true,
    javaOptions += s"-Djna.library.path=${"python3-config --prefix".!!.trim}/lib",
    
    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )

lazy val `scalapy-tensorflow` = project
  .in(file("modules/scalapy-tensorflow"))
  .settings(
    scalaVersion := scala213Version,
    organization := "me.shadaj",
    skip in publish := true,

    // ScalaPy:
    libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.3.0+15-598682f0",
    libraryDependencies += "me.shadaj" %% "scalapy-numpy" % "0.1.0+5-ad550211",

    // Tests:
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,

    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )
