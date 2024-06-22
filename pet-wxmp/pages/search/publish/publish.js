// pages/publish/publish.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pic: '',
    title: '',
    category: '',
    features: '',
    location: '',
    contact: '',
    detail: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  bindDetailInput: function(e){
    this.setData({
      detail: e.detail.value
    })
  },

  //选择宠物图片
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
          formData: {
            'user': 'test',
            'fileName': tmpFileName,
            'u': '98344921-326f-471e-b735-792eacfecc53'
          },
          success (res){
            let resjson = JSON.parse(res.data)
            console.log(resjson.url)
            _this.setData({
              pic: resjson.url
            })
            //do something
          }
        })
      }
    })
  },

  submitSearch: function(){
    //组装参数
    let reqData = this.data
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    wx.request({
      url: app.globalData.apiBaseUrl+'/search/publishNotice',
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
          wx.showModal({
            title: '提示',
            content: '发布成功！',
            showCancel: false,
            success (res2) {
              if (res2.confirm) {
                wx.switchTab({
                  url: '/pages/search/search-list',
                })
              }
            }
          })
        }else if(res.data.code == "301"){
          wx.showModal({
            title: '提示',
            content: '请登录后再发布寻宠，是否马上登录？',
            success (res2) {
              if (res2.confirm) {
                wx.navigateTo({
                  url: '/pages/user/login/login',
                })
              }
            }
          })
        }else{
          wx.showToast({
            title: '系统繁忙，发布失败！',
            icon: 'error'
          })
        }
      }
    })  
  }
})