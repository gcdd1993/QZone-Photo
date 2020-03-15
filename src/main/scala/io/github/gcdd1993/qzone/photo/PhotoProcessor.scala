package io.github.gcdd1993.qzone.photo

import com.alibaba.fastjson.JSON

/**
 * 相册
 *
 * @param id   相册ID
 * @param name 相册名称
 */
case class Album(id: String,
                 name: String)

case class Photo(name: String,
                 desc: String,
                 url: String)

/**
 * QQ配置
 *
 * @param qq     QQ号
 * @param gTk    g_tk
 * @param cookie cookie
 */
case class QQAccess(qq: String,
                    gTk: String,
                    cookie: String)

object PhotoProcessor {

  /**
   * 列举相册
   *
   * @return 相册列表
   */
  def listAlbum(qqAccess: QQAccess): List[Album] = {
    val params = Map(
      "g_tk" -> qqAccess.gTk,
      "hostUin" -> qqAccess.qq,
      "uin" -> qqAccess.qq,
      "appid" -> 4,
      "inCharset" -> "utf-8",
      "outCharset" -> "utf-8",
      "source" -> "qzone",
      "plat" -> "qzone",
      "format" -> "jsonp",
      "needUserInfo" -> 1,
      "idcNum" -> 4
    )
    val url = s"$baseUrl/$albumListUri"
    val res = get(url, params, qqAccess.cookie)

    // 2.解析相册列表
    val albumJsonArray = JSON.parseObject(res)
      .getJSONObject("data")
      .getJSONArray("albumListModeSort")
    if (albumJsonArray == null ||
      albumJsonArray.isEmpty) {
      return List.empty
    }
    albumJsonArray
      .toArray
      .map(e => {
        val node = JSON.parseObject(e.toString)
        Album(node.getString("id"),
          node.getString("name"))
      }).toList
  }

  /**
   * 列举照片
   */
  def listPhoto(album: Album,
                qqAccess: QQAccess): List[Photo] = {
    val params = Map(
      "g_tk" -> qqAccess.gTk,
      "hostUin" -> qqAccess.qq,
      "topicId" -> album.id,
      "pageStart" -> 0,
      "pageNum" -> Int.MaxValue,
      "uin" -> qqAccess.qq,
      "appid" -> 4,
      "inCharset" -> "utf-8",
      "outCharset" -> "utf-8",
      "source" -> "qzone",
      "plat" -> "qzone",
      "format" -> "jsonp"
    )
    val url = s"$baseUrl/$photoListUri"
    val res = get(url, params, qqAccess.cookie)
    // 2.解析相册列表
    val photoJsonArray = JSON.parseObject(res)
      .getJSONObject("data")
      .getJSONArray("photoList")
    if (photoJsonArray == null ||
      photoJsonArray.isEmpty) {
      return List.empty
    }
    photoJsonArray
      .toArray
      .map(e => {
        val node = JSON.parseObject(e.toString)
        Photo(node.getString("name"),
          node.getString("desc"),
          node.getString("url"))
      }).toList
  }

}
