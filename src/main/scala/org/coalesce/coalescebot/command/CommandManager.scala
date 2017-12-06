package org.coalesce.coalescebot.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import org.coalesce.coalescebot.CoalesceBot
import org.coalesce.coalescebot._
import org.coalesce.coalescebot.command.image.InvertCommand

object CommandManager extends ListenerAdapter {

  val commands: Set[BotCommand] = Set(
    PingCommand,
    EightBallCommand,
    InvertCommand
  )

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {

    if (event.getMessage.getRawContent.startsWith(CoalesceBot.PREFIX)){
      handleCommand(new CommandContext(event))
    }
  }

  private def handleCommand(context: CommandContext): Unit = {
    commands.find(_.doesMatch(context.name)) match {
      case Some(c) => c.execute(context)
      case None => context.channel.sendError("No such command!").queue()
    }
  }

}
