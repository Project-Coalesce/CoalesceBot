
name := "Coalesce Bot"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "net.dv8tion" % "JDA" % "3.3.0_260", // JDA
  "net.coobird" % "thumbnailator" % "0.4.8", // Thumbnail thingy
  "net.lyxnx" % "predef" % "2.0.9" // Alice's utils lib
)