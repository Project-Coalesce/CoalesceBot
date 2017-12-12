package org.coalescing.coalescebot.command.executors.request

import java.util.concurrent.TimeUnit

import org.coalescing.coalescebot.{listExtensions, _}
import predef._
import org.coalescing.coalescebot.command.{BotCommand, CommandContext}
import org.coalescing.coalescebot.utilities.Embeddable

object RoleRequestCommand extends BotCommand with Request with Embeddable {

  val jda = CoalesceBot.JDA

  val blacklist: Set[Long] = Set (
    388184083508756482L, // Founder
    388184139809161216L, // Admin
    388184199233929216L, // Daddy
    320952521965305858L, // Supporter
    388184976765485057L  // Respected
  )

  override val name: String = "rolerequest"
  override val aliases: Set[String] = Set("request", "rr")
  override val desc: String = "Request a role to the server staff"

  override def execute(context: CommandContext): Unit = {
    val user = context.author

    if (context.args.length <= 0) {
      context.err("Specify your requested role!")
      return
    }

    val role = context.guild.getRolesByName(context.args(0), true).getOrNull(0)
    if (role == null || blacklist.contains(role.getIdLong) || context.member.getRoles.contains(role)) {
      context.err("Invalid role!")
      return
    }

    context.author.usePrivateChannel(channel =>
      channel.send(embed().makeEmbed(
        title = s"Role request for: ${role.getName}",
        url = s"https://github.com/login/oauth/authorize?client_id=2e8a8ed194265736c99a&state=${role.getIdLong}+${user.getIdLong}",
        desc = "We need to verify your ability to code in the requested language by looking at your GitHub profile." +
          "\nIt's also needed to make sure the GitHub account belongs to you, and so we request a one-time authentication" +
          "(click on the title of this box).\n" +
          "You can remove the authorization at any time in your GitHub profile.",
        footer = "role="
      ), queueAfter = none, deleteAfter = (10L, TimeUnit.MINUTES)) // I guess this is plenty of time
    )
    context(s"Your request for the role ${role.getName} is almost ready, " +
    "but we need to verify your GitHub profile first. We have sent more details in DM's.",
      queueAfter = none, deleteAfter = (30L, TimeUnit.MINUTES))
  }

  override def response(properties: String, accepted: Boolean): Unit = {
    val split = properties.split(",")

    val user = jda.getUserById(split(0))
    val role = jda.getRoleById(split(1))
    val guild = jda.getGuildById(split(2))

    if (accepted) user.usePrivateChannel(
      _.send(s"You have been awarded the ${role.getName} on ${guild.getName}",
      queueAfter = none, deleteAfter = null))
    else user.usePrivateChannel(
        _.send(s"Your request for the role ${role.getName} on the server ${guild.getName} was rejected by moderators.\n" +
        "Make sure you have code of the respective language in your GitHub account.", queueAfter = none, deleteAfter = null))
  }

}
