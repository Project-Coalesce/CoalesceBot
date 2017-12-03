package org.coalesce.coalescebot

import net.dv8tion.jda.core.{AccountType, JDABuilder}
import org.coalesce.coalescebot.command.CommandManager

import scala.io.Source

object CoalesceBot extends App {

  val PREFIX = "c!"

  val JDA = new JDABuilder(AccountType.BOT)
    .setToken(Source.fromFile("src/main/resources/token.txt").mkString)
    .buildBlocking()

  //Register Event Handler
  JDA.addEventListener(CommandManager)

}
