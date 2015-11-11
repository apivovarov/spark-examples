lazy val appSettings = Seq(
  organization := "org.x4444",
  name := "spark-examples",
  version := "1.0.0-SNAPSHOT"
)

// Those settings should be the same as in alchemy!
lazy val scalaVersion_ = "2.10.4" // should be the same as spark
lazy val javaVersion = "1.7" // should be the same as spark
lazy val sparkVersion = "1.4.1"

lazy val scalaCheckVersion = "1.12.2"
lazy val scalaTestVersion = "2.2.4"

scalaVersion in Global := scalaVersion_

scalacOptions in Global ++= Seq(
  "-deprecation",
  "-feature",
  "-target:jvm-" + javaVersion,
  "-Xlint"
)

javaOptions in Global ++= Seq(
  "-XX:MaxPermSize=256m"
)

javacOptions in Global ++= Seq(
  "-encoding", "UTF-8",
  "-source", javaVersion,
  "-target", javaVersion
)

lazy val sparkLib = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion withSources() withJavadoc(),
  "org.apache.spark" %% "spark-mllib" % sparkVersion withSources() withJavadoc(),
  "org.apache.spark" %% "spark-graphx" % sparkVersion withSources() withJavadoc(),
  "org.apache.spark" %% "spark-sql" % sparkVersion withSources() withJavadoc(),
  "org.apache.spark" %% "spark-hive" % sparkVersion withSources() withJavadoc()
)

lazy val auxLib = Seq(
  "commons-codec" % "commons-codec" % "1.10",
  "org.apache.commons" % "commons-lang3" % "3.3.2" withSources(),
  // AWS
  "com.amazonaws" % "aws-java-sdk" % "1.8.9.1" withSources(),
  // parsing
  "org.json4s" %% "json4s-ext" % "3.2.10",
  "net.sf.supercsv" % "super-csv" % "2.2.0" withSources(),
  // validators
  "com.github.fge" % "json-schema-validator" % "2.2.0" withSources(),
  "commons-validator" % "commons-validator" % "1.4.0" withSources(),
  // utils
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2" withSources(),
  "com.github.nscala-time" %% "nscala-time" % "1.6.0" withSources(),
  "org.slf4j" % "slf4j-api" % "1.7.10" withSources()
)

lazy val testLib = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % scalaCheckVersion % Test withSources() withJavadoc()
)

//removes _2.10 auto suffix in artifact name
crossPaths in Global := false

credentials in Global += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle in Global := true

net.virtualvoid.sbt.graph.Plugin.graphSettings

lazy val root = (project in file("."))
  .settings(appSettings: _*)
  .settings(
    libraryDependencies ++= sparkLib,
    libraryDependencies ++= auxLib,
    libraryDependencies ++= testLib,
    dependencyOverrides ++= Set(
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.4.4" % Provided, // to avoid mix of jackson jars versions
      "com.fasterxml.jackson.core" % "jackson-core" % "2.4.4" % Provided,
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4" % Provided
    )
  )
  .settings(addArtifact(artifact in (Compile, assembly), assembly).settings: _*)

fork in Global := true


