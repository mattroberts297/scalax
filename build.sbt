lazy val root = (project in file(".")).
  enablePlugins(UniversalPlugin, JavaAppPackaging).
  settings(
    name := "akka-http-example",
    version := "0.1.0",
    scalaVersion := "2.11.6",
    resolvers += "Spy" at "http://files.couchbase.com/maven2/",
    libraryDependencies ++= Seq(
      "com.bionicspirit" %% "shade" % "1.6.0",
      "com.github.kxbmap" %% "configs" % "0.3.0",
      "com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
      "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0",
      "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0",
      "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0" % "test",
      "com.typesafe.slick" %% "slick" % "3.1.0",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.1.0",
      "com.h2database" % "h2" % "1.4.190",
      "org.postgresql" % "postgresql" % "9.4-1205-jdbc41",
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      "org.scalatest" %% "scalatest" % "2.2.4" % "test",
      "org.slf4s" %% "slf4s-api" % "1.7.12",
      "org.mockito" % "mockito-all" % "1.10.19" % "test"
    ),
    bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""
  )
