package org.coalesce.coalescebot.command.executors.misc

import org.coalesce.coalescebot._
import org.coalesce.coalescebot.command.{BotCommand, CommandContext}

object PingCommand extends BotCommand {

  override val name: String = "ping"
  override val aliases: Set[String] = Set.empty[String]
  override val desc: String = "Just a simple ping command"

  //TODO: Add more to this
  override def execute(context: CommandContext): Unit = {
    context.channel.sendFancyMessage(title = "Pong! :ping_pong:").queue()
  }
}