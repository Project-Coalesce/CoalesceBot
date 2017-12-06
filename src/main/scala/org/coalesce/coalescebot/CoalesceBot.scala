package org.coalesce.coalescebot

import java.awt.Color

import net.dv8tion.jda.core.{AccountType, JDABuilder}
import org.coalesce.coalescebot.command.CommandManager

import scala.io.Source

object CoalesceBot extends App {

  val PREFIX = "c!"
  val BOT_COLOR = Color.decode("#4CAF50")

  val JDA = new JDABuilder(AccountType.BOT)
    .setToken(Source.fromFile("src/main/resources/token.txt").mkString)
    .buildBlocking()

  //Register Event Handler
  JDA.addEventListener(CommandManager)

}
