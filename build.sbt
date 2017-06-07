organization := "eu.akkamo"

name := "akkamo-demo"

version := "1.0.0-SNAPSHOT"

description := "Demo application for the Akkamo platform"


val cScalaVersion = "2.12.2"
val cAkkaVersion = "2.4.17"
val cAkkaHttpVersion = "10.0.5"
lazy val cAkkamoVersion = "1.1.0"

scalaVersion in Global := cScalaVersion

cancelable in Global := true

fork in(IntegrationTest, run) := false

lazy val akkamoDemo = project.in(file(".")).dependsOn(pingAkkamo, pongAkkamo, httpAkkamo).settings(
	name := "akkamo-demo",
	mainClass in Compile := Some("eu.akkamo.Main"),
  libraryDependencies ++= Seq(
    "eu.akkamo" %% "akkamo-akka-log" % cAkkamoVersion
  )
).enablePlugins(JavaAppPackaging, AkkamoSbtPlugin)

lazy val pingAkkamo = project.in(file("pingAkkamo")).settings(
	name := "akkamo-ping",
	libraryDependencies ++= Seq(
		"eu.akkamo" %% "akkamo" % cAkkamoVersion,
    "eu.akkamo" %% "akkamo-akka" % cAkkamoVersion,
		"com.typesafe.akka" %% "akka-actor" % cAkkaVersion,
		"com.typesafe.akka" %% "akka-cluster-tools" % cAkkaVersion
	)
).dependsOn(pongAkkamo)

lazy val pongAkkamo = project.in(file("pongAkkamo")).settings(
	name := "akkamo-pong",
	libraryDependencies ++= Seq(
		"eu.akkamo" %% "akkamo" % cAkkamoVersion,
    "eu.akkamo" %% "akkamo-akka" % cAkkamoVersion,
		"com.typesafe.akka" %% "akka-actor" % cAkkaVersion,
		"com.typesafe.akka" %% "akka-cluster-tools" % cAkkaVersion
	)
)

lazy val httpAkkamo = project.in(file("httpAkkamo")).settings(
	name := "akkamo-http",
	libraryDependencies ++= Seq(
		"com.typesafe.akka" %% "akka-http" % cAkkaHttpVersion,
		"eu.akkamo" %% "akkamo-akka-http" % cAkkamoVersion,
		"eu.akkamo" %% "akkamo-web-content" % cAkkamoVersion
	)
)
