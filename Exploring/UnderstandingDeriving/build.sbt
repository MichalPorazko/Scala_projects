ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.2"

lazy val root = (project in file("."))
  .settings(
    name := "UnderstandingDeriving"
  )

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"

