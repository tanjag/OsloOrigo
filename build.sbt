val ScalatraVersion = "2.7.1"

ThisBuild / scalaVersion := "2.12.13"
ThisBuild / organization := "com.github.tanjag"

lazy val hello = (project in file("."))
  .settings(
    name := "Bike Station Web App",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "org.scalatra" %% "scalatra-json" % ScalatraVersion,
      "org.json4s" %% "json4s-jackson" % "3.6.11",
      "org.apache.httpcomponents" % "httpclient" % "4.5.13",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
      "org.eclipse.jetty" % "jetty-webapp" % "9.4.35.v20201120" % "container",
      "org.scalactic" %% "scalactic" % "3.1.0",
      "org.scalatest" %% "scalatest" % "3.1.0" % Test,
      "org.scalamock" %% "scalamock" % "5.1.0" % Test
    ),
  )

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)
