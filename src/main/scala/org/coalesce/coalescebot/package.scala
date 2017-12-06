package org.coalesce

import java.awt.Color

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.{Message, MessageChannel, MessageEmbed}
import net.dv8tion.jda.core.requests.RestAction

package object coalescebot {

  implicit class RichMessageChannel(channel: MessageChannel) {
    def sendFancyMessage(
                        title: String = "",
                        desc: String = "",
                        color: Color = CoalesceBot.BOT_COLOR,
                        fields: List[MessageEmbed.Field] = List.empty[MessageEmbed.Field]
                        ): RestAction[Message] = {

      val builder = new EmbedBuilder()
        .setTitle(title)
        .setDescription(desc)
        .setColor(color)

      //Finally add the fields
      channel.sendMessage(fields.foldRight(builder)((a, b) => b.addField(a)).build())
    }

    def sendError(message: String): RestAction[Message] = {
      sendFancyMessage(":no_entry_sign: Error!", message, color = Color.decode("#F44336"))
    }
  }

}
