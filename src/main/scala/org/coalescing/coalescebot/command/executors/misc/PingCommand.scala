package org.coalescing.coalescebot.command.executors.misc

import java.awt.Color
import java.util.concurrent.TimeUnit

import org.coalescing.coalescebot._
import org.coalescing.coalescebot.command.{BotCommand, CommandContext}
import org.coalescing.coalescebot.utilities.Embeddable

object PingCommand extends BotCommand with Embeddable {

  override val name: String = "ping"
  override val aliases: Set[String] = Set.empty[String]
  override val desc: String = "Just a simple ping command"

  //TODO: Add more to this
  override def execute(context: CommandContext): Unit = {
    context(embed().makeEmbed(
      title = "Pong! :ping_pong:",
      color = Color.cyan,
      fields = Array(field("Discord API", s"${context.jda.getPing} ms"))
    ), queueAfter = none, deleteAfter = (30L, TimeUnit.SECONDS))
  }
}