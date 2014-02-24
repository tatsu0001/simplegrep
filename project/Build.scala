import sbt._
import Keys._

object SimpleGrepBuild extends Build {
  val buildSettings = Seq(
    version := "0.1",
    scalaVersion := "2.10.3",
    scalacOptions ++= Seq(
      "-Xlint", "-feature"))

  val resolverSettings = Seq(
    resolvers += Resolver.sonatypeRepo("public")
  )

  val libraryDependsSettings = Seq(
    libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"
  )

  val simplegrep = 
    Project("simplegrep", file("."))
      .settings(buildSettings: _*)
      .settings(resolverSettings: _*)
      .settings(libraryDependsSettings: _*)
}

