var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    user: {},
    realName: '',
    idCardNO : '',
    idImg: ''
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  bindDateChange: function(e){
    this.setData({ 'user.birthday': e.detail.value})
  },

  bindNameChange: function(e){
    this.setData({ 'realName': e.detail.value})
  },

  bindIDnoChange: function (e) {
    this.setData({ 'idCardNO': e.detail.value })
  },

/**用户注册 */
  regedit: function() {
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    //发送请求，实名认证
  },

  //选择证件图片
  chooseImg: function(){
    let _this = this
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success (res) {
        // tempFilePath可以作为img标签的src属性显示图片
        const tempFilePaths = res.tempFilePaths
        var tmpFileName = tempFilePaths[0].substring(tempFilePaths[0].lastIndexOf('/')+1)
        wx.uploadFile({
          url: 'http://118.25.89.125:16602/upload-server/upload', //图片服务器
          filePath: tempFilePaths[0],
          name: 'myFile',
          'fileName': tmpFileName,
          formData: {
            'user': 'test',
            'fileName': tmpFileName,
            'u': '98344921-326f-471e-b735-792eacfecc53'
          },
          success (res){
            let resjson = JSON.parse(res.data)
            console.log(resjson.url)
            _this.setData({
              idImg: resjson.url
            })
            //do something
          }
        })
      }
    })
  },

  submitValidate: function(){
    //组装参数
    let reqData = {url: this.data.idImg, realName: this.data.realName}
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/user/validate',
      header: {
        'content-type': 'application/json' ,
        'Cookie': wx.getStorageSync('cookieKey')
      },
      data: reqData,
      method: 'POST',
      success(res){
        console.log(res)
        wx.hideLoading()
        if(res.data.code == "0"){
          app.globalData.userInfo.isValidate=1
          wx.showModal({
            title: '提示',
            content: '认证成功！',
            showCancel: false,
            success (res2) {
              if (res2.confirm) {
                wx.switchTab({
                  url: '/pages/user/profile',
                })
              }
            }
          })
        }
      }
    })
  }
})