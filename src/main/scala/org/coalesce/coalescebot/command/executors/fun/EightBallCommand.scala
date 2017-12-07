package org.coalesce.coalescebot.command.executors.fun

import org.coalesce.coalescebot._
import org.coalesce.coalescebot.command.{BotCommand, CommandContext}

import scala.util.Random

object EightBallCommand extends BotCommand {

  override val name: String = "8ball"
  override val aliases: Set[String] = Set("eightball")
  override val desc: String = "Ask upon the magic eight ball"

  private val responses: List[String] = List(
    "It is certain",
    "It is decidedly so",
    "Without a doubt",
    "Yes definitely",
    "You may rely on i",
    "As I see it, yes",
    "Most likely",
    "Outlook good",
    "Yes",
    "Signs point to ye",
    "Reply hazy try again",
    "Ask again later",
    "Better not tell you now",
    "Cannot predict now",
    "Concentrate and ask agai",
    "Don't count on it",
    "My reply is no",
    "My sources say no",
    "Outlook not so good",
    "Very doubtful"
  )

  override def execute(commandContext: CommandContext): Unit = {
    val response = responses(Random.nextInt(responses.size))
    commandContext.channel.sendFancyMessage(":8ball: Magic Eight Ball", desc = response).queue()
  }
}