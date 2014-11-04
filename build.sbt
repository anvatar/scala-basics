name := """scala-basics"""

version := "1.0"

scalaVersion := "2.11.4"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scala-sbt" % "io" % "0.13.6" % "test"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.3"

// assertion을 비활성화 하려면 다음 주석 해제
//scalacOptions += "-Xelide-below MAXIMUM"
