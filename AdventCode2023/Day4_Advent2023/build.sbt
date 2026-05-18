ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.2"

lazy val root = (project in file("."))
  .settings(
    name := "Day4"
  )

libraryDependencies += "org.scalameta" %% "munit" % "1.0.0-M11" % Test
