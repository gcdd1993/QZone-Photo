# QQ空间相册批量下载

> 本工具是批量下载QQ空间相册的小工具

# 效果截图

> 以下是我自己的QQ空间相册下载结果，下载的照片在运行目录的`output`目录下，按照`QQ号-->相册名-->照片`存储

![](https://i.loli.net/2020/03/15/yr4EwvJCURVShLf.png)

![](https://i.loli.net/2020/03/15/1dh49Eou6iWNJay.png)

# 准备

基于`Jdk1.8`开发，所以需要安装`Jdk`。

# 使用

在[Release](https://github.com/gcdd1993/QZone-Photo/releases)页面下载 `QZone-Photo-v0.1.0.zip`，解压得到` QZone-Photo-v0.1.0`，执行

> 以下是在Git Bash中运行的命令

```bash
$ cd  QZone-Photo-v0.1.0
$ vim application.conf
{
	"output":"output",
    "qq_access":[
        {
            "g_tk":"g_tk",
            "qq":"qq号",
            "cookie":"QZone cookie"
        }
    ]
}
$ bin/QZone-Photo
=================config load finished=================
=================qq size 1=================
=================fetch photos for ***=================
[INFO] find album: 朋友网头像, photos: 0
[INFO] find album: 高中同学, photos: 23
...
```

# 配置说明

`g_tk`和`cookie`获取，参照[如何获取QQ空间Cookie](#如何获取QQ空间Cookie)

```json
{
    "output":"相册输出目录",
    "qq_access":[
        {
            "g_tk":"QQ空间的g_tk",
            "qq":"你的QQ号",
            "cookie":"QQ空间的cookie"
        }
    ],
    "http":{
        "sleep":1, // 下载间隔，如果失败次数过多，建议调大，单位是ms
        "timeout":5, // HTTP连接超时，单位s
        "read_timeout":5, // HTTP读超时，单位s
        "retry":true // 失败重连
    }
}
```

> 支持同时下载多个QQ空间的相册，`qq_access`配置多个即可。但是我没有尝试。

# 如何获取QQ空间Cookie

> 请使用Chrome内核的浏览器

登录QQ空间后，

1. `F12`打开控制台
2. 选择Tab：Network
3. 点击`XHR`
4. 在`Filter`栏中输入`fcg_list_album_v3`
5. 在页面点击**相册**

![](https://i.loli.net/2020/03/15/ed243FkgWPaBv8b.png)

正常情况下（不排除接口变更），应该会出现内容，点开它

## 获取`g_tk`

`g_tk`具体什么用途，我不清楚，但是不填，无法正常请求。

例如，我这里的`g_tk=1829449952`

![](https://i.loli.net/2020/03/15/7c3pIEHgZySDzej.png)

## 获取cookie

往下翻到`Request Headers`，复制`cookie`

![](https://i.loli.net/2020/03/15/c48nXwS2loAaDrN.png)

最后一步，别忘了，更改你的QQ号

# 额外

> 关于QQ空间的相册接口，我也简单列了一下

[QQ空间相册解析](https://github.com/gcdd1993/QZone-Photo/blob/master/Analysis.md)