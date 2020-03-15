package io.github.gcdd1993.qzone.photo

import org.junit.Test

/**
 * TODO
 *
 * @author gaochen
 * @date 2020/3/15
 */
class PhotoProcessorTest {


  @Test def listAlbumTest(): Unit = {
    PhotoProcessor.listAlbum("1398371419")
      .foreach(album => println(s"find album : ${album.id}, ${album.name}"))
  }

  @Test def listPhotoTest(): Unit = {
    PhotoProcessor.listAlbum("1398371419")
      .foreach(album => {
        println(s"find album : ${album}, ${album.id}, ${album.name}")
        PhotoProcessor.listPhoto(album, "1398371419")
          .foreach(photo => println(s"find photo: ${photo}, ${photo.name}, ${photo.desc}, ${photo.url}"))
      })
  }

  @Test def downloadPhoto(): Unit = {
    PhotoProcessor.listAlbum("1398371419")
      .foreach(album => {
        println(s"find album : ${album}, ${album.id}, ${album.name}")
        PhotoProcessor.listPhoto(album, "1398371419")
          .foreach(photo => {
            println(s"find photo: ${photo}, ${photo.name}, ${photo.desc}, ${photo.url}")
            downloadFile(photo.url, s"output/${Some(photo.name)}.jpg")
          })
      })
  }
}
