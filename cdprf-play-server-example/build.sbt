import NativePackagerHelper._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

name := """play-server-example"""

version := "1.0-SNAPSHOT"

// LauncherJarPlugin serves to overcome long classpath problem
lazy val root = (project in file(".")).enablePlugins(PlayJava, LauncherJarPlugin)

//lazy val root = (project in file(".")).enablePlugins(PlayScala, LauncherJarPlugin)

scalaVersion := "2.11.8"
//scalaVersion := "2.11.7"

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
val log4jVersion = "2.7"
val cdprfVersion = "1.0.0-SNAPSHOT"
val hibernateVersion = "4.3.10.Final"
val commonsIOVersion = "2.4"

libraryDependencies ++= Seq(
		"org.projectlombok" % "lombok" % lombokVersion,
 		"org.apache.logging.log4j" % "log4j-core" % log4jVersion,
        "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
 		"org.hibernate" % "hibernate-entitymanager" % hibernateVersion,
 		"commons-io" % "commons-io" % commonsIOVersion,
		"com.alasch1.cdprf.examples" % "cdprf-log4j2-examples" % cdprfVersion
)
