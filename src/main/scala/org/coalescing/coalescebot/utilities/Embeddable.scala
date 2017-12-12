package org.coalescing.coalescebot.utilities

import java.util.concurrent.TimeUnit

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.{EmbedType, MessageEmbed}

trait Embeddable {
  def embed(): EmbedBuilder = new EmbedBuilder

  def field(title: String, desc: String, inline: Boolean = true): MessageEmbed.Field =
    new MessageEmbed.Field(title, desc, inline)

  val none: (Long, TimeUnit) = (0L, TimeUnit.NANOSECONDS) // yes
}
