# QQ空间相册解析

经过寻找，最终锁定了以下接口

## 相册列表

URL:

```http
https://user.qzone.qq.com/proxy/domain/photo.qzone.qq.com/fcgi-bin/fcg_list_album_v3
```

Params:

```http
g_tk:65887268
callback:shine0_Callback
t:976241465
hostUin:1398371419
uin:1398371419
appid:4
inCharset:utf-8
outCharset:utf-8
source:qzone
plat:qzone
format:jsonp
notice:0
filter:1
handset:4
pageNumModeSort:40
pageNumModeClass:15
needUserInfo:1
idcNum:4
callbackFun:shine0
_:1582564479598
```

Headers:

```http
cookie:xxx
user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36
```

请求结果：

![](https://i.loli.net/2020/02/25/AMuzTFhIEwqCcB7.png)

接口请求结果（Postman测试）：

![](https://i.loli.net/2020/02/25/4VUwOKZcl3FxL8S.png)

## 相册照片列表

URL:

```http
https://h5.qzone.qq.com/proxy/domain/photo.qzone.qq.com/fcgi-bin/cgi_list_photo
```

Params:

```http
g_tk:65887268
callback:shine0_Callback
t:701026091
mode:0
idcNum:4
hostUin:1398371419
topicId:V12wqfVg3Kg2KJ
noTopic:0
uin:1398371419
pageStart:0
pageNum:30
skipCmtCount:0
singleurl:1
batchId:
notice:0
appid:4
inCharset:utf-8
outCharset:utf-8
source:qzone
plat:qzone
outstyle:json
format:jsonp
json_esc:1
question:
answer:
callbackFun:shine0
_:1582563698046
```

请求结果：

![](https://i.loli.net/2020/02/25/t19jE8qDM2uLCzm.png)

接口请求结果（Postman测试）：

![](https://i.loli.net/2020/02/25/jBvWybgYOX7EZVl.png)
