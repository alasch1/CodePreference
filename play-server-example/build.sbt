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
  javaWs,
  javaJpa  
)

mappings in Universal ++= directory("scripts")

val lombokVersion = "1.16.6"
val log4jVersion = "2.4.1"
val codepreferenceVersion = "1.0.0-SNAPSHOT"

libraryDependencies ++= Seq(
		"org.projectlombok" % "lombok" % lombokVersion,
 		"org.apache.logging.log4j" % "log4j-core" % log4jVersion,
        "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
		"com.alasch1.codepreference.log4j2examples" % "codepref-log4j2" % codepreferenceVersion
)