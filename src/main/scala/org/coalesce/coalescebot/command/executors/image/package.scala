package org.coalesce.coalescebot.command.executors

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}
import java.net.{URL, URLConnection}
import javax.imageio.ImageIO

import net.dv8tion.jda.core.entities.MessageChannel

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future, Promise}

package object image {

  implicit val gc: ExecutionContextExecutor = ExecutionContext.global

  private[image] def lastImageUrl(channel: MessageChannel): Future[URL] = {
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

  private[image] def getImage(url: URL): Future[InputStream] = {
    println(url)
    Future{
      val connection: URLConnection = url.openConnection()
      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
      connection.connect()
      connection.getInputStream
    }
  }

  private[image] def editPixels(inputStream: InputStream, f: (BufferedImage, Seq[(Int, Int, Color)]) => Seq[(Int, Int, Color)]): InputStream = {

    val image: BufferedImage = ImageIO.read(inputStream)

    val colors = for {
      x <- 0 until image.getWidth
      y <- 0 until image.getHeight
    } yield (x, y, new Color(image.getRGB(x, y), true))

    //Do the transformation
    f(image, colors)
      .foreach(p => {
        image.setRGB(p._1, p._2, p._3.getRGB)
      })

    val outputStream = new ByteArrayOutputStream
    ImageIO.write(image, "png", outputStream)
    new ByteArrayInputStream(outputStream.toByteArray)
  }
}
