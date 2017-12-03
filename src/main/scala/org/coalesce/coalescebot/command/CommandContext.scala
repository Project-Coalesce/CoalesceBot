package org.coalesce.coalescebot.command

import net.dv8tion.jda.core.entities.{Message, MessageChannel, User}
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import org.coalesce.coalescebot.CoalesceBot

class CommandContext(val event: MessageReceivedEvent) {

  //Basic stuff that is just from the event
  val channel: MessageChannel = event.getChannel
  val message: Message = event.getMessage
  val rawMessage: String = event.getMessage.getRawContent
  val author: User = event.getAuthor

  //Doing this so that the other args variable won't be in scope
  val (name, args) = {
    val rawArgs = rawMessage.split(" ")
    (rawArgs.head.replace(CoalesceBot.PREFIX, ""), rawArgs.tail)
  }

}
