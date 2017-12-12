package org.coalescing.coalescebot.command.executors.image

import java.awt.Color
import java.io.InputStream

object InvertCommand extends ImageCommand {

  override val name: String = "invert"
  override val aliases: Set[String] = Set.empty[String]
  override val desc: String = "Inverts the color of the last image in chat."

  override def modifyImage(inputStream: InputStream): InputStream =
    editPixels(inputStream, (_, ps) => {
      ps.map(p => (p._1, p._2, invertColor(p._3)))
    })

  private def invertColor(color: Color): Color =
    new Color(255 - color.getRed, 255 - color.getGreen, 255 - color.getBlue)

}