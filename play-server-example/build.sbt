import NativePackagerHelper._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

name := """play-server-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

//scalaVersion := "2.11.8"
scalaVersion := "2.11.7"

resolvers += "Local Maven Repository" at "file:///"+Path.userHome.absolutePath+"/.m2/repository"

EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

mappings in Universal ++= directory("scripts")


