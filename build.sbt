name := "scala-sbt-shading"

version := "1.0"

scalaVersion := "2.12.6"

val elasticsearch6V = "6.2.10"
val elasticsearchV = "5.4.17"
val specs2V = "4.0.2"

lazy val shadedElasticAssemblySettings = Seq(
  //logLevel in assembly := Level.Debug,
  test in assembly := {},
  assemblyOption in assembly ~= {
    _.copy(includeScala = false)
  },
  assemblyJarName in assembly := {
    s"${name.value}_${scalaBinaryVersion.value}-${version.value}.jar"
  },
  assemblyJarName in (Test, assembly) := {
    s"${name.value}_${scalaBinaryVersion.value}-${version.value}-test.jar"
  },
  assemblyShadeRules in assembly := Seq(
    ShadeRule
      .rename("com.sksamuel.elastic4s.**" -> "elastic4s6.@0").inAll
  ),
  libraryDependencies ++= Seq(
    "com.sksamuel.elastic4s" %% "elastic4s-core" % elasticsearch6V,
    "com.sksamuel.elastic4s" %% "elastic4s-http" % elasticsearch6V,
    "com.sksamuel.elastic4s" %% "elastic4s-circe" % elasticsearch6V,
    "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elasticsearch6V % "test",
    "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elasticsearch6V % "test",
  ),
  target in assembly := baseDirectory.value / "target" / scalaBinaryVersion.value,

  assemblyMergeStrategy in (Test, assembly) := {
    case p if p.endsWith("io.netty.versions.properties") =>
      MergeStrategy.first
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

lazy val elastic4s6 = Project(
  id = "elastic4s6",
  base = file("elastic4s6")
)
  .settings(inConfig(Test)(baseAssemblySettings): _*)
  .settings(inConfig(Test)(shadedElasticAssemblySettings): _*)
  .settings(shadedElasticAssemblySettings ++ addArtifact(artifact in (Compile, assembly), sbtassembly.AssemblyKeys.assembly))
  .enablePlugins(AssemblyPlugin)

lazy val rootSettings = Seq(
  compile := ((compile in Compile) dependsOn (elastic4s6 / assembly)).value,
  unmanagedJars in Compile ++= Seq(
    (target in (elastic4s6, assembly)).value / (assemblyJarName in (elastic4s6, assembly)).value,
  ),
  compile in Test := ((compile in Test) dependsOn (assembly in Test in elastic4s6)).value,
  unmanagedJars in Test ++= Seq(
    (target in (elastic4s6, assembly)).value / (assemblyJarName in assembly in (elastic4s6, Test)).value,
  ),

  libraryDependencies ++= Seq(
    "com.sksamuel.elastic4s" %% "elastic4s-core" % elasticsearchV,
    "com.sksamuel.elastic4s" %% "elastic4s-http" % elasticsearchV,
    "com.sksamuel.elastic4s" %% "elastic4s-circe" % elasticsearchV,
    "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elasticsearchV % "test",
    "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elasticsearchV % "test",

    "org.specs2" %% "specs2-core" % specs2V % "test",
  ),

)

lazy val root = Project(
  id = "root",
  base = file(".")
).settings(rootSettings)
  //.disablePlugins(sbtassembly.AssemblyPlugin)
  .dependsOn(elastic4s6)