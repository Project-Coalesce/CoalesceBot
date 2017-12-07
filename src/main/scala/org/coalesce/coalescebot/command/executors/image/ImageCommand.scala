package org.coalesce.coalescebot.command.executors.image

import java.io.InputStream

import net.dv8tion.jda.core.MessageBuilder
import org.coalesce.coalescebot.command.{BotCommand, CommandContext}
import org.coalesce.coalescebot._

import scala.concurrent.Future
import scala.util.{Failure, Success}

abstract class ImageCommand extends BotCommand {

  final override def execute(context: CommandContext): Unit = {
    lastImageUrl(context.channel)
      .flatMap(getImage)
      .flatMap(i => Future {
        modifyImage(i)
      }) //Wanna keep this off the main thread
      .onComplete{
        case Success(image) =>
          context.channel.sendFile(image, System.currentTimeMillis().toString + ".png", new MessageBuilder().append(" ").build()).queue()
        case Failure(e) =>
          e.printStackTrace()
          context.channel.sendError("No images to modify!").queue()
      }
  }

  def modifyImage(inputStream: InputStream): InputStream

}
