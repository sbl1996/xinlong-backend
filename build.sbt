name := "xinlong"

scalaVersion := "2.12.2"

scalacOptions ++= Seq(
  "-deprecation",
  "-Xfatal-warnings",
  "-feature"
)

libraryDependencies ++= akkaHttpDeps

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "org.postgresql" % "postgresql" % "9.4.1212",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0"
)


libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

val akkaHttpDeps = {
  val akkaHttpVersion = "10.0.6"
  Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test"
  )
}