package org.coalescing.coalescebot.command.executors.image

import java.io.InputStream

import net.dv8tion.jda.core.MessageBuilder
import org.coalescing.coalescebot.command.{BotCommand, CommandContext}
import org.coalescing.coalescebot._

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
        context.err("No images to modify!")
    }
  }

  def modifyImage(inputStream: InputStream): InputStream
}
