package org.coalescing.coalescebot

import java.awt.Color

import net.dv8tion.jda.core.{AccountType, JDABuilder}
import org.coalescing.coalescebot.command.CommandManager

import scala.io.Source

object CoalesceBot extends App {

  val PREFIX = "c!"
  val BOT_COLOR = Color.decode("#4CAF50")

  /**
    * @usecase tokens
    *          0 - Discord token
    *          1 - OAuth2 token
    *          2 - ? (Probably console logging)
    */
  val tokens: Array[String] = Source.fromFile("src/main/resources/token.txt").mkString.split(":") // We will eventually change this, but for now this will work

  val JDA = new JDABuilder(AccountType.BOT)
    .setToken(tokens(0))
    .buildBlocking()

  //Register Event Handler
  JDA.addEventListener(CommandManager)

  val REQUEST_CHANNEL = JDA.getTextChannelById(311317585775951872L)
}
