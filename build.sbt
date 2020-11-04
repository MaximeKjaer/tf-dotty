import scala.sys.process._


val dottyVersion = "3.0.0-M1"
val scala213Version = "2.13.2"

val munitVersion = "0.7.16"
val scalapyVersion = "0.3.0+15-598682f0"
val scalapyNumpyVersion = "0.1.0+5-ad550211"

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

lazy val tensorflow = project
  .in(file("modules/tensorflow"))
  .dependsOn(`scalapy-tensorflow`, compiletime)
  .settings(
    organization := "io.kjaer",
    name := "tf-dotty",
    scalaVersion := dottyVersion,

    // Tests:
    libraryDependencies += "org.scalameta" %% "munit" % munitVersion % Test,
    testFrameworks += new TestFramework("munit.Framework"),

    // ScalaPy:
    libraryDependencies += "me.shadaj" % "scalapy-core_2.13" % scalapyVersion,
    fork := true,
    javaOptions += s"-Djna.library.path=${"python3-config --prefix".!!.trim}/lib",

    projectDependencies ~= (_.map {
      case projectDep =>
        if (projectDep.organization == "io.kjaer") projectDep
        else projectDep.withDottyCompat(dottyVersion)
    }),
  )

lazy val compiletime = project
  .in(file("modules/compiletime"))
  .settings(
    organization := "io.kjaer",
    name := "tf-dotty-compiletime",
    scalaVersion := dottyVersion,

    libraryDependencies += "org.scalameta" %% "munit" % munitVersion % Test,
    testFrameworks += new TestFramework("munit.Framework"),
  )

lazy val `scalapy-tensorflow` = project
  .in(file("modules/scalapy-tensorflow"))
  .settings(
    scalaVersion := scala213Version,
    organization := "me.shadaj",
    skip in publish := true,

    // ScalaPy:
    libraryDependencies += "me.shadaj" %% "scalapy-core" % scalapyVersion,
    libraryDependencies += "me.shadaj" %% "scalapy-numpy" % scalapyNumpyVersion,

    // Tests:
    libraryDependencies += "org.scalameta" %% "munit" % munitVersion % Test,
    testFrameworks += new TestFramework("munit.Framework"),

    projectDependencies ~=(_.map(_.withDottyCompat(dottyVersion))),
  )
