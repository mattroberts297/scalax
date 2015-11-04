lazy val root = (project in file(".")).settings(
  name := "akka-http-example",
  version := "0.1.0",
  scalaVersion := "2.11.6",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
    "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0",
    "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
    "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0" % "test",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  )
)

