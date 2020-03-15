package io.github.gcdd1993.qzone

import java.io.File
import java.net.URL

import com.typesafe.config.{Config, ConfigFactory}
import okhttp3.{OkHttpClient, Request}

import scala.sys.process._

/**
 * @author gaochen
 * @date 2020/3/15
 */
package object photo {

  val baseUrl = "https://user.qzone.qq.com/proxy/domain/photo.qzone.qq.com/fcgi-bin"
  val albumListUri = "fcg_list_album_v3"
  val photoListUri = "cgi_list_photo"

  val config: Config = ConfigFactory.load()

  val qq: String = loadConfig("qq")
  val cookie: String = loadConfig("cookie")
  val output: String = loadConfig("output")

  def loadConfig(key: String): String = {
    if (!config.hasPath("cookie")) {
      println(s"Please add your QZone cookie to config/application.conf with key '$key'")
      System.exit(1)
    }
    config.getString(key)
  }

  val client = new OkHttpClient()

  def get(url: String,
          params: Map[String, _]): String = {
    val buf = new StringBuilder
    buf ++= url
    buf += '?'
    params.foreach(e => {
      buf ++= s"${e._1}=${e._2}"
      buf += '&'
    })
    val finalUrl = buf.toString()
    val request = new Request.Builder()
      .url(finalUrl)
      .method("GET", null)
      .addHeader("Cookie", cookie)
      .build()
    val response = client.newCall(request).execute
    response.body().string()
  }

  def downloadFile(url: String, filename: String): String = {
    new URL(url) #> new File(filename) !!
  }

}
