import bintray.Keys._

lazy val root = (project in file(".")).
  settings(bintrayPublishSettings).
  settings(
    name := "sdk_scala",
    version := "0.5.0",
    description := "The official Recast.AI Scala SDK",
    organization := "ai.recast",
    publishMavenStyle := true,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    repository in bintray := "generic",
    bintrayOrganization in bintray := None,
    libraryDependencies ++= Seq(
      "org.scalaj" %% "scalaj-http" % "2.3.0",
      "play" % "play_2.10" % "2.1.0"
    )
  )
