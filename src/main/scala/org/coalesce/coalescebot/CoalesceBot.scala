package org.coalesce.coalescebot

import net.dv8tion.jda.core.{AccountType, JDABuilder}

import scala.io.Source

object CoalesceBot extends App {

  val JDA = new JDABuilder(AccountType.BOT)
    .setToken(Source.fromFile("src/main/resources/token.txt").mkString)
    .buildBlocking()

}
