package org.coalescing.coalescebot.command.executors.request

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent
import org.coalescing.coalescebot.CoalesceBot
import org.coalescing.coalescebot.CoalesceBot.REQUEST_CHANNEL
import org.coalescing.coalescebot.command.CommandContext
import org.coalescing.coalescebot.utilities.Embeddable

trait Request extends Embeddable {

  def request(context: CommandContext, user: User, embed: EmbedBuilder): Unit = {
    REQUEST_CHANNEL.sendMessage(embed.build()).queue { channel =>
      channel.addReaction("✔").queue()
      channel.addReaction("❌").queue()
    }
  }

  def react(event: GuildMessageReactionAddEvent, name: String): Unit = {
    if (event.getChannel.equals(REQUEST_CHANNEL)) {
      val emote = event.getReactionEmote.getName
      val reaction = if (emote == "✔") true else if (emote == "❌") false else return
      REQUEST_CHANNEL.getMessageById(event.getMessageIdLong).queue {
        message =>
          val embed = message.getEmbeds.get(0)
          val properties = embed.getFooter.getText
          if (!properties.startsWith(name)) return

          response(properties.substring(name.length), reaction)
      }
    }
  }

  def response(properties: String, accepted: Boolean)
}
