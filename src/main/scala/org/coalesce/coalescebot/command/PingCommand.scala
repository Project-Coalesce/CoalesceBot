package org.coalesce.coalescebot.command

object PingCommand extends BotCommand {

  override val name: String = "ping"
  override val aliases: Set[String] = Set.empty[String]
  override val desc: String = "Just a simple ping command"

  override def execute(context: CommandContext): Unit = {
    context.channel.sendMessage("pong!").queue()
  }
}
