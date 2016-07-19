organization := "eu.akkamo"

name := "akkamo-demo"

version := "1.0.0-SNAPSHOT"

description := "Demo application for the Akkamo platform"

scalaVersion in Global := "2.11.8"

cancelable in Global := true

fork in(IntegrationTest, run) := false

lazy val akkamoVersion = "1.0.0"

lazy val akkamoDemo = project.in(file(".")).dependsOn(pingAkkamo, pongAkkamo, httpAkkamo).settings(
	name := "akkamo-demo",
	mainClass in Compile := Some("eu.akkamo.Main")
).enablePlugins(JavaAppPackaging, AkkamoSbtPlugin)

lazy val pingAkkamo = project.in(file("pingAkkamo")).settings(
	name := "akkamo-ping",
	libraryDependencies ++= Seq(
		"eu.akkamo" %% "akkamo" % akkamoVersion,
		"com.typesafe.akka" %% "akka-cluster-tools" % "2.4.4"
	)
).dependsOn(pongAkkamo)

lazy val pongAkkamo = project.in(file("pongAkkamo")).settings(
	name := "akkamo-pong",
	libraryDependencies ++= Seq(
		"eu.akkamo" %% "akkamo" % akkamoVersion,
		"com.typesafe.akka" %% "akka-cluster-tools" % "2.4.4"
	)
)

lazy val httpAkkamo = project.in(file("httpAkkamo")).settings(
	name := "akkamo-http",
	libraryDependencies ++= Seq(
		"eu.akkamo" %% "akkamo-akka-http" % akkamoVersion,
		"org.scalaz" %% "scalaz-core" % "7.2.2"
	)
)
