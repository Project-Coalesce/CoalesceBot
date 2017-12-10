package org.coalescing.coalescebot.command.executors.fun

import java.awt.Color
import java.util.concurrent.TimeUnit

import org.coalescing.coalescebot._
import org.coalescing.coalescebot.command.{BotCommand, CommandContext}
import org.coalescing.coalescebot.utilities.Embeddable

import scala.util.Random

object EightBallCommand extends BotCommand with Embeddable {

  override val name: String = "8ball"
  override val aliases: Set[String] = Set("eightball")
  override val desc: String = "Ask upon the magic eight ball"

  private val negative: Color = Color.red
  private val neutral: Color = Color.yellow
  private val positive: Color = Color.green

  private val responses: Map[String, Color] = Map(
    "It is certain" -> positive,
    "It is decidedly so" -> positive,
    "Without a doubt" -> positive,
    "Yes definitely" -> positive,
    "You may rely on it" -> positive,
    "As I see it, yes" -> positive,
    "Most likely" -> positive,
    "Outlook good" -> positive,
    "Yes" -> positive,
    "Signs point to yes" -> positive,
    "Reply hazy try again" -> neutral,
    "Ask again later" -> neutral,
    "Better not tell you now" -> neutral,
    "Cannot predict now" -> neutral,
    "Concentrate and ask agai" -> neutral,
    "Don't count on it" -> negative,
    "My reply is no" -> negative,
    "My sources say no" -> negative,
    "Outlook not so good" -> negative,
    "Very doubtful" -> negative
  )

  override def execute(context: CommandContext): Unit = {
    if (context.args.size <= 0) {
      context.sendError("Ask your question!")
      return
    }

    val question = context.args.mkString(" ")
    val response = responses.toList(Random.nextInt(responses.size))

    context(embed().makeEmbed(
      title = ":8ball: Magic Eight Ball",
      color = response._2,
      fields = Array(field("Question", question, false), field("Response", response._1, false))
    ), queueAfter = none, deleteAfter = (30L, TimeUnit.SECONDS))
  }
}