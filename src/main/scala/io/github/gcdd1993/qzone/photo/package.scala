package io.github.gcdd1993.qzone

import com.typesafe.config.{Config, ConfigFactory}
import java.io.File
import java.net.URL

import com.alibaba.fastjson.JSON
import okhttp3.{OkHttpClient, Request}

import scala.sys.process._
import scala.collection.JavaConverters._
import scala.collection.mutable

package object photo {

  val baseUrl = "https://user.qzone.qq.com/proxy/domain/photo.qzone.qq.com/fcgi-bin"
  val albumListUri = "fcg_list_album_v3"
  val photoListUri = "cgi_list_photo"

  val config: Config = ConfigFactory.load()

  val output: String = {
    val key = "output"
    checkConfig(config, key)
    config.getString(key)
  }

  val qqAccesses: mutable.Seq[QQAccess] = {
    val key = "qq_access"
    checkConfig(config, key)
    config.getConfigList(key)
      .asScala
      .map(configNode => {
        QQAccess({
          val k = "qq"
          checkConfig(configNode, k)
          configNode.getString(k)
        }, {
          val k = "g_tk"
          checkConfig(configNode, k)
          configNode.getString(k)
        }, {
          val k = "cookie"
          checkConfig(configNode, k)
          configNode.getString(k)
        })
      })
  }

  def checkConfig(config: Config,
                  key: String): Unit = {
    if (!config.hasPath(key)) {
      println(s"Please set config with key '$key' to your config file!")
      System.exit(1)
    }
  }

  val client = new OkHttpClient()

  def get(url: String,
          params: Map[String, _],
          cookie: String): String = {
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
    val code = response.code()
    if (code != 200) {
      println(s"[ERROR] http code error :$code")
      System.exit(1)
    }
    val body = response.body().string()
    // 1.去除_Callback()
    val cleanJson = body
      .replace("_Callback(", "")
      .replace(");", "")

    val jsonObj = JSON.parseObject(cleanJson)
    val msCode = jsonObj.getInteger("code")
    val msSubCode = jsonObj.getInteger("subcode")
    val message = jsonObj.getString("message")

    if (msCode != 0 ||
      msSubCode != 0) {
      println(s"[ERROR] code: $msCode, subcode: $msSubCode, message: $message")
    }
    cleanJson
  }

  def downloadFile(url: String, filename: String): String = {
    new URL(url) #> new File(filename) !!
  }

}
