organization := "com.github.jurajburian"

name := "makka-demo"

version := "0.1.0"

description := "Demo application for the Makka platform"

scalaVersion in Global := "2.11.8"

//cancelable in Global := true

lazy val root = project.in(file(".")).dependsOn(pingMakka, pongMakka, shared).settings(
	mainClass in (Compile, run) := Some("com.github.jurajburian.makka.Main")
)

lazy val pingMakka = project.in(file("pingMakka")).settings(
	name := "makka-ping",
	libraryDependencies ++= Seq(
		"com.github.jurajburian" %% "makka" % "1.0.0",
		"org.scalaz" %% "scalaz-core" % "7.2.2"
	)
).dependsOn("shared")

lazy val pongMakka = project.in(file("pongMakka")).settings(
	name := "makka-pong",
	libraryDependencies ++= Seq(
		"com.github.jurajburian" %% "makka" % "1.0.0",
		"org.scalaz" %% "scalaz-core" % "7.2.2"
	)
).dependsOn("shared")

lazy val shared = project.in(file("shared")).settings(
	name := "shared"
)

