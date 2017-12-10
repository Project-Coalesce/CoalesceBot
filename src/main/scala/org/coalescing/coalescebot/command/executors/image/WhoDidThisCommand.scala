package org.coalescing.coalescebot.command.executors.image
import java.awt.image.BufferedImage
import java.io.{File, InputStream}
import javax.imageio.ImageIO

import net.coobird.thumbnailator.Thumbnails

object WhoDidThisCommand extends ImageCommand {

  override val name = "whodidthis"
  override val aliases = Set("wdt")
  override val desc = "Modifies an image to add a shitty \"Who did this\" caption."

  private val wdtImage = ImageIO.read(new File("src/main/resources/images/whodidthis.png"))
  private val emptyX = 0
  private val emptyY = 159
  private val emptyWidth = 716
  private val emptyHeight = 403

  override def modifyImage(inputStream: InputStream): InputStream = {
    editImage(inputStream, image => {

      val editedOg = Thumbnails.of(image)
        .size(emptyWidth, emptyHeight)
        .asBufferedImage()

      //Make a new third image and draw onto it
      val combined = new BufferedImage(wdtImage.getWidth, wdtImage.getHeight(), BufferedImage.TYPE_INT_ARGB)
      val g = combined.getGraphics
      g.drawImage(wdtImage, 0, 0, null)
      g.drawImage(editedOg, emptyX, emptyY, null)

      combined
    })
  }
}
