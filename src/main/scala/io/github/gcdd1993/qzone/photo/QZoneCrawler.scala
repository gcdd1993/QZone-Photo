package io.github.gcdd1993.qzone.photo

import java.nio.file.{Files, Paths}
import java.util.UUID

/**
 * 爬取相册
 *
 * @author gaochen
 * @date 2020/3/15
 */
object QZoneCrawler extends App {

  PhotoProcessor.listAlbum(qq)
    .foreach(album => {
      println(s"find album : ${album.name}")
      PhotoProcessor.listPhoto(album, qq)
        .foreach(photo => {
          println(s"find photo: ${photo.name}, download url is ${photo.url}")

          val dir = Paths.get(output, album.name)
          if (!Files.exists(dir)) {
            Files.createDirectories(dir)
          }
          downloadFile(photo.url, s"$dir/${UUID.randomUUID()}.jpg")
        })
    })
}