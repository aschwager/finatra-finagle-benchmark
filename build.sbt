lazy val buildSettings = Seq(
  scalaVersion := versions.scala,
  version := "0.0.1-SNAPSHOT"
)

val baseSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-compiler" % versions.scala,
    "com.twitter" %% "util-core" % versions.twitterUtil,
    "joda-time" % "joda-time" % versions.joda
  ),
  resolvers ++= Seq(
    Resolver.mavenLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    "Twitter" at "http://maven.twttr.com/",
    "Finatra Repo" at "http://twitter.github.com/finatra",
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )
)

lazy val commonSettings = buildSettings ++ baseSettings

lazy val versions = new {
  val scala = "2.11.7"
  val finatra = "2.1.1"
  val finagle = "6.30.0"
  val twitterUtil = "6.29.0"
  val joda = "2.9.1"
  val jackson = "2.6.3"
}

lazy val common = project
  .in(file("common"))
  .settings(commonSettings)
  .settings(
    name := "common"
  )

lazy val finatra = project
  .in(file("finatra"))
  .enablePlugins(JavaAppPackaging)
  .settings(commonSettings)
  .settings(
    name := "finatra",
    libraryDependencies ++= Seq(
      "com.twitter.finatra" %% "finatra-http" % versions.finatra,
      "com.twitter.finatra" %% "finatra-httpclient" % versions.finatra
    )
  )
  .dependsOn(common)

lazy val finagle = project
  .in(file("finagle"))
  .enablePlugins(JavaAppPackaging)
  .settings(commonSettings)
  .settings(
    name := "finagle",
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-core" % versions.finagle,
      "com.twitter" %% "finagle-http" % versions.finagle,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % versions.jackson
    )
  )
  .dependsOn(common)

//mainClass in Compile := Some("com.foldleft.finatra.FinatraServerMain")
//mainClass in Compile := Some("com.foldleft.finagle.FinagleServer")