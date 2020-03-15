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

object PhotoProcessor {

  /**
   * 列举相册
   *
   * @return 相册列表
   */
  def listAlbum(qq: String): List[Album] = {
    val params = Map(
      "g_tk" -> "981116523",
      "hostUin" -> qq,
      "uin" -> qq,
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
    val res = get(url, params)

    // 1.去除_Callback()
    val cleanJson = res.replace("_Callback(", "")
      .replace(");", "")
    // 2.解析相册列表
    JSON.parseObject(cleanJson)
      .getJSONObject("data")
      .getJSONArray("albumListModeSort")
      .toArray()
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
                qq: String): List[Photo] = {
    val params = Map(
      "g_tk" -> "981116523",
      "hostUin" -> qq,
      "topicId" -> album.id,
      "pageStart" -> 0,
      "pageNum" -> Int.MaxValue,
      "uin" -> qq,
      "appid" -> 4,
      "inCharset" -> "utf-8",
      "outCharset" -> "utf-8",
      "source" -> "qzone",
      "plat" -> "qzone",
      "format" -> "jsonp"
    )
    val url = s"$baseUrl/$photoListUri"
    val res = get(url, params)
    // 1.去除_Callback()
    val cleanJson = res.replace("_Callback(", "")
      .replace(");", "")
    // 2.解析相册列表
    val photoJsonArray = JSON.parseObject(cleanJson)
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
