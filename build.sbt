val deps = new {
  val autoValue = "com.google.auto.value" % "auto-value" % "1.6.3"
  val autoValueAnnotation = "com.google.auto.value" % "auto-value-annotations" % "1.6.3"
  val guava = "com.google.guava" % "guava" % "27.1-jre"
  val junit = "com.github.sbt" % "junit-interface" % "0.13.2"
  val truth = "com.google.truth" % "truth" % "0.45"
  val compileTesting = "com.google.testing.compile" % "compile-testing" % "0.19"
}

ThisBuild / version := "4.2.1"
ThisBuild / organization := "com.eed3si9n.starlark"

lazy val root = (project in file("."))
  .aggregate(starlark)
  .settings(nocomma {
    // ignore src
    Compile / sources := Nil
    Test / sources := Nil
    publish / skip := true
  })

lazy val starlark = (project in file("starlark"))
  .settings(nocomma {
    name := "starlark"
    libraryDependencies ++= List(
      deps.guava,
      deps.autoValueAnnotation,
      deps.autoValue,
      deps.junit % Test,
      deps.truth % Test,
      deps.compileTesting % Test,
    )
    Compile / javacOptions += "-Xdoclint:none"
    crossPaths := false
    autoScalaLibrary := false
  })

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/eed3si9n/bazel"),
    "scm:git@github.com:eed3si9n/bazel.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id    = "eed3si9n",
    name  = "Eugene Yokota",
    email = "@eed3si9n",
    url   = url("https://eed3si9n.com")
  )
)

ThisBuild / description := "Some description about your project."
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://docs.bazel.build/versions/main/skylark/language.html"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
