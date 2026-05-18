ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "Futures"
  )

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client3" %% "core" % "3.9.7",
  "org.playframework" %% "play-json" % "3.0.3",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % "3.9.7",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test
)

