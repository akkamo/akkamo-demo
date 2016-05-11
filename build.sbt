organization := "com.github.jurajburian"

name := "makka-demo"

version := "0.1.0"

description := "Demo application for the Makka platform"

scalaVersion in Global := "2.11.8"

cancelable in Global := true

fork in(IntegrationTest, run) := true

Revolver.settings

lazy val makkaDemo = project.in(file(".")).dependsOn(pingMakka, pongMakka, httpMakka).settings(
	name := "makka-demo"
	//mainClass in Compile := Some("com.github.jurajburian.makka.Makka")
).enablePlugins(JavaAppPackaging, MakkaSbtPlugin)

lazy val pingMakka = project.in(file("pingMakka")).settings(
	name := "makka-ping",
	libraryDependencies ++= Seq(
		"com.github.jurajburian" %% "makka" % "1.0.0",
		"com.typesafe.akka" %% "akka-cluster-tools" % "2.4.4"
	)
).dependsOn(pongMakka)

lazy val pongMakka = project.in(file("pongMakka")).settings(
	name := "makka-pong",
	libraryDependencies ++= Seq(
		"com.github.jurajburian" %% "makka" % "1.0.0",
		"com.typesafe.akka" %% "akka-cluster-tools" % "2.4.4"
	)
)

lazy val httpMakka = project.in(file("httpMakka")).settings(
	name := "makka-http",
	libraryDependencies ++= Seq(
		"com.github.jurajburian" %% "makka-akka-http" % "1.0.0",
		"org.scalaz" %% "scalaz-core" % "7.2.2"
	)
)
