# 宠物领养小程序

#### 介绍
宠物领养小程序，包括前端小程序、管理后台。分享更多毕设项目，请查看：https://github.com/jinnanxu/biyesheji
或者访问我国内的码云gitee仓库，ID：野生程序猿

#### 软件架构
SpringBoot、MyBatis、MySQL、原生小程序。

#### 联系作者
1.  不会部署，或出现bug可以联系作者：157086662@qq.com。需要 **《助农小程序》、公寓平台、自习室** 等项目的同学请联系我。
2.  更多原创项目请联系我：


#### 安装教程

1.  在本地创建数据库，名为：pet，导入sql脚本；
2.  小程序整合vant-weapp：
打开cmd命令窗口，进入小程序项目文件夹，按顺序执行下面命令 
* (1）npm init 
* (2）npm i @vant/weapp -S --production
* (3)开启使用npm模块: 在微信开发者工具右上角“详情”--->“本地设置”--->勾选上使用npm模块 【注：新版的微信开发者工具没有这个选项了，可以忽略】
* (4)构建npm: 在软件左上角“工具”--->“构建npm”
3.  用idea导入服务端代码：pet-server，修改数据库连接配置为你本地的信息，启动接口应用，就可以使用前端小程序了
4.  管理后台：http://localhost:8090/pet-server/admin/login，管理员用户请自行查看数据库admin表


#### 外部依赖服务
1.  为保证小程序可以正常显示图片，图片均保存在云服务器上，如保存在本地，在不同的机器上运行时，容易丢失图片文件导致404。
2.  本小程序使用了腾讯地图的SLB相关API，如果计算距离、获取地址等功能失效了，请配置你自己的API KEY。

