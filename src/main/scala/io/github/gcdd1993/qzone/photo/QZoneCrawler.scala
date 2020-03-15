package io.github.gcdd1993.qzone.photo

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util.UUID

import scala.collection.mutable
import scala.io.Source

/**
 * 爬取相册
 *
 * @author gaochen
 * @date 2020/3/15
 */
object QZoneCrawler extends App {

  private[this] val doneCache = "downloaded.txt"

  val cache: scala.collection.mutable.Set[String] = {
    val set = mutable.Set[String]()
    if (Files.exists(Paths.get(doneCache))) {
      val source = Source.fromFile(doneCache)
      source.getLines().foreach(set.add)
      source.close()
    }
    set
  }

  checkConfig()
  try {
    download()
    println("[INFO] photos are all downloaded.")
  } catch {
    case _: Exception =>
      println("[ERROR] something went wrong, retry...")
      download()
  } finally {
    val pw = new PrintWriter(new File(doneCache))
    cache.foreach(pw.println)
    pw.flush()
    pw.close()
  }

  def checkConfig(): Unit = {
    // check config
    println("=================config load finished=================")
    println(s"=================qq size ${qqAccesses.size}=================")
  }

  def download(): Unit = {
    qqAccesses
      .foreach(qqAccess => {
        println(s"=================fetch photos for ${qqAccess.qq}=================")
        PhotoProcessor.listAlbum(qqAccess)
          .foreach(album => {
            val photos = PhotoProcessor.listPhoto(album, qqAccess)
            println(s"[INFO] find album: ${album.name}, photos: ${if (photos != null) photos.size else 0}")
            photos.foreach(photo => {
              val dir = Paths.get(output, s"${qqAccess.qq}/${album.name}")
              if (!Files.exists(dir)) {
                Files.createDirectories(dir)
              }
              if (cache.contains(photo.url)) {
                println(s"[WARN] photo ${photo.url} is already downloaded, skip...")
              } else {
                downloadFile(photo.url, s"$dir/${UUID.randomUUID()}.jpg")
                println(s"[INFO] download photo: ${photo.name}")
                cache.add(photo.url)
              }
            })
          })
      })
  }

}