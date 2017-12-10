package org.coalescing.coalescebot.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import org.coalescing.coalescebot.CoalesceBot
import org.coalescing.coalescebot.command.executors.fun.EightBallCommand
import org.coalescing.coalescebot.command.executors.image.InvertCommand
import org.coalescing.coalescebot.command.executors.misc.PingCommand
import org.coalescing.coalescebot._

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
      case None => context.channel.sendError("No such command!")
    }
  }

}
