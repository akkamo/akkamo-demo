organization := "com.github.jurajburian"

name := "makka-demo"

version := "0.1.0"

description := "Demo application for the Makka platform"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
	"com.github.jurajburian" %% "makka" % "1.0.0"
)

// boot Makka
mainClass in (Compile, run) := Some("com.github.jurajburian.makka.Main")
