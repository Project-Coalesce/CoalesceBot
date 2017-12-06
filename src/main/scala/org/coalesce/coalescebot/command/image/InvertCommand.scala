package org.coalesce.coalescebot.command.image

import java.io.InputStream
import java.net.{URL, URLConnection}

import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.MessageChannel
import org.coalesce.coalescebot._
import org.coalesce.coalescebot.command.{BotCommand, CommandContext}

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future, Promise}
import scala.util.{Failure, Success}

object InvertCommand extends BotCommand {

  override val name: String = "invert"
  override val aliases: Set[String] = Set.empty[String]
  override val desc: String = "Inverts the color of the last image in chat."

  implicit val gc: ExecutionContextExecutor = ExecutionContext.global

  override def execute(commandContext: CommandContext): Unit = {
    lastImageUrl(commandContext.channel).flatMap(getImage).onComplete{
      case Success(image) =>
        commandContext.channel.sendFile(image, System.currentTimeMillis().toString + ".png", new MessageBuilder().append(" ").build()).queue()
      case Failure(e) =>
        commandContext.channel.sendError("No images to modify!").queue()
    }
  }

  private def lastImageUrl(channel: MessageChannel): Future[URL] = {
    val promise = Promise[URL]()
    Future{
      val messages = channel.getHistory.retrievePast(20).complete()
      val imageUrl = messages
        .asScala
        .flatMap(_.getAttachments.asScala)
        .find(_.isImage)
        .map(u => new URL(u.getUrl))

      imageUrl match {
        case Some(u) => promise.success(u)
        case None => promise.failure(new InterruptedException())
      }
    }
    promise.future
  }

  private def getImage(url: URL): Future[InputStream] = {
    println(url)
    Future{
      val connection: URLConnection = url.openConnection()
      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
      connection.connect()
      connection.getInputStream
    }
  }
}
