package org.coalescing.coalescebot.command.executors.image

import java.awt.Color
import java.io.InputStream

import net.dv8tion.jda.core.MessageBuilder
import org.coalescing.coalescebot._
import org.coalescing.coalescebot.command.{BotCommand, CommandContext}
import org.coalescing.coalescebot.utilities.Embeddable

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.{Failure, Success}

object InvertCommand extends BotCommand with Embeddable {

  override val name: String = "invert"
  override val aliases: Set[String] = Set.empty[String]
  override val desc: String = "Inverts the color of the last image in chat."

  implicit val gc: ExecutionContextExecutor = ExecutionContext.global

  override def execute(commandContext: CommandContext): Unit = {
    lastImageUrl(commandContext.channel)
      .flatMap(getImage)
      .map(invertImage)
      .onComplete{
      case Success(image) =>
        commandContext.channel.sendFile(image, System.currentTimeMillis().toString + ".png", new MessageBuilder().append(" ").build()).queue()
      case Failure(e) =>
        e.printStackTrace()
        commandContext.channel.sendError("No images to modify!")
    }
  }

  def invertImage: InputStream => InputStream =
    editPixels(_)((image, ps) => {
      ps.map(p => (p._1, p._2, invertColor(p._3)))
    })

  private def invertColor(color: Color): Color = {
    new Color(255 - color.getRed, 255 - color.getGreen, 255 - color.getBlue)
  }
}
