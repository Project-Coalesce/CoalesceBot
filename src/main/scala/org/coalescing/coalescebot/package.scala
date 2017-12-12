package org.coalescing

import java.awt.Color
import java.util
import java.util.concurrent.TimeUnit

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities._
import net.dv8tion.jda.core.requests.RestAction
import org.coalescing.coalescebot.utilities.Embeddable
import predef.Implicits.function2Consumer

package object coalescebot {

  implicit class RichMessageChannel(channel: MessageChannel) extends Embeddable {
    def sendError(message: String): Unit = {
      send(embed().makeEmbed(
        title = ":no_entry_sign: Error!",
        desc = message,
        color = Color.decode("#F44336")
      ), queueAfter = none, deleteAfter = (30L, TimeUnit.SECONDS))
    }

    def send(embed: EmbedBuilder, queueAfter: (Long, TimeUnit),
             deleteAfter: (Long, TimeUnit)): Unit =
      sendTask(channel.sendMessage(embed.build()), queueAfter, deleteAfter)

    def send(text: String, mention: IMentionable = null, queueAfter: (Long, TimeUnit),
             deleteAfter: (Long, TimeUnit)): Unit = {
      sendTask(channel.sendMessage(if (mention != null) s"${mention.getAsMention}: $text" else text),
        queueAfter, deleteAfter)
    }

    def sendTask(task: RestAction[Message], queueAfter: (Long, TimeUnit), deleteAfter: (Long, TimeUnit)): Unit = {
      val remove: (Message => Unit) =
        message => if (deleteAfter != null) message.delete().queueAfter(deleteAfter._1, deleteAfter._2)

      if (queueAfter != null) task.queueAfter(queueAfter._1, queueAfter._2, function2Consumer(remove))
      else task.queue()
    }
  }

  implicit class RichEmbedBuilder(embed: EmbedBuilder) {
    def makeEmbed(title: String = "", url: String = "",
                  author: String = null, authorUrl: String = null,
                  desc: String = "",
                  color: Color = CoalesceBot.BOT_COLOR,
                  fields: Array[MessageEmbed.Field] = Array.empty[MessageEmbed.Field],
                  footer: String = ""): EmbedBuilder =
      {
        fields.foreach(embed.addField)
        embed
          .setTitle(title, url)
          .setAuthor(author, null, authorUrl)
          .setColor(color)
          .setDescription(desc)
          .setFooter(footer, null)
      }
  }

  implicit class RichUser(user: User) {
    def usePrivateChannel(task: (PrivateChannel => Unit)): Unit = user.openPrivateChannel().queue(channel => task(channel))
  }

  implicit def listExtensions[T](seq: util.List[T]) = new {
    def getOrNull(index: Int): T = if (seq.size() <= index) null.asInstanceOf[T] else seq.get(index)
  }
}
