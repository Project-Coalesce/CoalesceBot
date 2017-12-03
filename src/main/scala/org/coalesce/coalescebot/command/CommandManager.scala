package org.coalesce.coalescebot.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import org.coalesce.coalescebot.CoalesceBot

object CommandManager extends ListenerAdapter {

  val commands: Set[BotCommand] = Set(
    PingCommand
  )

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {

    if (event.getMessage.getRawContent.startsWith(CoalesceBot.PREFIX)){

      println(new CommandContext(event).name)

      handleCommand(new CommandContext(event))
    }
  }

  private def handleCommand(context: CommandContext): Unit = {
    commands.find(_.doesMatch(context.name)) match {
      case Some(c) => c.execute(context)
      case None => //TODO: ADD ERROR OR SOMETHING
    }
  }

}
