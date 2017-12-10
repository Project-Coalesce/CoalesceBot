package org.coalescing.coalescebot.command

import java.awt.Color
import java.util.concurrent.TimeUnit

import net.dv8tion.jda.core.{EmbedBuilder, JDA}
import net.dv8tion.jda.core.entities.{PrivateChannel, _}
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import org.coalescing.coalescebot.CoalesceBot
import org.coalescing.coalescebot._

import scala.concurrent.duration.TimeUnit

class CommandContext(val event: MessageReceivedEvent) {

  //Basic stuff that is just from the event
  val jda: JDA = event.getJDA
  val guild: Guild = event.getGuild
  val channel: MessageChannel = event.getChannel
  val message: Message = event.getMessage
  val rawMessage: String = event.getMessage.getRawContent
  val author: User = event.getAuthor
  val member: Member = guild.getMember(author)

  def usePrivateChannel(task: (PrivateChannel => Unit)): Unit = author.openPrivateChannel().queue(channel => task(channel))

  def sendError(message: String): Unit = channel.sendError(message)

  def mention(text: String): Unit = apply(text, author, null, null)

  def apply(embed: EmbedBuilder, queueAfter: (Long, TimeUnit), deleteAfter: (Long, TimeUnit)): Unit =
    channel.send(embed, queueAfter, deleteAfter)

  def apply(text: String, mention: IMentionable = null, queueAfter: (Long, TimeUnit), deleteAfter: (Long, TimeUnit)): Unit =
    channel.send(text, if (mention != null) mention else author, queueAfter, deleteAfter)

  //Doing this so that the other args variable won't be in scope
  val (name, args) = {
    val rawArgs = rawMessage.split(" ")
    (rawArgs.head.replace(CoalesceBot.PREFIX, ""), rawArgs.tail)
  }

}
