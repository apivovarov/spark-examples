// ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := false

// parallelExecution in Test := false

assemblyJarName in assembly := "spark-examples.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  val jars = Set(
    "activation-1.1.jar",
    "aws-java-sdk-1.8.9.1.jar",
    "commons-beanutils-1.8.3.jar",
    "commons-codec-1.6.jar",
    "commons-codec-1.10.jar",
    "commons-digester-1.8.jar",
    "commons-io-2.4.jar",
    "commons-lang-2.5.jar",
    "commons-lang3-3.3.2.jar",
    "commons-logging-1.1.1.jar",
    "jackson-annotations-2.4.4.jar",
    "jackson-core-2.4.4.jar",
    "jackson-coreutils-1.6.jar",
    "jackson-databind-2.4.4.jar",
    "jsr305-2.0.1.jar",
    "libthrift-0.9.0.jar",
    "log4j-1.2.17.jar",
    "mailapi-1.4.3.jar",
    "rhino-1.7R4.jar",
    "slf4j-api-1.7.10.jar",
    "validation-api-1.1.0.Final.jar"
  )
  cp.filter(f => jars.contains(f.data.getName))
}

test in assembly := {}

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}
