
name := "Coalesce Bot"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  //JDA
  "net.dv8tion" % "JDA" % "3.3.0_260",
  "net.coobird" % "thumbnailator" % "0.4.8"
)