import Dependencies._
import com.typesafe.sbt.packager.docker._

ThisBuild / scalaVersion     := "2.13.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "cchalc"

val nixDockerSettings = List(
  name := "sbt-nix-getprogramming",
  dockerCommands := Seq(
    Cmd("FROM", "base-jre:latest"),
    Cmd("COPY", "1/opt/docker/lib/*.jar", "/lib/"),
    Cmd("COPY", "2/opt/docker/lib/*.jar", "/app.jar"),
    ExecCmd("ENTRYPOINT", "java", "-cp", "/app.jar:/lib/*", "cchalc.getprogramming.Hello")
  )
)

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")),
    libraryDependencies ++= Seq(
      catsCore,
      scalaTest % Test
    )
  )
  .settings(nixDockerSettings: _*)
